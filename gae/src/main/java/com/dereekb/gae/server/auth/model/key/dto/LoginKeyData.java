package com.dereekb.gae.server.auth.model.key.dto;

import com.dereekb.gae.server.datastore.models.dto.DatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO of the {@link LoginKey} class.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginKeyData extends DatabaseModelData {

	private static final long serialVersionUID = 1L;

	private String pointer;

	private String name;

	private Long mask;

	private String verification;

	private Long expiration;

	public LoginKeyData() {}

	public String getPointer() {
		return pointer;
	}

	public void setPointer(String pointer) {
		this.pointer = pointer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMask() {
		return mask;
	}

	public void setMask(Long mask) {
		this.mask = mask;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public Long getExpiration() {
		return expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	@Override
	public String toString() {
		return "LoginKeyData [pointer=" + pointer + ", name=" + name + ", mask=" + mask + ", verification="
		        + verification + ", expiration=" + expiration + ", key=" + key + ", created=" + date + "]";
	}

}
