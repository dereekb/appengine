package com.dereekb.gae.web.api.auth.controller.model.impl;

import com.dereekb.gae.web.api.auth.controller.model.ApiLoginTokenModelContextResponse;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link ApiLoginTokenModelContextResponse} implementation.
 *
 * @author dereekb
 *
 */
public class ApiLoginTokenModelContextResponseImpl
        implements ApiLoginTokenModelContextResponse {

	private LoginTokenPair loginToken;
	private ApiModelRolesResponseData rolesData;

	public ApiLoginTokenModelContextResponseImpl(LoginTokenPair loginToken, ApiModelRolesResponseData rolesData) {
		this.setLoginToken(loginToken);
		this.setRolesData(rolesData);
	}

	// MARK: ApiLoginTokenModelContextResponse
	@Override
	public LoginTokenPair getLoginToken() {
		return this.loginToken;
	}

	public void setLoginToken(LoginTokenPair loginToken) {
		this.loginToken = loginToken;
	}

	@Override
	public ApiModelRolesResponseData getRolesData() {
		return this.rolesData;
	}

	public void setRolesData(ApiModelRolesResponseData rolesData) {
		this.rolesData = rolesData;
	}

	@Override
	public String toString() {
		return "ApiLoginTokenModelContextResponseImpl [loginToken=" + this.loginToken + ", rolesData=" + this.rolesData
		        + "]";
	}

}
