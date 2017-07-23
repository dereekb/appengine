package com.dereekb.gae.model.extension.links.system.mutable;

/**
 * {@link MutableLinkChange} type.
 * 
 * @author dereekb
 *
 */
public enum MutableLinkChangeType {

	ADD("add"),

	REMOVE("remove"),

	SET("set"),

	CLEAR("clear"),
	
	/**
	 * Special change type used in cases where a change must be submitted but no actual changes should take place. 
	 */
	NONE("none");

	private final String action;

	private MutableLinkChangeType(String action) {
		this.action = action;
	}

	public String getActionName() {
		return this.action;
	}

	public static MutableLinkChangeType fromString(String action) throws IllegalArgumentException {
		MutableLinkChangeType result;

		switch (action.toLowerCase()) {
			case "set":
				result = MutableLinkChangeType.SET;
				break;
			case "add":
			case "link":
				result = MutableLinkChangeType.ADD;
				break;
			case "remove":
			case "unlink":
				result = MutableLinkChangeType.REMOVE;
				break;
			case "clear":
				result = MutableLinkChangeType.CLEAR;
				break;
			default:
				throw new IllegalArgumentException("Unknown change action.");
		}

		return result;
	}

}
