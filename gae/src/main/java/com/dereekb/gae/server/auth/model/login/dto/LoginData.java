package com.dereekb.gae.server.auth.model.login.dto;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.search.document.search.dto.SearchableDatabaseModelData;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * DTO of the {@link Login} class.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginData extends SearchableDatabaseModelData {

	private static final long serialVersionUID = 1L;

	private Integer type;

	private Set<Integer> roles;

	private List<String> pointers;

	public LoginData() {}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

    public Set<Integer> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Integer> roles) {
		this.roles = roles;
	}

	public List<String> getPointers() {
		return this.pointers;
	}

	public void setPointers(List<String> pointers) {
		this.pointers = pointers;
	}

	// UniqueModel
	@Override
	public ModelKey getModelKey() {
		return ModelKey.convertNumberString(this.key);
	}

}
