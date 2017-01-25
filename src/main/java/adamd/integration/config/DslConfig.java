package adamd.integration.config;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.http.Http;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.messaging.MessageChannel;

import adamd.domain.Ticket;

/**
 * Java DSL configuration. with the {@code @IntegrationComponentScan} it will connect any
 * integration annotated endpoints.
 * 
 * @author ADAM-SE
 * @version 1
 */
@Configuration
@IntegrationComponentScan(basePackageClasses = { adamd.integration.LottoPlayGateway.class })
@ConditionalOnExpression("'${app.integration.style}' eq 'DSL'")
public class DslConfig {

	@Bean
	public MessageChannel error() {
		return MessageChannels.direct("error").get();
	}

	@Bean("lottoFlow.output")
	public MessageChannel lottoFlowoutput() {
		return MessageChannels.direct().get();
	}

	@Bean
	public IntegrationFlow lottoFlow(ConnectionFactory connectionFactory) {
		return lottoFlow -> lottoFlow
				.transform(new ObjectToJsonTransformer())
				.handle(Http
						.outboundGateway("http://localhost:8080/lotto-int-demo/{username}/wallet/purchase/lottonumber")
						.expectedResponseType(adamd.domain.AuthToken.class)
						.httpMethod(HttpMethod.POST)
						.extractPayload(true)
						.charset("UTF-8")
						.uriVariable("username", "'number1'")
						.get())
				.transform(new ObjectToJsonTransformer())
				.handle(Jms.outboundGateway(connectionFactory)
						.correlationKey("coId")
						.requestPubSubDomain(true)
						.requestDestination("topic.req")
						.replyDestination("topic.res")
						.replyPubSubDomain(true)
						.get())
				.transform(new JsonToObjectTransformer(adamd.domain.Ticket.class))
				.channel(lottoFlowoutput());
	}

	@Bean
	public IntegrationFlow getGameEngineFlow(ConnectionFactory connectionFactory) {
		return IntegrationFlows
				.from(Jms
						.inboundAdapter(connectionFactory)
						.destination("topic.req")
						.get())
				.handle(Http
						.outboundGateway("http://localhost:8080/lotto-int-demo/lotto/ticket/{number}")
						.extractPayload(false)
						.httpMethod(HttpMethod.GET)
						.expectedResponseType(Ticket.class)
						.charset("UTF-8")
						.uriVariable("username", "headers.lottoNumber")
						.get())
				.headerFilter("lottoNumber, http_statusCode")
				.transform(new ObjectToJsonTransformer())
				.handle(Jms
						.outboundAdapter(connectionFactory)
						.extractPayload(true)
						.destination("topic.res")
						.get())
				.get();
	}
}
