package com.dereekb.gae.model.general.people.website;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dereekb.gae.model.general.people.website.impl.DecodedWebsiteAddress;
import com.dereekb.gae.model.general.people.website.impl.ServiceWebsiteImpl;
import com.dereekb.gae.model.general.people.website.validation.ValidWebsiteAddressType;

/**
 * Represents an <u>encoded</u> {@link Website} with a specified
 * {@link WebsiteAddressType}.
 *
 * @author dereekb
 * @see {@link ServiceWebsiteImpl} for an un-encoded {@link Website}
 *      implementation.
 * @see {@link DecodedWebsiteAddress}
 * @see {@link WebsiteAddressDecoder}
 */
public class WebsiteAddress
        implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MAX_DATA_LENGTH = 300;

	@NotNull
	@ValidWebsiteAddressType
	private Integer typeId;

	@NotNull
	@Size(max = MAX_DATA_LENGTH)
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
		return WebsiteAddressType.valueOf(this.typeId);
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
