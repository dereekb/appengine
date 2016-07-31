package com.dereekb.gae.server.auth.model.login.dto;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.search.dto.SearchableDatabaseModelData;
import com.dereekb.gae.server.auth.model.login.Login;
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

	private Integer group;

	private Long roles;

	private List<String> pointers;

	public LoginData() {}

	public Integer getGroup() {
		return this.group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Long getRoles() {
		return this.roles;
	}

	public void setRoles(Long roles) {
		this.roles = roles;
	}

	public List<String> getPointers() {
		return this.pointers;
	}

	public void setPointers(List<String> pointers) {
		this.pointers = pointers;
	}

	@Override
	public String toString() {
		return "LoginData [group=" + this.group + ", roles=" + this.roles + ", pointers=" + this.pointers
		        + ", searchIdentifier=" + this.searchIdentifier + ", key=" + this.key + ", created=" + this.created
		        + "]";
	}

}
