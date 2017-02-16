package com.dereekb.gae.web.api.model.crud.controller;

import java.util.Collection;

import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ReadControllerEntry} response.
 *
 * @author dereekb
 *
 */
public interface ReadControllerEntryResponse {

	public Collection<Object> getResponseModels();

	public Collection<ModelKey> getUnavailableModelKeys();

	public InclusionReaderSetAnalysis getAnalysis();

}
