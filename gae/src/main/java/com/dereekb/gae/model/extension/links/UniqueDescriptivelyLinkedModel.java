package com.dereekb.gae.model.extension.links;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * A {@link UniqueModel} that also implements {@link DescriptivelyLinkedModel}.
 * 
 * @author dereekb
 *
 */
public interface UniqueDescriptivelyLinkedModel
        extends UniqueModel, DescriptivelyLinkedModel {

}
