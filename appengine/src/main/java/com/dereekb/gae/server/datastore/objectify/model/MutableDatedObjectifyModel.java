package com.dereekb.gae.server.datastore.objectify.model;

import com.dereekb.gae.server.datastore.objectify.MutableObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.utilities.time.model.MutableDatedModel;

/**
 * Model that implements both {@link MutableDatedModel} and {@link ObjectifyModel}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface MutableDatedObjectifyModel<T>
        extends MutableObjectifyModel<T>, MutableDatedModel {}
