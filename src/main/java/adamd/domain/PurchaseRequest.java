package adamd.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.databind.JavaType;

public class PurchaseRequest {
	private final Long walletId;

	private final Integer amount;


	@JsonCreator
	public PurchaseRequest(@JsonProperty("walletId") Long walletId,
			@JsonProperty("amount") Integer amount) {
		this.walletId = walletId;
		this.amount = amount;
	}

	public Long getWalletId() {
		return walletId;
	}

	public Integer getAmount() {
		return amount;
	}
}
