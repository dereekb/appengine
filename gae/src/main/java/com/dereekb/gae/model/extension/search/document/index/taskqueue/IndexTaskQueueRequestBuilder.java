package com.dereekb.gae.model.extension.search.document.index.taskqueue;

import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.taskqueue.deprecated.ModelTaskQueueRequestBuilder;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ModelTaskQueueRequestBuilder} implementation for building search
 * indexing requests for the taskqueue.
 *
 * @author dereekb
 *
 * @param <T>
 *            Type scheduled to be indexed.
 */
@Deprecated
public class IndexTaskQueueRequestBuilder<T extends UniqueModel> extends ModelTaskQueueRequestBuilder<T> {

	private static final String SEARCH_TASK_NAME_FORMAT = "search/%s";

	private final IndexAction action;
	private final String format;

	public IndexTaskQueueRequestBuilder(IndexAction action) {
		this(action, SEARCH_TASK_NAME_FORMAT);
	}

	public IndexTaskQueueRequestBuilder(IndexAction action, String format) {
		this.action = action;
		this.format = format;
	}

	@Override
	protected String getRequestPath() {
		String actionString = null;

		switch (this.action) {
			case INDEX: {
				actionString = "index";
			}
				break;
			case UNINDEX: {
				actionString = "unindex";
			}
				break;
		}

		String requestPath = String.format(this.format, actionString);
		return requestPath;
	}

	public IndexAction getAction() {
		return this.action;
	}

	public String getFormat() {
		return this.format;
	}

}
