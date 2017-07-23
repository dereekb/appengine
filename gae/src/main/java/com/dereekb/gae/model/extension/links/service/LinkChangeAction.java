package com.dereekb.gae.model.extension.links.service;

/**
 * Relationship link change action.
 *
 * @author dereekb
 * 
 * @see LinkSystemChange
 */
public enum LinkChangeAction {

	/**
	 * Creates a relationship between targets.
	 */
	LINK("link"),

	/**
	 * Removes the relationship between targets.
	 */
	UNLINK("unlink"),

	/**
	 * Clears the existing relationship before establishing new relationships between targets.
	 */
	SET("set"),
	
	/**
	 * Clears all relationships in the specified link.
	 */
	CLEAR("clear");

	private final String action;

	private LinkChangeAction(String action) {
		this.action = action;
	}

	public String getActionName() {
		return this.action;
	}

	public static LinkChangeAction fromString(String action) throws IllegalArgumentException {
		LinkChangeAction result;

		switch (action.toLowerCase()) {
			case "set":
				result = LinkChangeAction.SET;
				break;
			case "add":
			case "link":
				result = LinkChangeAction.LINK;
				break;
			case "remove":
			case "unlink":
				result = LinkChangeAction.UNLINK;
				break;
			case "clear":
				result = LinkChangeAction.CLEAR;
				break;
			default:
				throw new IllegalArgumentException("Unknown change action.");
		}

		return result;
	}

}
