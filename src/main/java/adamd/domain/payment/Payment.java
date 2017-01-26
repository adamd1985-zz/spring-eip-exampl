package adamd.domain.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.databind.JavaType;

public class Payment {

	private final Integer amount;

	@JsonCreator
	public Payment(@JsonProperty("amount") Integer amount) {
		this.amount = amount;
	}

	public Integer getAmount() {
		return amount;
	}
}
