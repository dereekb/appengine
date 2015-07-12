package com.dereekb.gae.model.general.people.contact;


/**
 * ContactAddress information.
 *
 * @author dereekb
 */
public final class ContactAddress {

	private Integer typeId;

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
		return ContactAddressType.typeForId(this.typeId);
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
