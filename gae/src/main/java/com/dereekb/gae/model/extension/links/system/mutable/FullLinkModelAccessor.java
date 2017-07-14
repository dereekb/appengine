package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.readonly.LinkModelAccessor;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Full accessor that extends {@link MutableLinkModelAccessor}. {@link MutableLinkModelBuilder} and {@link LinkModelAccessor}.
 * 
 * @author dereekb
 *
 * @param <T> model type
 */
public interface FullLinkModelAccessor<T extends UniqueModel> extends MutableLinkModelAccessor<T>, MutableLinkModelBuilder<T>, LinkModelAccessor {

}
