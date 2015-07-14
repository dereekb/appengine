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
	PHONE(0),

	/**
	 * Denotes the number is a cell phone. Format is the same as {@link #PHONE}
	 */
	MOBILE_PHONE(1),

	/**
	 * An email address, formatted as "{@code email@domain.com}".
	 */
	EMAIL(2);

	public final Integer id;

	private ContactAddressType(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
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
