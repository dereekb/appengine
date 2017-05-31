package com.dereekb.gae.utilities.misc.delta.utility;

import com.dereekb.gae.utilities.misc.delta.MutableCountSyncModel;

/**
 * Used for counting and synchronizing the model changes.
 * 
 * @author dereekb
 *
 */
public interface CountSyncModelSynchronizeCounter<T extends MutableCountSyncModel<T, ?>> {

	/**
	 * Synchronizes the model with the counter. The model should be modified.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 */
	public void synchronize(T model);

	/**
	 * De-synchronizes the model with the counter. The model's delta will return
	 * to equal it's count.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 */
	public void remove(T model);

}
