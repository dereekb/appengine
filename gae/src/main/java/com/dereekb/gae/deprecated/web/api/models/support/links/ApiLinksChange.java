package com.thevisitcompany.gae.deprecated.web.api.models.support.links;

import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;

/**
 * Wrapper for an {@link LinksChange}, used by {@link ApiLinksDirector}.
 * 
 * @author dereekb
 */
public class ApiLinksChange<K> {

	private final LinksAction action;
	private final LinksChange<K> changes;
	private final boolean forced;

	public ApiLinksChange(LinksAction action, LinksChange<K> changes, Boolean forced) throws NullPointerException {
		if (action == null || changes == null) {
			throw new NullPointerException("Action or changes was null.");
		}

		this.action = action;
		this.changes = changes;
		this.forced = (forced != null) ? forced : false;
	}

	public String getType() {
		return this.changes.getType();
	}

	public LinksAction getAction() {
		return this.action;
	}

	public LinksChange<K> getChanges() {
		return this.changes;
	}

	public boolean isForced() {
		return forced;
	}

}
