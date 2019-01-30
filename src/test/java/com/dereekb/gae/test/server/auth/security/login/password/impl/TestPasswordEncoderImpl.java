package com.dereekb.gae.test.server.auth.security.login.password.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Test {@link PasswordEncoder} implementation.
 * <p>
 * NEVER use this for production. Use {@link BCryptPasswordEncoder} instead.
 * <p>
 * This implementation is just to reduce the amount of CPU required by not using BCrypt for tests.
 * 
 * @author dereekb
 *
 */
public class TestPasswordEncoderImpl
        implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword,
	                       String encodedPassword) {
		return encodedPassword.equals(rawPassword.toString());
	}

}
