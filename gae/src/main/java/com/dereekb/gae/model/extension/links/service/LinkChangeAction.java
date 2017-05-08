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
	 * Clears all relationships in the specified link.
	 */
	CLEAR("clear"),

	/**
	 * Creates a relationship between targets.
	 */
	LINK("link"),

	/**
	 * Removes the relationship between targets.
	 */
	UNLINK("unlink");

	private final String action;

	private LinkChangeAction(String action) {
		this.action = action;
	}

	public String getActionName() {
		return this.action;
	}

	public static LinkChangeAction withString(String action) throws IllegalArgumentException {
		LinkChangeAction result;

		switch (action) {
			case "set":
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
			default:
				throw new IllegalArgumentException("Unknown change action.");
		}

		return result;
	}

}
