package com.dereekb.gae.web.api.model.controller.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.inclusion.service.ConfiguredInclusionService;
import com.dereekb.gae.model.extension.inclusion.service.InclusionResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.model.controller.ReadModelControllerDelegate;

/**
 * {@link ReadModelControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class ReadModelControllerDelegateImpl<T extends UniqueModel>
        implements ReadModelControllerDelegate<T> {

	private ReadService<T> readService;
	private ConfiguredInclusionService<T> inclusionService;

	public ReadModelControllerDelegateImpl(ReadService<T> readService) {
		this(readService, null);
	}

	public ReadModelControllerDelegateImpl(ReadService<T> readService, ConfiguredInclusionService<T> inclusionService) {
		this.readService = readService;
		this.inclusionService = inclusionService;
	}

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		this.readService = readService;
	}

	public ConfiguredInclusionService<T> getInclusionService() {
		return this.inclusionService;
	}

	public void setInclusionService(ConfiguredInclusionService<T> inclusionService) {
		this.inclusionService = inclusionService;
	}

	@Override
	public ReadResponse<T> read(ReadRequest<T> readRequest) {
		return this.readService.read(readRequest);
	}

	@Override
	public Map<String, Object> readIncluded(Collection<T> models) {
		Map<String, Object> included;

		if (this.inclusionService != null) {
			InclusionResponse<T> response = this.inclusionService.loadRelated(models);

			Map<String, Collection<? extends UniqueModel>> responseMap = response.getRelated();
			included = new HashMap<String, Object>();

			for (String type : responseMap.keySet()) {
				Collection<? extends UniqueModel> related = responseMap.get(type);
				included.put(type, related);
			}

		} else {
			included = Collections.emptyMap();
		}

		return included;
	}

}
