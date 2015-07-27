package com.dereekb.gae.model.extension.read.wrappers;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.ReadResponseImpl;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ReadService} implementation that wraps another {@link ReadService} and
 * uses a {@link DirectionalConverter} to convert the results.
 *
 * @author dereekb
 *
 */
public class ConversionReadService<T extends UniqueModel, I extends UniqueModel>
        implements ReadService<I> {

	private ReadService<T> service;
	private DirectionalConverter<T, I> converter;

	public ConversionReadService(ReadService<T> service, DirectionalConverter<T, I> converter) {
		this.service = service;
		this.converter = converter;
	}

	public ReadService<T> getService() {
		return this.service;
	}

	public void setService(ReadService<T> service) {
		this.service = service;
	}

	public DirectionalConverter<T, I> getConverter() {
		return this.converter;
	}

	public void setConverter(DirectionalConverter<T, I> converter) {
		this.converter = converter;
	}

	@Override
	public ReadResponse<I> read(ReadRequest request) throws AtomicOperationException {
		ReadResponse<T> response = this.service.read(request);
		Collection<T> models = response.getModels();
		List<I> convertedModels = this.converter.convert(models);
		return new ReadResponseImpl<I>(convertedModels, response.getFiltered(), response.getUnavailable());
	}

	@Override
	public String toString() {
		return "ConversionReadService [service=" + this.service + ", converter=" + this.converter + "]";
	}

}
