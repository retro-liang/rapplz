package com.retro.rapplz.web.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class SignUpForm
{
	@NotBlank
	@Length(min=6, max=50)
	@Email
	private String email;

	@NotBlank
	@Length(min=6, max=30)
	private String password;
	
	@NotBlank
	@Length(min=3, max=30)
	private String firstName;
	
	@NotBlank
	@Length(min=3, max=30)
	private String lastName;
}