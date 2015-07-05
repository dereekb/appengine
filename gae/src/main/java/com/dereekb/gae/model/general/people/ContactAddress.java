package com.dereekb.gae.model.general.people;


/**
 * ContactAddress information.
 *
 * @author dereekb
 */
public final class ContactAddress {

	private Integer typeId;

	private String info;

	public ContactAddress() {}

	public ContactAddress(ContactAddressType type, String info) {
		this.setType(type);
		this.info = info;
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

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "ContactAddress [typeId=" + this.typeId + ", info=" + this.info + "]";
	}

}
