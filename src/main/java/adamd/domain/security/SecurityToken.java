package adamd.domain.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SecurityToken {
	private final String username;

	private final String[] authorities;

	@JsonCreator
	public SecurityToken(@JsonProperty("username") String username,
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
