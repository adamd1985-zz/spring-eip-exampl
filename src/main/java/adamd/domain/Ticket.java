package adamd.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {
	private final String id;

	private final Integer number;

	private final Integer price;

	@JsonCreator
	public Ticket(@JsonProperty("id") String id,
			@JsonProperty("number") Integer number,
			@JsonProperty("price") Integer price) {
		this.id = id;
		this.number = number;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public Integer getPrice() {
		return price;
	}

	public Integer getNumber() {
		return number;
	}

}
