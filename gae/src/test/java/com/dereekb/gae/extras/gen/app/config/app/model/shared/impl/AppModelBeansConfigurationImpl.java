package com.dereekb.gae.extras.gen.app.config.app.model.shared.impl;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelBeansConfiguration;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link AppModelBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppModelBeansConfigurationImpl
        implements AppModelBeansConfiguration {

	private ModelKeyType modelKeyType;

	private String modelBeanPrefix;
	private String modelTypeBeanId;
	private String modelIdTypeBeanId;
	private String modelClassBeanId;
	private String modelDtoClassBeanId;
	private String modelDtoBeanId;
	private String modelDataConverterBeanId;

	public AppModelBeansConfigurationImpl(String modelType, ModelKeyType modelKeyType) {
		String modelBeanPrefix = StringUtility.firstLetterLowerCase(modelType);

		this.setModelKeyType(modelKeyType);
		this.setModelBeanPrefix(modelBeanPrefix);
		this.setModelTypeBeanId(modelBeanPrefix + "Type");
		this.setModelIdTypeBeanId(modelBeanPrefix + "IdType");
		this.setModelClassBeanId(modelBeanPrefix + "Class");
		this.setModelDtoClassBeanId(modelBeanPrefix + "DtoClass");
		this.setModelDtoBeanId(modelBeanPrefix + "DtoClass");
		this.setModelDataConverterBeanId(modelBeanPrefix + "DataConverter");
	}

	public ModelKeyType getModelKeyType() {
		return this.modelKeyType;
	}

	public void setModelKeyType(ModelKeyType modelKeyType) {
		if (modelKeyType == null) {
			throw new IllegalArgumentException("modelKeyType cannot be null.");
		}

		this.modelKeyType = modelKeyType;
	}

	@Override
	public String getModelBeanPrefix() {
		return this.modelBeanPrefix;
	}

	public void setModelBeanPrefix(String modelBeanPrefix) {
		if (modelBeanPrefix == null) {
			throw new IllegalArgumentException("modelBeanPrefix cannot be null.");
		}

		this.modelBeanPrefix = modelBeanPrefix;
	}

	@Override
	public String getModelTypeBeanId() {
		return this.modelTypeBeanId;
	}

	public void setModelTypeBeanId(String modelTypeBeanId) {
		if (modelTypeBeanId == null) {
			throw new IllegalArgumentException("modelTypeBeanId cannot be null.");
		}

		this.modelTypeBeanId = modelTypeBeanId;
	}

	@Override
	public String getModelIdTypeBeanId() {
		return this.modelIdTypeBeanId;
	}

	public void setModelIdTypeBeanId(String modelIdTypeBeanId) {
		if (modelIdTypeBeanId == null) {
			throw new IllegalArgumentException("modelIdTypeBeanId cannot be null.");
		}

		this.modelIdTypeBeanId = modelIdTypeBeanId;
	}

	@Override
	public String getModelClassBeanId() {
		return this.modelClassBeanId;
	}

	public void setModelClassBeanId(String modelClassBeanId) {
		if (modelClassBeanId == null) {
			throw new IllegalArgumentException("modelClassBeanId cannot be null.");
		}

		this.modelClassBeanId = modelClassBeanId;
	}

	@Override
	public String getModelDtoClassBeanId() {
		return this.modelDtoClassBeanId;
	}

	public void setModelDtoClassBeanId(String modelDtoClassBeanId) {
		if (modelDtoClassBeanId == null) {
			throw new IllegalArgumentException("modelDtoClassBeanId cannot be null.");
		}

		this.modelDtoClassBeanId = modelDtoClassBeanId;
	}

	@Override
	public String getModelDtoBeanId() {
		return this.modelDtoBeanId;
	}

	public void setModelDtoBeanId(String modelDtoBeanId) {
		if (modelDtoBeanId == null) {
			throw new IllegalArgumentException("modelDtoBeanId cannot be null.");
		}

		this.modelDtoBeanId = modelDtoBeanId;
	}

	@Override
	public String getModelDataConverterBeanId() {
		return this.modelDataConverterBeanId;
	}

	public void setModelDataConverterBeanId(String modelDataConverterBeanId) {
		if (modelDataConverterBeanId == null) {
			throw new IllegalArgumentException("modelDataConverterBeanId cannot be null.");
		}

		this.modelDataConverterBeanId = modelDataConverterBeanId;
	}

}
