package adamd.domain.bid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bid {
	private final String itemName;

	private final Integer cost;

	@JsonCreator
	public Bid(@JsonProperty("itemName") String itemName,
			@JsonProperty("cost") Integer cost) {
		this.itemName = itemName;
		this.cost = cost;
	}

	public String getItemName() {
		return itemName;
	}

	public Integer getCost() {
		return cost;
	}
}
