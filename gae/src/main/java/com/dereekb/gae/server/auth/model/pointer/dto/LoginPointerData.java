package com.dereekb.gae.server.auth.model.pointer.dto;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelData;
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
public class LoginPointerData extends DatabaseModelData {

	private static final long serialVersionUID = 1L;

	private Long login;

	public LoginPointerData() {}

	public Long getLogin() {
		return this.login;
	}

	public void setLogin(Long login) {
		this.login = login;
	}

	// UniqueModel
	@Override
	public ModelKey getModelKey() {
		return ModelKey.convertNumberString(this.key);
	}

	@Override
	public String toString() {
		return "LoginPointerData [login=" + this.login + ", key=" + this.key + ", created=" + this.created + "]";
	}

}
