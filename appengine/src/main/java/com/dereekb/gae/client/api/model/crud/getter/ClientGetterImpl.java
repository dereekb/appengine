package com.dereekb.gae.client.api.model.crud.getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.exception.UninitializedModelException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.datastore.models.keys.accessor.impl.LoadedModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.impl.ModelKeyListAccessorImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;

/**
 * {@link Getter} and {@link ModelKeyListAccessor} implementation that uses
 * {@link ClientReadRequestSender}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ClientGetterImpl<T extends UniqueModel> extends TypedModelImpl
        implements Getter<T>, ModelKeyListAccessorFactory<T> {

	private ClientReadRequestSender<T> readRequestSender;
	private transient StringModelKeyConverter stringModelKeyTypeConverter;

	public ClientGetterImpl(ClientReadRequestSender<T> readRequestSender) {
		super();
		this.setReadRequestSender(readRequestSender);
	}

	public ClientReadRequestSender<T> getReadRequestSender() {
		return this.readRequestSender;
	}

	public void setReadRequestSender(ClientReadRequestSender<T> readRequestSender) {
		if (readRequestSender == null) {
			throw new IllegalArgumentException("readRequestSender cannot be null.");
		}

		this.readRequestSender = readRequestSender;
		this.stringModelKeyTypeConverter = ModelKey.converterForKeyType(readRequestSender.getModelKeyType());
	}

	// MARK: Getter
	@Override
	public T get(T model) throws UninitializedModelException {
		ModelKey key = model.getModelKey();

		if (key == null) {
			throw new UninitializedModelException();
		}

		return this.get(key);
	}

	@Override
	public List<T> get(Iterable<T> models) throws UninitializedModelException {
		return this.getWithKeys(ModelKey.readModelKeys(models));
	}

	@Override
	public T get(ModelKey key) throws IllegalArgumentException {
		ReadRequest request = new KeyReadRequest(key);

		try {
			return this.getWithRequest(request).get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public List<T> getWithKeys(Iterable<ModelKey> keys) {
		ReadRequest request = new KeyReadRequest(keys);
		return this.getWithRequest(request);
	}

	protected List<T> getWithRequest(ReadRequest request) {
		try {
			SimpleReadResponse<T> response = this.readRequestSender.read(request,
			        ClientRequestSecurityImpl.systemSecurity());
			return new ArrayList<T>(response.getModels());
		} catch (ClientRequestFailureException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean exists(T model) throws UninitializedModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists(ModelKey key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean allExist(Iterable<ModelKey> keys) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<ModelKey> getExisting(Iterable<ModelKey> keys) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	// MARK: ModelKeyListAccessorFactory<T>
	@Override
	public ModelKeyListAccessor<T> createAccessor() {
		return new ModelKeyListAccessorImpl<T>(this.getModelType(), this);
	}

	@Override
	public ModelKeyListAccessor<T> createAccessor(Collection<ModelKey> keys) {
		return new ModelKeyListAccessorImpl<T>(this.getModelType(), this, keys);
	}

	@Override
	public ModelKeyListAccessor<T> createAccessorWithStringKeys(Collection<String> keys) {
		List<ModelKey> modelKeys = this.stringModelKeyTypeConverter.convert(keys);
		return this.createAccessor(modelKeys);
	}

	@Override
	public ModelKeyListAccessor<T> createAccessorWithModels(Collection<T> models) {
		return new LoadedModelKeyListAccessor<T>(this.getModelType(), models);
	}

	@Override
	public String toString() {
		return "ClientGetterImpl [readRequestSender=" + this.readRequestSender + "]";
	}

}
