package com.dereekb.gae.model.extension.links.deleter.impl;

import java.util.Collection;

import com.dereekb.gae.model.extension.links.deprecated.deleter.LinkDeleterServiceRequest;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * {@link LinkDeleterServiceRequest} implementation.
 *
 * @author dereekb
 *
 */
@Deprecated
public class LinkDeleterServiceRequestImpl
        implements LinkDeleterServiceRequest {

	private String modelType;
	private Collection<ModelKey> targetKeys;

	public LinkDeleterServiceRequestImpl(String modelType, ModelKey targetKey) {
		this(modelType, SingleItem.withValue(targetKey));
	}

	public LinkDeleterServiceRequestImpl(String modelType, Collection<ModelKey> targetKeys) {
		this.setModelType(modelType);
		this.setTargetKeys(targetKeys);
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Override
	public Collection<ModelKey> getTargetKeys() {
		return this.targetKeys;
	}

	public void setTargetKeys(Collection<ModelKey> targetKeys) {
		this.targetKeys = targetKeys;
	}

	@Override
	public String toString() {
		return "LinkDeleterServiceRequestImpl [modelType=" + this.modelType + ", targetKeys=" + this.targetKeys + "]";
	}

}
