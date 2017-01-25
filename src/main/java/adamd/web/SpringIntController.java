package adamd.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adamd.domain.AuthToken;
import adamd.domain.Ticket;
import adamd.integration.LottoPlayGateway;

@RestController
public class SpringIntController {
	public static final Logger LOGGER = LoggerFactory.getLogger(SpringIntController.class);

	@Autowired
	private LottoPlayGateway gateway;

	@RequestMapping(method = RequestMethod.GET, path = "/test")
	public void test() {
		final Ticket ticket = gateway.buy(new AuthToken("IamThebestGurururu", new String[] { "LOTTO", "WALLET" }), 1);

		if (ticket != null) {
			LOGGER.info("Flow finished buying ticket: {}", ticket.getNumber());
		}
		else {
			LOGGER.error("Flow failed buying ticket");
		}
	}
}
