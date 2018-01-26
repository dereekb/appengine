package com.dereekb.gae.test.extras.gen.app.model;

import com.dereekb.gae.model.extension.data.conversion.ModelDataConversionInfo;

public interface AppModelConfiguration
        extends ModelDataConversionInfo<Object, Object> {

	/**
	 * Returns the bean model name/prefix, which is usually
	 * {{@link #getModelType()} with the first character to lowercase.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getModelBeanPrefix();

	public String getModelTypeBeanId();

	public String getModelClassBeanId();

	public String getModelDtoBeanId();

	public String getModelDataConverterBeanId();

}
