package adamd.integration.config;

import java.util.UUID;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

import adamd.domain.bid.AuctionService;
import adamd.domain.bid.Bid;
import adamd.domain.bid.BidReciept;
import adamd.domain.payment.Payment;
import adamd.domain.payment.PaymentService;
import adamd.domain.payment.Receipt;

/**
 * Java DSL configuration. with the {@code @IntegrationComponentScan} it will connect any
 * integration annotated endpoints.
 * 
 * @author ADAM-SE
 * @version 1
 */
@Configuration
@IntegrationComponentScan(basePackageClasses = { adamd.integration.AuctionFlowGateway.class })
@ConditionalOnExpression("'${app.integration.style}' eq 'DSL'")
public class DslConfig {

	@Bean("error.channel")
	public MessageChannel errorChannel() {
		return MessageChannels.direct("error.channel").get();
	}

	@Bean
	public IntegrationFlow errorFlow() {
		return IntegrationFlows
				.from(errorChannel())
				.handle(new LoggingHandler(Level.INFO.name()))
				.get();
	}

	@Bean("auction.output")
	public MessageChannel auctionOutput() {
		return MessageChannels.direct("auction.output").get();
	}

	@Bean
	public IntegrationFlow auctionFlow(ConnectionFactory connectionFactory) {
		return IntegrationFlows
				.from("auction.input")
				.enrich(e1 -> e1
						.requestChannel(paymentIn())
						.replyChannel(paymentOut())
						.requestPayload(p1 -> {
							Bid bid = (Bid) p1.getPayload();
							return new Payment(bid.getCost());
						})
						.headerExpression("receipt", "payload"))
				.transform(new ObjectToJsonTransformer())
				.log(LoggingHandler.Level.INFO, "Commencing auction flow")
				.handle(Jms.outboundGateway(connectionFactory)
						.correlationKey("auctionCoId")
						.requestPubSubDomain(false)
						.requestDestination("jms.bid.in")
						.replyDestination("jms.bid.out")
						.replyPubSubDomain(true)
						.get())
				.transform(Transformers.fromJson(Bid.class))
				.handle((pld, hdrs) -> new BidReciept(UUID.randomUUID().toString(),
						((Bid) pld).getItemName(),
						(Receipt) hdrs.get("receipt")))
				.log(LoggingHandler.Level.INFO, "Auction flow finishing")
				.channel(auctionOutput())
				.get();
	}

	@Bean("payment.in")
	public MessageChannel paymentIn() {
		return MessageChannels.direct("payment.in").get();
	}

	@Bean("payment.out")
	public MessageChannel paymentOut() {
		return MessageChannels.direct("payment.out").get();
	}

	@Bean
	public IntegrationFlow paymentFlow(ConnectionFactory connectionFactory) {
		return IntegrationFlows
				.from(paymentIn())
				.transform(new ObjectToJsonTransformer())
				.log(LoggingHandler.Level.INFO, "Enriching with payment data")
				.handle(Jms.outboundGateway(connectionFactory)
						.correlationKey("auctionCoId")
						.requestPubSubDomain(false)
						.requestDestination("jms.payment.in")
						.replyDestination("jms.payment.out")
						.replyPubSubDomain(true)
						.extractRequestPayload(true)
						.get())
				.transform(Transformers.fromJson(Receipt.class))
				.get();
	}

	@Bean
	public IntegrationFlow paymentGatewayFlow(ConnectionFactory connectionFactory, PaymentService paymentService) {
		return IntegrationFlows
				.from(Jms
						.messageDrivenChannelAdapter(connectionFactory)
						.errorChannel(errorChannel())
						.destination("jms.payment.in")
						.get())
				.transform(new JsonToObjectTransformer(adamd.domain.payment.Payment.class))
				.log(LoggingHandler.Level.INFO, "Processing payment")
				.handle(paymentService, "pay", a -> a.requiresReply(true))
				.transform(new ObjectToJsonTransformer())
				.handle(Jms
						.outboundAdapter(connectionFactory)
						.configureJmsTemplate(c -> c.pubSubDomain(true))
						.destination("jms.payment.out")
						.get())
				.get();
	}

	@Bean
	public IntegrationFlow gameEngineFlow(ConnectionFactory connectionFactory, AuctionService auctionService) {
		return IntegrationFlows
				.from(Jms
						.messageDrivenChannelAdapter(connectionFactory)
						.errorChannel(errorChannel())
						.destination("jms.bid.in")
						.get())
				.transform(new JsonToObjectTransformer(Bid.class))
				.handle(auctionService, "bid")
				.log(LoggingHandler.Level.INFO)
				.transform(new ObjectToJsonTransformer())
				.handle(Jms
						.outboundAdapter(connectionFactory)
						.configureJmsTemplate(c -> c.pubSubDomain(true))
						.destination("jms.bid.out")
						.get())
				.get();
	}
}
