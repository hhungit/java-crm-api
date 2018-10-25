package com.bys.crm.config.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SHA1PasswordEncoder implements PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(rawPassword.toString().getBytes(StandardCharsets.US_ASCII));
			byte[] bytes = digest.digest();
			
			return Base64.getEncoder().encodeToString(bytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}

}
