package com.dereekb.gae.server.auth.security.token.provider.details.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginPointerGrantedAuthorityBuilder;

/**
 * {@link LoginPointerGrantedAuthorityBuilder} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginPointerGrantedAuthorityBuilderImpl
        implements LoginPointerGrantedAuthorityBuilder {

	private static final String DEFAULT_NAME_FORMAT = "ROLE_LOGIN_%s";
	private static final String DEFAULT_TYPE_FORMAT = "ROLE_LOGINTYPE_%s";

	private String nameFormat = DEFAULT_NAME_FORMAT;
	private String typeFormat = DEFAULT_TYPE_FORMAT;

	public LoginPointerGrantedAuthorityBuilderImpl() {}

	public LoginPointerGrantedAuthorityBuilderImpl(String nameFormat, String typeFormat)
	        throws IllegalArgumentException {
		this.setNameFormat(nameFormat);
		this.setTypeFormat(typeFormat);
	}

	public String getNameFormat() {
		return this.nameFormat;
	}

	public void setNameFormat(String nameFormat) throws IllegalArgumentException {
		if (nameFormat == null) {
			throw new IllegalArgumentException();
		}

		this.nameFormat = nameFormat;
	}

	public String getTypeFormat() {
		return this.typeFormat;
	}

	public void setTypeFormat(String typeFormat) throws IllegalArgumentException {
		if (typeFormat == null) {
			throw new IllegalArgumentException();
		}

		this.typeFormat = typeFormat;
	}

	@Override
	public Set<? extends GrantedAuthority> getGrantedAuthorities(LoginPointerType type) {

		String nameString = type.toString();
		String typeString = type.getType().toString();

		String nameRole = String.format(this.nameFormat, nameString);
		String typeRole = String.format(this.typeFormat, typeString);

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(nameRole));
		authorities.add(new SimpleGrantedAuthority(typeRole));

		return authorities;
	}

	@Override
	public String toString() {
		return "LoginPointerGrantedAuthorityBuilderImpl [nameFormat=" + this.nameFormat + ", typeFormat="
		        + this.typeFormat + "]";
	}

}
