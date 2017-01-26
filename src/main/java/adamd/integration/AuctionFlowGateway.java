package adamd.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

import adamd.domain.bid.Bid;
import adamd.domain.bid.BidReciept;
import adamd.domain.security.SecurityToken;

/**
 * Annotations on this gateway will only be used if {@code @IntegrationComponentScan} is defined.
 * 
 * @author adamd
 * @version 1
 */
@MessagingGateway
public interface AuctionFlowGateway {

	@Gateway(requestChannel = "auction.input", replyChannel = "auction.output", requestTimeout = 10000, replyTimeout = 50000)
	BidReciept bid(@Header("secToken") SecurityToken token, Bid bid);
}
