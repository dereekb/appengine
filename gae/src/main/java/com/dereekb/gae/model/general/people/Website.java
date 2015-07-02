package com.dereekb.gae.model.general.people;

import java.io.Serializable;

/**
 * Represents a website with a specified {@link WebsiteType}.
 *
 * @author dereekb
 *
 */
public class Website
        implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer typeId;

	private String data;

	public Website() {}

	public Website(WebsiteType type, String data) {
		this.setType(type);
		this.data = data;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public WebsiteType getType() {
		return WebsiteType.typeForId(this.typeId);
	}

	public void setType(WebsiteType type) {
		this.typeId = type.getId();
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Website [typeId=" + this.typeId + ", data=" + this.data + "]";
	}

}
