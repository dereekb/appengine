package com.thevisitcompany.gae.deprecated.model.mod.restore;

import java.util.Collection;

/**
 * Delegate used when restoring objects.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public interface ModelRestoreDelegate<T> {

	public void restore(Collection<T> objects);

}
