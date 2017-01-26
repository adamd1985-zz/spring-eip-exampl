package adamd.domain.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Receipt {
	private final String id;

	private final Integer amount;

	@JsonCreator
	public Receipt(@JsonProperty("id") String id,
			@JsonProperty("amount") Integer amount) {
		this.id = id;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public Integer getAmount() {
		return amount;
	}

}
