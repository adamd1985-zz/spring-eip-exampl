package adamd.domain.bid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import adamd.domain.payment.Receipt;

public class BidReciept {

	final private String id;

	final private String itemName;

	final private Receipt paymentReceipt;

	@JsonCreator
	public BidReciept(@JsonProperty("id") String id,
			@JsonProperty("itemName") String itemName) {
		this.id = id;
		this.itemName = itemName;
		this.paymentReceipt = null;
	}

	@JsonCreator
	public BidReciept(@JsonProperty("id") String id,
			@JsonProperty("itemName") String itemName,
			@JsonProperty("paymentReceipt") Receipt paymentReceipt) {
		this.id = id;
		this.itemName = itemName;
		this.paymentReceipt = paymentReceipt;
	}

	public String getItemName() {
		return itemName;
	}

	public Receipt getPaymentReceipt() {
		return paymentReceipt;
	}

	@Override
	public String toString() {
		return String.format("id: [%s], itemName: [%s], cost: [%s]",
				this.id,
				this.itemName,
				this.paymentReceipt != null ? this.paymentReceipt.getAmount().toString() : "N/A");
	}
}
