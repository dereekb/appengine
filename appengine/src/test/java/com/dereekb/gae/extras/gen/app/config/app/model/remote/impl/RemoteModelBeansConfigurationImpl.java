package com.dereekb.gae.extras.gen.app.config.app.model.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.impl.AppModelBeansConfigurationImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link RemoteModelBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class RemoteModelBeansConfigurationImpl extends AppModelBeansConfigurationImpl
        implements RemoteModelBeansConfiguration {

	private String modelClientCrudServiceBeanId;
	private String modelClientCreateServiceBeanId;
	private String modelClientReadServiceBeanId;
	private String modelClientUpdateServiceBeanId;
	private String modelClientDeleteServiceBeanId;
	private String modelClientQueryServiceBeanId;

	public RemoteModelBeansConfigurationImpl(String modelType, ModelKeyType modelKeyType) {
		super(modelType, modelKeyType);
		String modelBeanPrefix = this.getModelBeanPrefix();

		this.setModelClientCrudServiceBeanId(modelBeanPrefix + "ClientCrudService");
		this.setModelClientCreateServiceBeanId(modelBeanPrefix + "ClientCreateService");
		this.setModelClientReadServiceBeanId(modelBeanPrefix + "ClientReadService");
		this.setModelClientUpdateServiceBeanId(modelBeanPrefix + "ClientUpdateService");
		this.setModelClientDeleteServiceBeanId(modelBeanPrefix + "ClientDeleteService");
		this.setModelClientQueryServiceBeanId(modelBeanPrefix + "ClientQueryService");
	}

	@Override
	public String getModelClientCrudServiceBeanId() {
		return this.modelClientCrudServiceBeanId;
	}

	public void setModelClientCrudServiceBeanId(String modelClientCrudServiceBeanId) {
		if (modelClientCrudServiceBeanId == null) {
			throw new IllegalArgumentException("modelClientCrudServiceBeanId cannot be null.");
		}

		this.modelClientCrudServiceBeanId = modelClientCrudServiceBeanId;
	}

	@Override
	public String getModelClientCreateServiceBeanId() {
		return this.modelClientCreateServiceBeanId;
	}

	public void setModelClientCreateServiceBeanId(String modelClientCreateServiceBeanId) {
		if (modelClientCreateServiceBeanId == null) {
			throw new IllegalArgumentException("modelClientCreateServiceBeanId cannot be null.");
		}

		this.modelClientCreateServiceBeanId = modelClientCreateServiceBeanId;
	}

	@Override
	public String getModelClientReadServiceBeanId() {
		return this.modelClientReadServiceBeanId;
	}

	public void setModelClientReadServiceBeanId(String modelClientReadServiceBeanId) {
		if (modelClientReadServiceBeanId == null) {
			throw new IllegalArgumentException("modelClientReadServiceBeanId cannot be null.");
		}

		this.modelClientReadServiceBeanId = modelClientReadServiceBeanId;
	}

	@Override
	public String getModelClientUpdateServiceBeanId() {
		return this.modelClientUpdateServiceBeanId;
	}

	public void setModelClientUpdateServiceBeanId(String modelClientUpdateServiceBeanId) {
		if (modelClientUpdateServiceBeanId == null) {
			throw new IllegalArgumentException("modelClientUpdateServiceBeanId cannot be null.");
		}

		this.modelClientUpdateServiceBeanId = modelClientUpdateServiceBeanId;
	}

	@Override
	public String getModelClientDeleteServiceBeanId() {
		return this.modelClientDeleteServiceBeanId;
	}

	public void setModelClientDeleteServiceBeanId(String modelClientDeleteServiceBeanId) {
		if (modelClientDeleteServiceBeanId == null) {
			throw new IllegalArgumentException("modelClientDeleteServiceBeanId cannot be null.");
		}

		this.modelClientDeleteServiceBeanId = modelClientDeleteServiceBeanId;
	}

	@Override
	public String getModelClientQueryServiceBeanId() {
		return this.modelClientQueryServiceBeanId;
	}

	public void setModelClientQueryServiceBeanId(String modelClientQueryServiceBeanId) {
		if (modelClientQueryServiceBeanId == null) {
			throw new IllegalArgumentException("modelClientQueryServiceBeanId cannot be null.");
		}

		this.modelClientQueryServiceBeanId = modelClientQueryServiceBeanId;
	}

}
