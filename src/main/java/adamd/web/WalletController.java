package adamd.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import adamd.domain.AuthToken;
import adamd.domain.PurchaseRequest;
import adamd.domain.Ticket;

@RestController
public class WalletController {
	public static final Logger LOGGER = LoggerFactory.getLogger(WalletController.class);
	
	private boolean fail = false;
	
	@Value("${app.fail:false}")
	public void setFail(boolean fail){
		this.fail = fail;
	}


	@RequestMapping(method = RequestMethod.POST, path = "/{username}/wallet/purchase/lottonumber")
	public AuthToken purchaseLottoNumber(@PathVariable(value = "username") String username, @RequestBody AuthToken auth) throws Exception {
		LOGGER.info("Purchased lotto number on wallet of {}", username);
		
		if (fail){
			throw new Exception("I failed");
		}

		return auth;
	}
}
