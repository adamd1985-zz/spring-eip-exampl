package adamd.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {
	private final String username;

	private final String[] authorities;

	@JsonCreator
	public AuthToken(@JsonProperty("username") String username,
			@JsonProperty("authorities") String[] authorities) {
		this.username = username;
		this.authorities = authorities;
	}

	public String getUsername() {
		return username;
	}

	public String[] getAuthorities() {
		return authorities;
	}

}
