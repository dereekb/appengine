package com.dereekb.gae.web.api.model.controller.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.controller.ReadControllerEntryResponse;

/**
 * {@link ReadControllerEntryResponse} implementation.
 *
 * @author dereekb
 *
 */
public class ReadControllerEntryResponseImpl
        implements ReadControllerEntryResponse {

	private Collection<Object> responseModels;
	private Collection<ModelKey> unavailableModelKeys;
	private InclusionReaderSetAnalysis analysis;

	@Override
	public Collection<Object> getResponseModels() {
		return this.responseModels;
	}

	public void setResponseModels(Collection<? extends Object> responseModels) {
		if (responseModels != null) {
			this.responseModels = new ArrayList<Object>(responseModels);
		} else {
			this.responseModels = Collections.emptyList();
		}
	}

	@Override
	public Collection<ModelKey> getUnavailableModelKeys() {
		return this.unavailableModelKeys;
	}

	public void setUnavailableModelKeys(Collection<ModelKey> unavailableModelKeys) {
		this.unavailableModelKeys = unavailableModelKeys;
	}

	@Override
	public InclusionReaderSetAnalysis getAnalysis() {
		return this.analysis;
	}

	public void setAnalysis(InclusionReaderSetAnalysis analysis) {
		this.analysis = analysis;
	}

	@Override
	public String toString() {
		return "ReadControllerEntryResponseImpl [responseModels=" + this.responseModels + ", unavailableModelKeys="
		        + this.unavailableModelKeys + ", analysis=" + this.analysis + "]";
	}

}
