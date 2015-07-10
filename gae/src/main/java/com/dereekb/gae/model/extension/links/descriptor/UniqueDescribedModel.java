package com.dereekb.gae.model.extension.links.descriptor;

import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * A {@link UniqueModel} that also implements {@link DescribedModel}.
 * 
 * @author dereekb
 *
 */
public interface UniqueDescribedModel
        extends UniqueModel, DescribedModel {

}
