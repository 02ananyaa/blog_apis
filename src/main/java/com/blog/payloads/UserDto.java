package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {

	private Long id;
	
	@NotEmpty
	@Size(min=4 , max=6)
	private String name;
	@Email(message="Email address is not valid !!!")
	
	private String email;
	
	@Min(6)
	
	private String password;
	
	@NotEmpty(message="Please write about yourself ")
	private String about;

}
