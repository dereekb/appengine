package com.dereekb.gae.model.general.people.contact;

import javax.validation.constraints.NotNull;

import com.dereekb.gae.model.general.people.contact.validation.ValidContactAddressType;

/**
 * Contact information.
 *
 * @author dereekb
 */
public class ContactAddress {

	@NotNull
	@ValidContactAddressType
	private Integer typeId;

	@NotNull
	private String data;

	public ContactAddress() {}

	public ContactAddress(ContactAddressType type, String data) {
		this.setType(type);
		this.data = data;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public ContactAddressType getType() {
		return ContactAddressType.valueOf(this.typeId);
	}

	public void setType(ContactAddressType type) {
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
		return "ContactAddress [typeId=" + this.typeId + ", data=" + this.data + "]";
	}

}
