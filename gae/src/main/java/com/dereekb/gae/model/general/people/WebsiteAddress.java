package com.dereekb.gae.model.general.people;

import java.io.Serializable;

/**
 * Represents a website with a specified {@link WebsiteAddressType}.
 *
 * @author dereekb
 *
 */
public class WebsiteAddress
        implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer typeId;

	private String data;

	public WebsiteAddress() {}

	public WebsiteAddress(WebsiteAddressType type, String data) {
		this.setType(type);
		this.data = data;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public WebsiteAddressType getType() {
		return WebsiteAddressType.typeForId(this.typeId);
	}

	public void setType(WebsiteAddressType type) {
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
		return "WebsiteAddress [typeId=" + this.typeId + ", data=" + this.data + "]";
	}

}
