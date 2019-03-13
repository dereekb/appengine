package com.dereekb.gae.server.event.model.notification.dto;

import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO of the {@link Notification} class.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationData extends OwnedDatabaseModelData {

	private static final long serialVersionUID = 1L;

	private Integer type = 0;

	private Boolean read = true;

	private String code;

	private String data;
	
	@JsonInclude(Include.NON_DEFAULT)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@JsonInclude(Include.NON_DEFAULT)
	public Boolean getRead() {
		return this.read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "NotificationData [type=" + this.type + ", read=" + this.read + ", code=" + this.code + ", data="
		        + this.data + "]";
	}

}
