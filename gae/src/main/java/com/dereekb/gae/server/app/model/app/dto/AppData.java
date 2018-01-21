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
	private String secret;
	private Integer level;

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

	@Override
	public String toString() {
		return "AppData [name=" + this.name + ", secret=" + this.secret + ", level=" + this.level + ", login="
		        + this.login + ", key=" + this.key + ", date=" + this.date + "]";
	}

}
