package com.dereekb.gae.server.app.model.app.shared;

import com.dereekb.gae.server.app.model.app.App;
import com.googlecode.objectify.Key;

/**
 * Mutable {@link AppRelatedModel}.
 *
 * @author dereekb
 *
 */
public interface MutableAppRelatedModel
        extends AppRelatedModel {

	/**
	 * Sets the app for the model.
	 *
	 * @param App
	 *            {@link Key}. Can be {@code null}.
	 */
	public void setApp(Key<App> App);

}
