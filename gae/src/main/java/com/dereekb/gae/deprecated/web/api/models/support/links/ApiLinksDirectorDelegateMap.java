package com.thevisitcompany.gae.deprecated.web.api.models.support.links;

import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;
import com.thevisitcompany.gae.utilities.collections.map.CatchMap;

public class ApiLinksDirectorDelegateMap<K> extends CatchMap<ApiLinksDirectorDelegate<K>>
        implements ApiLinksDirectorDelegate<K> {

	@Override
	public ApiResponse handleLinksChange(ApiLinksChange<K> changes) {
		String type = changes.getType();

		ApiResponse response = null;
		ApiLinksDirectorDelegate<K> delegate = this.get(type);

		if (delegate != null) {
			response = delegate.handleLinksChange(changes);
		}

		return response;
	}

}
