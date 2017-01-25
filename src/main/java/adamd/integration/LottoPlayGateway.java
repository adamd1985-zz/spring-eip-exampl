package adamd.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

import adamd.domain.AuthToken;
import adamd.domain.Ticket;

/**
 * Annotations on this gateway will only be used if {@code @IntegrationComponentScan} is defined. s
 * 
 * @author ADAM-SE
 * @version 1
 */
@MessagingGateway
public interface LottoPlayGateway {

	@Gateway(requestChannel = "lottoFlow.input", replyChannel = "lottoFlow.output", payloadExpression = "#args[0]")
	Ticket buy(@Header("authToken") AuthToken token, @Header("lottoNumber") int lottoNumber);
}
