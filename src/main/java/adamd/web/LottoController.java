package adamd.web;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adamd.domain.Ticket;

/**
 * 
 * @author ADAM-SE
 * @version 1
 */
@RestController
public class LottoController {
	public static final Logger LOGGER = LoggerFactory.getLogger(LottoController.class);

	@RequestMapping(method = RequestMethod.GET, path = "/lotto/ticket/{number}")
	public Ticket playLottoNumber(@PathVariable(value = "number") Integer number) {
		LOGGER.info("Played lotto number {}", number);

		return new Ticket(UUID.randomUUID().toString(), number, 10);
	}
}
