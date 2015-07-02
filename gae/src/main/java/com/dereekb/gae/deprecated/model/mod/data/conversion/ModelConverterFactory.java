package com.thevisitcompany.gae.deprecated.model.mod.data.conversion;

import com.thevisitcompany.gae.utilities.factory.Factory;

@Deprecated
public class ModelConverterFactory<T, D>
        implements Factory<ModelConverter<T, D>> {

	private Class<ModelConversionDelegate<T, D>> delegateClass;
	private Factory<ModelConversionDelegate<T, D>> delegateFactory;

	public ModelConverterFactory() {}

	@Override
	public ModelConverter<T, D> make() {
		ModelConversionDelegate<T, D> delegate = null;

		if (this.delegateFactory != null) {
			delegate = this.delegateFactory.make();
		} else {
			try {
				delegate = this.delegateClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Could not create a new ModelConversionDelegate of type '"
				        + this.delegateClass + "'.");
			}
		}

		ModelConverter<T, D> converter = new ModelConverter<T, D>(delegate);
		return converter;
	}

	public Class<ModelConversionDelegate<T, D>> getDelegateClass() {
		return this.delegateClass;
	}

	public void setDelegateClass(Class<ModelConversionDelegate<T, D>> delegateClass) {
		this.delegateClass = delegateClass;
	}

	public Factory<ModelConversionDelegate<T, D>> getDelegateFactory() {
		return this.delegateFactory;
	}

	public void setDelegateFactory(Factory<ModelConversionDelegate<T, D>> delegateFactory) {
		this.delegateFactory = delegateFactory;
	}

}
