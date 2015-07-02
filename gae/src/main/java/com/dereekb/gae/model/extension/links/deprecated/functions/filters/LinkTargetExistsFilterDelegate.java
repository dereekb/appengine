package com.dereekb.gae.model.extension.links.deprecated.functions.filters;

import java.util.Collection;

import com.dereekb.gae.model.crud.function.filters.misc.ExistsFilterDelegate;
import com.dereekb.gae.model.extension.links.deprecated.functions.LinksPair;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

public class LinkTargetExistsFilterDelegate<W extends LinksPair<?>>
        implements ExistsFilterDelegate<W> {

	@Override
	public Collection<ModelKey> getKeysForExistCheck(W item) {
		return item.getLinks();
	}

}
