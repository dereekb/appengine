package com.dereekb.gae.model.extension.links.system.modification.utility;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderAccessorDelegate;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;

/**
 * {@link LinkModificationSystemBuilder} entry for a model type.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface LinkModificationSystemBuilderEntry<T>
        extends MutableLinkSystemBuilderEntry, MutableLinkSystemBuilderAccessorDelegate<T> {

	/**
	 * 
	 * @return {@link Updater}. Never {@code null}.
	 */
	public Updater<T> getUpdater();

	/**
	 * 
	 * @return {@link TaskRequestSender}. Never {@code null}.
	 */
	public TaskRequestSender<T> getReviewTaskSender();

}
