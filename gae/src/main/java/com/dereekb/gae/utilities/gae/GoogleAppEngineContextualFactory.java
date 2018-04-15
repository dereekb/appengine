package com.dereekb.gae.utilities.gae;

import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link Factory} that returns different implementations based on the type of
 * Google App Engine environment.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface GoogleAppEngineContextualFactory<T>
        extends Factory<T> {}
