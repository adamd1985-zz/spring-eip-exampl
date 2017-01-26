package adamd.domain.payment;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adamd.domain.bid.Bid;
import adamd.domain.security.SecurityToken;

@Service
@MessageEndpoint
public class PaymentService {
	public static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

	@ServiceActivator
	public Receipt pay(Payment payment) throws Exception {
		LOGGER.info("Purchased lotto number on wallet of {}", payment);
		return new Receipt(UUID.randomUUID().toString(), payment.getAmount());
	}
}
