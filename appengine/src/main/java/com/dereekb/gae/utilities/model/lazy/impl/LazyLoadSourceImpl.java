package com.dereekb.gae.utilities.model.lazy.impl;

import com.dereekb.gae.utilities.model.lazy.LazyLoadSource;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;

/**
 * {@link LazyLoadSource} implementation that wraps another {@link Source}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LazyLoadSourceImpl<T>
        implements LazyLoadSource<T> {

	private boolean loaded;
	private T model;
	private RuntimeException e;

	private Source<T> delegate;

	public LazyLoadSourceImpl(Source<T> delegate) {
		super();
		this.setDelegate(delegate);
	}

	public Source<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(Source<T> delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: LazyLoadedModel
	@Override
	public boolean hasTriedLoading() {
		return this.loaded;
	}

	@Override
	public boolean hasModel() {
		return (this.model != null);
	}

	@Override
	public T safeLoadObject() {
		if (this.loaded == false) {
			try {
				return this.loadObject();
			} catch (UnavailableSourceObjectException e) {
				// Do nothing.
			} catch (RuntimeException e) {
				throw e;
			} finally {
				this.loaded = true;
			}
		}

		return this.model;
	}

	@Override
	public T loadObject() throws RuntimeException, UnavailableSourceObjectException {
		if (this.model == null) {
			// If loaded is true, then encountered exception previously.
			if (this.loaded) {
				throw this.e;
			} else {
				// Try loading the model.
				try {
					this.model = this.getDelegate().loadObject();
				} catch (RuntimeException e) {
					this.e = e;
					throw e;
				} finally {
					this.loaded = true;
				}
			}
		}

		return this.model;
	}

	@Override
	public String toString() {
		return "LazyLoadedModelImpl [loaded=" + this.loaded + ", model=" + this.model + ", delegate=" + this.delegate
		        + "]";
	}

}
