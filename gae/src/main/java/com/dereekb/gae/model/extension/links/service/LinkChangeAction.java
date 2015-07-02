package com.dereekb.gae.model.extension.links.service;


public enum LinkChangeAction {

	/**
	 * Clears all relationships in the specified link.
	 */
	CLEAR,

	/**
	 * Creates a relationship between targets.
	 */
	LINK,

	/**
	 * Removes the relationship between targets.
	 */
	UNLINK;

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
