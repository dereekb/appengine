package com.dereekb.gae.model.general.people;


/**
 * Contact information.
 *
 * @author dereekb
 */
public final class Contact {

	private Integer typeId;

	private String info;

	public Contact() {}

	public Contact(ContactType type, String info) {
		this.setType(type);
		this.info = info;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public ContactType getType() {
		return ContactType.typeForId(this.typeId);
	}

	public void setType(ContactType type) {
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
		return "Contact [typeId=" + this.typeId + ", info=" + this.info + "]";
	}

}
