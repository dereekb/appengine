package com.dereekb.gae.server.auth.model.login.dto;

import java.util.Date;
import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.impl.dto.DescribedDatabaseModelData;
import com.dereekb.gae.server.auth.model.login.Login;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class LoginData extends DescribedDatabaseModelData {

	private static final long serialVersionUID = 1L;

	private Date authReset;

	private Integer group;

	private Long roles;

	private List<String> pointers;

	public LoginData() {}

	public String getAuthReset() {
		String value = null;

		if (this.authReset != null) {
			value = DATE_CONVERTER.convertToString(this.authReset);
		}

		return value;
	}

	public void setAuthReset(String date) {
		Date value = null;

		if (date != null) {
			value = DATE_CONVERTER.convertFromString(date);
		}

		this.authReset = value;
	}

	@JsonIgnore
	public Date getAuthResetValue() {
		return this.authReset;
	}

	@JsonIgnore
	public void setAuthResetValue(Date date) {
		this.authReset = date;
	}

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
		        + ", searchIdentifier=" + this.searchIdentifier + ", key=" + this.key + ", created=" + this.date + "]";
	}

}
