package com.dereekb.gae.server.datastore.utility.source;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.model.lazy.LazyLoadSource;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.lazy.impl.LazyLoadSourceImpl;
import com.dereekb.gae.utilities.model.lazy.impl.UnavailableLazyLoadSourceImpl;
import com.dereekb.gae.utilities.model.source.Source;
import com.dereekb.gae.utilities.model.source.impl.SourceImpl;
import com.dereekb.gae.utilities.model.source.impl.UnavailableSourceImpl;

/**
 * {@link LazyLoadSourceDelegate} implementation with a {@link Getter}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelKeyGetterSourceImpl<T extends UniqueModel>
        implements Source<T> {

	private Getter<T> getter;
	private Source<ModelKey> keySource;

	public ModelKeyGetterSourceImpl(Getter<T> getter, ModelKey key) {
		this(getter, SourceImpl.make(key));
	}

	public ModelKeyGetterSourceImpl(Getter<T> getter, Source<ModelKey> source) {
		super();
		this.setGetter(getter);
		this.setKeySource(source);
	}

	public static <T extends UniqueModel> LazyLoadSource<T> makeLazySource(Getter<T> getter,
	                                                                       Source<ModelKey> source) {
		if (getter == null) {
			return UnavailableLazyLoadSourceImpl.make();
		} else {
			Source<T> delegate = ModelKeyGetterSourceImpl.make(getter, source);
			return new LazyLoadSourceImpl<T>(delegate);
		}
	}

	public static <T extends UniqueModel> Source<T> make(Getter<T> getter,
	                                                     Source<ModelKey> source) {
		if (getter == null) {
			return UnavailableSourceImpl.make();
		} else {
			return new ModelKeyGetterSourceImpl<T>(getter, source);
		}
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	public Source<ModelKey> getKeySource() {
		return this.keySource;
	}

	public void setKeySource(Source<ModelKey> keySource) {
		if (keySource == null) {
			throw new IllegalArgumentException("keySource cannot be null.");
		}

		this.keySource = keySource;
	}

	// MARK: Source
	@Override
	public T loadObject() throws RuntimeException, UnavailableSourceObjectException {
		ModelKey key = this.keySource.loadObject();
		T model = this.getter.get(key);

		if (model == null) {
			throw new UnavailableSourceObjectException("Model is unavailable.");
		}

		return model;
	}

	@Override
	public String toString() {
		return "LazyLoadedModelGetterDelegateImpl [getter=" + this.getter + ", keySource=" + this.keySource + "]";
	}

}
