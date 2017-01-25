package adamd.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adamd.domain.AuthToken;

/**
 * 
 * @author ADAM-SE
 * @version 1
 */
@RestController
public class AuthController {
	public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(method = RequestMethod.GET, path = "/auth/token")
	public AuthToken getAuth() {
		LOGGER.info("Authenticating");

		return new AuthToken("thebestandtheguru", new String[]{"WALLET", "LOTTO"});
	}
}
