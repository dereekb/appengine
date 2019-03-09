package com.dereekb.gae.model.extension.search.document;

import com.dereekb.gae.model.extension.links.descriptor.UniqueDescribedModel;

/**
 * {@link SearchableUniqueModel} and {@link UniqueDescribedModel} union.
 * 
 * @author dereekb
 *
 */
public interface SearchableDescribedModel
        extends SearchableUniqueModel, UniqueDescribedModel {

}
