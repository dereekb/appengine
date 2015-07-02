package com.dereekb.gae.model.extension.links.deprecated.functions;

/**
 * @author dereekb
 * @see {@link LinksMethodRetriever} for looking up methods annotated with this.
 */
public enum LinksAction {

	/**
	 * Operation to link the objects to eachother.
	 */
	LINK,

	/**
	 * Operation to unlink the objects from eachother.
	 */
	UNLINK;

	public static LinksAction withString(String type) {
		LinksAction action = null;

		switch (type) {
			case "add":
			case "link":
				action = LinksAction.LINK;
				break;
			case "remove":
			case "unlink":
				action = LinksAction.UNLINK;
				break;
		}

		return action;
	}

}
