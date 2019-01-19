package com.dereekb.gae.utilities.gae.impl;

import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.gae.GoogleAppEngineContextualFactory;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;
import com.dereekb.gae.utilities.model.source.Source;
import com.dereekb.gae.utilities.model.source.impl.SourceImpl;

/**
 * {@link GoogleAppEngineContextualFactory} implementation.
 * <p>
 * Uses {@link Source}s to enable lazy-loading.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class GoogleAppEngineContextualFactoryImpl<T>
        implements GoogleAppEngineContextualFactory<T> {

	private boolean assertNotNull = false;

	private Source<T> defaultSource = SourceImpl.nullSource();
	private Source<T> productionSource = SourceImpl.nullSource();
	private Source<T> developmentSource = SourceImpl.nullSource();
	private Source<T> testSource = SourceImpl.nullSource();

	public GoogleAppEngineContextualFactoryImpl() {
		super();
	}

	public GoogleAppEngineContextualFactoryImpl(boolean assertNotNull, Source<T> defaultSource) {
		this(assertNotNull);
		this.setDefaultSource(defaultSource);
	}

	public GoogleAppEngineContextualFactoryImpl(boolean assertNotNull) {
		super();
		this.setAssertNotNull(assertNotNull);
	}

	public boolean isAssertNotNull() {
		return this.assertNotNull;
	}

	public void setAssertNotNull(boolean assertNotNull) {
		this.assertNotNull = assertNotNull;
	}

	public Source<T> getDefaultSource() {
		return this.defaultSource;
	}

	public void setDefaultSource(Source<T> defaultSource) {
		if (defaultSource == null) {
			throw new IllegalArgumentException("defaultSource cannot be null.");
		}

		this.defaultSource = defaultSource;
	}

	public Source<T> getProductionSource() {
		return this.productionSource;
	}

	public void setProductionSource(Source<T> productionSource) {
		if (productionSource == null) {
			throw new IllegalArgumentException("productionSource cannot be null.");
		}

		this.productionSource = productionSource;
	}

	public Source<T> getDevelopmentSource() {
		return this.developmentSource;
	}

	public void setDevelopmentSource(Source<T> developmentSource) {
		if (developmentSource == null) {
			throw new IllegalArgumentException("developmentSource cannot be null.");
		}

		this.developmentSource = developmentSource;
	}

	public Source<T> getTestSource() {
		return this.testSource;
	}

	public void setTestSource(Source<T> testSource) {
		if (testSource == null) {
			throw new IllegalArgumentException("testSource cannot be null.");
		}

		this.testSource = testSource;
	}

	public T getDefaultSingleton() {
		return this.defaultSource.loadObject();
	}

	public void setDefaultSingleton(T defaultSingleton) {
		this.defaultSource = SourceImpl.make(defaultSingleton);
	}

	public T getProductionSingleton() {
		return this.productionSource.loadObject();
	}

	public void setProductionSingleton(T productionSingleton) {
		this.productionSource = SourceImpl.make(productionSingleton);
	}

	public T getDevelopmentSingleton() {
		return this.developmentSource.loadObject();
	}

	public void setDevelopmentSingleton(T developmentSingleton) {
		this.developmentSource = SourceImpl.make(developmentSingleton);
	}

	public T getTestSingleton() {
		return this.testSource.loadObject();
	}

	public void setTestSingleton(T testSingleton) {
		this.testSource = SourceImpl.make(testSingleton);
	}

	// MARK: Make
	@Override
	public T make() throws FactoryMakeFailureException {
		T item = null;

		switch (GoogleAppEngineUtility.getEnvironmentType()) {
			case DEVELOPMENT:
				item = this.getDevelopmentSingleton();

				if (item != null) {
					break;
				}

				// Fall-through otherwise to production.
			case PRODUCTION:
				item = this.getProductionSingleton();
				break;
			case UNIT_TESTING:
				item = this.getTestSingleton();
				break;
			default:
				break;
		}

		if (item == null) {
			item = this.getDefaultSingleton();

			if (item == null && this.assertNotNull) {
				throw new NullPointerException();
			}
		}

		return item;
	}

	@Override
	public String toString() {
		return "GoogleAppEngineContextualFactoryImpl [assertNotNull=" + this.assertNotNull + ", defaultSource="
		        + this.defaultSource + ", productionSource=" + this.productionSource + ", developmentSource="
		        + this.developmentSource + ", testSource=" + this.testSource + "]";
	}

}
