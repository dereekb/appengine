package com.thevisitcompany.gae.deprecated.model.users.login.dto;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.KeyedModelData;
import com.thevisitcompany.gae.deprecated.model.users.login.LoginSettings;

@JsonInclude(Include.NON_EMPTY)
public class LoginData extends KeyedModelData<Long> {

	private static final long serialVersionUID = 1L;

	@Size(min = 0, max = 128)
	private String email;

	private Long user;

	private List<Long> accounts;

	private LoginSettings settings;

	private Boolean isActive;

	public LoginData() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public List<Long> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Long> accounts) {
		this.accounts = accounts;
	}

	public LoginSettings getSettings() {
		return settings;
	}

	public void setSettings(LoginSettings settings) {
		this.settings = settings;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
