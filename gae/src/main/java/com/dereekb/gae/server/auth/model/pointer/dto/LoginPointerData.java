package com.dereekb.gae.server.auth.model.pointer.dto;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelData;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO of the {@link LoginPointer} class.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginPointerData extends OwnedDatabaseModelData {

	private static final long serialVersionUID = 1L;

	private Long login;

	private String email;

	private Integer type;

	public LoginPointerData() {}

	public Long getLogin() {
		return this.login;
	}

	public void setLogin(Long login) {
		this.login = login;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	// UniqueModel
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.key);
	}

	@Override
	public String toString() {
		return "LoginPointerData [login=" + this.login + ", key=" + this.key + ", created=" + this.date + "]";
	}

}
