package com.dereekb.gae.model.extension.visibility;

/**
 * Used to read the current {@link VisibilityState} from objects.
 *
 * @author dereekb
 */
public interface VisibilityStateReader<T> {

	public VisibilityState readVisibilityState(T object);

}
