package com.dereekb.gae.model.general.people.contact;

/**
 * Describes the information of a of {@link ContactAddress}.
 *
 * @author dereekb
 */
public enum ContactAddressType {

	/**
	 * A phone number.
	 *
	 * The information should be formatted to follow the E164 standard.
	 *
	 * @see {@link http://en.wikipedia.org/wiki/E.164}
	 */
	PHONE(0),

	/**
	 * An email address, formatted as "{@code email@domain.com}".
	 */
	EMAIL(1);

	private final Integer type;

	private ContactAddressType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
		return this.type;
	}

	public static ContactAddressType typeForId(Integer typeId) {
		ContactAddressType type;

		switch (typeId) {
			case 0:
				type = PHONE;
				break;
			case 1:
				type = EMAIL;
				break;
			default:
				type = EMAIL;
				break;
		}

		return type;
	}

}
