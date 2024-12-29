package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {

	private String token;
	private String username;

	// Private constructor to enforce the use of the builder pattern
	private JwtResponse(JwtResponseBuilder builder) {
		this.token = builder.token;
		this.username = builder.username; // Set the username from builder
	}

}
