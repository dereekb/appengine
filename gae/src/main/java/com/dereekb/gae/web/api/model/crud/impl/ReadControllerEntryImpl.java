package com.dereekb.gae.web.api.model.crud.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.inclusion.reader.InclusionReaderSetAnalysis;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReader;
import com.dereekb.gae.model.extension.inclusion.reader.impl.EmptyInclusionReaderSetAnalysis;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.crud.controller.ReadControllerEntry;
import com.dereekb.gae.web.api.model.crud.controller.ReadControllerEntryRequest;
import com.dereekb.gae.web.api.model.crud.controller.ReadControllerEntryResponse;

/**
 * {@link ReadControllerEntry} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ReadControllerEntryImpl<T extends UniqueModel>
        implements ReadControllerEntry {

	private ReadService<T> readService;
	private DirectionalConverter<T, ? extends Object> dtoConverter;

	private ModelInclusionReader<T> inclusionReader;

	public ReadControllerEntryImpl(ReadService<T> readService, DirectionalConverter<T, ? extends Object> dtoConverter) {
		this(readService, dtoConverter, null);
	}

	public ReadControllerEntryImpl(ReadService<T> readService,
	        DirectionalConverter<T, ? extends Object> dtoConverter,
	        ModelInclusionReader<T> inclusionReader) {
		this.setReadService(readService);
		this.setDtoConverter(dtoConverter);
		this.setInclusionReader(inclusionReader);
	}

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		if (readService == null) {
			throw new IllegalArgumentException("readService cannot be null.");
		}

		this.readService = readService;
	}

	public DirectionalConverter<T, ? extends Object> getDtoConverter() {
		return this.dtoConverter;
	}

	public void setDtoConverter(DirectionalConverter<T, ? extends Object> dtoConverter) {
		if (dtoConverter == null) {
			throw new IllegalArgumentException("dtoConverter cannot be null.");
		}

		this.dtoConverter = dtoConverter;
	}

	public ModelInclusionReader<T> getInclusionReader() {
		return this.inclusionReader;
	}

	public void setInclusionReader(ModelInclusionReader<T> inclusionReader) {
		this.inclusionReader = inclusionReader;
	}

	// MARK: ReadControllerEntry
	@Override
	public ReadControllerEntryResponse read(ReadControllerEntryRequest request) throws AtomicOperationException {
		ReadControllerEntryResponseImpl response = new ReadControllerEntryResponseImpl();

		// Read
		boolean atomic = request.isAtomic();
		Collection<ModelKey> keys = request.getModelKeys();
		ReadRequest readRequest = new KeyReadRequest(keys, atomic);
		ReadResponse<T> readResponse = this.readService.read(readRequest);

		// Available
		Collection<T> available = readResponse.getModels();
		Collection<? extends Object> dtos = this.dtoConverter.convert(available);
		response.setResponseModels(dtos);

		// Unavailable
		List<ModelKey> unavailable = new ArrayList<ModelKey>();
		unavailable.addAll(readResponse.getFiltered());
		unavailable.addAll(readResponse.getUnavailable());
		response.setUnavailableModelKeys(unavailable);

		// Analysis
		if (request.loadRelatedTypes()) {
			InclusionReaderSetAnalysis analysis = null;
			
			if (this.inclusionReader != null) { 
				analysis = this.inclusionReader.analyzeInclusionsForModels(available);
			} else {
				analysis = EmptyInclusionReaderSetAnalysis.make();
			}
			
			response.setAnalysis(analysis);
		}

		return response;
	}

	@Override
	public String toString() {
		return "ReadControllerEntryImpl [readService=" + this.readService + ", inclusionReader=" + this.inclusionReader
		        + ", dtoConverter=" + this.dtoConverter + "]";
	}

}
