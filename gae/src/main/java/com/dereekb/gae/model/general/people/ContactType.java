package com.dereekb.gae.model.general.people;

/**
 * Describes the information of a of {@link Contact}.
 *
 * @author dereekb
 */
public enum ContactType {

	/**
	 * A phone number.
	 *
	 * The information should be formatted to follow the E164 standard.
	 *
	 * @see {@link http://en.wikipedia.org/wiki/E.164}
	 */
	PHONE(0),

	/**
	 * An email address.
	 */
	EMAIL(1);

	private final Integer type;

	private ContactType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
		return this.type;
	}

	public static ContactType typeForId(Integer typeId) {
		ContactType type;

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
