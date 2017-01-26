package adamd.domain.bid;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ADAM-SE
 * @version 1
 */
@Service
@MessageEndpoint
public class AuctionService {
	public static final Logger LOGGER = LoggerFactory.getLogger(AuctionService.class);

	@ServiceActivator
	public BidReciept bid(Bid bid) {
		LOGGER.info("Played lotto number {}", bid);

		return new BidReciept(UUID.randomUUID().toString(), bid.getItemName());
	}
}
