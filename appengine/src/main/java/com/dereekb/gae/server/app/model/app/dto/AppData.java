package com.dereekb.gae.server.app.model.app.dto;

import com.dereekb.gae.server.datastore.models.dto.DatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO for {@link App}.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppData extends DatabaseModelData {

	private static final long serialVersionUID = 1L;

	private String name;
	private String systemKey;
	private String secret;
	private Integer level;

	private String app;
	private String service;
	private String version;

	private boolean initialized = false;

	private Long login;

	public AppData() {
		super();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSystemKey() {
		return this.systemKey;
	}

	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getLogin() {
		return this.login;
	}

	public void setLogin(Long login) {
		this.login = login;
	}

	public String getApp() {
		return this.app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isInitialized() {
		return this.initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public String toString() {
		return "AppData [name=" + this.name + ", secret=" + this.secret + ", level=" + this.level + ", app=" + this.app
		        + ", service=" + this.service + ", version=" + this.version + ", initialized=" + this.initialized
		        + ", login=" + this.login + ", key=" + this.key + ", date=" + this.date + "]";
	}

}
