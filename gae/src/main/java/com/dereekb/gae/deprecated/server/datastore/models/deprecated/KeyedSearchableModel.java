package com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated;

import com.thevisitcompany.gae.server.search.UniqueSearchModel;

/**
 * Interface that extends the {@link KeyedModel} and {@link UniqueSearchModel} interface.
 *
 * @author dereekb
 *
 * @param <K>
 *            Key type of the KeyedModel.
 */
@Deprecated
public interface KeyedSearchableModel<K>
        extends KeyedModel<K>, UniqueSearchModel {

}
