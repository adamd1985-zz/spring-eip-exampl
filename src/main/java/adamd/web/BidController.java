package adamd.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adamd.domain.bid.Bid;
import adamd.domain.bid.BidReciept;
import adamd.domain.security.SecurityToken;
import adamd.integration.AuctionFlowGateway;

@RestController
public class BidController {
	public static final Logger LOGGER = LoggerFactory.getLogger(BidController.class);

	@Autowired
	private AuctionFlowGateway gateway;

	@RequestMapping(method = RequestMethod.GET, path = "/auction/example/bid")
	public BidReciept bid(@RequestParam("amount") Integer amount) {
		final SecurityToken secToken = new SecurityToken("anon", new String[] { "AUCTION", "WALLET" });
		final Bid bid = new Bid("example", amount);
		final BidReciept bidReceipt = gateway.bid(secToken, bid);

		if (bidReceipt != null) {
			LOGGER.info("Flow finished item auctioned: {}", bidReceipt);
		}
		else {
			LOGGER.error("Flow failed");
		}

		return bidReceipt;
	}
}
