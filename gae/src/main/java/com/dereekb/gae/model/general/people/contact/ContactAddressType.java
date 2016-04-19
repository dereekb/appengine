package com.dereekb.gae.model.general.people.contact;

/**
 * Describes the information of a of {@link ContactAddress}.
 *
 * @author dereekb
 */
public enum ContactAddressType {

	/**
	 * A general phone number.
	 *
	 * The information should be formatted to follow the E164 standard.
	 *
	 * @see {@link http://en.wikipedia.org/wiki/E.164}
	 */
	PHONE(0, "phone"),

	/**
	 * Denotes the number is a cell phone. Format is the same as {@link #PHONE}
	 */
	MOBILE_PHONE(1, "cell"),

	/**
	 * An email address, formatted as "{@code email@domain.com}".
	 */
	EMAIL(2, "email");

	private final Integer id;
	private final String type;

	private ContactAddressType(Integer id, String type) {
		this.id = id;
		this.type = type;
	}

	public Integer getId() {
		return this.id;
	}

	public String getType() {
		return this.type;
	}

	public static ContactAddressType typeForId(Integer id) {
		ContactAddressType type;

		switch (id) {
			case 0:
				type = PHONE;
				break;
			case 1:
				type = MOBILE_PHONE;
				break;
			case 2:
				type = EMAIL;
				break;
			default:
				type = EMAIL;
				break;
		}

		return type;
	}

}
