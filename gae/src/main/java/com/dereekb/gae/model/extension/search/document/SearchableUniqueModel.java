package com.dereekb.gae.model.extension.search.document;

import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.search.UniqueSearchModel;

/**
 * Interface that extends the {@link UniqueModel} and {@link UniqueSearchModel}
 * interface.
 *
 * @author dereekb
 */
public interface SearchableUniqueModel
        extends MutableUniqueModel, UniqueSearchModel {

}
