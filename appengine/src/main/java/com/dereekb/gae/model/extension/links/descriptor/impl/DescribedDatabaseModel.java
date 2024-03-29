package com.dereekb.gae.model.extension.links.descriptor.impl;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.UniqueDescribedModel;
import com.dereekb.gae.server.datastore.models.owner.OwnedDatabaseModel;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNotNull;

/**
 * {@link SearchableDatabaseModel} extension for models that are described by another model.
 *
 * @author dereekb
 *
 */
public abstract class DescribedDatabaseModel extends OwnedDatabaseModel
        implements UniqueDescribedModel {

	private static final long serialVersionUID = 1L;

	// Info
	/**
	 * (Optional) {@link Descriptor} model's type.
	 */
	@Index({ IfNotNull.class })
	@IgnoreSave({ IfEmpty.class })
	protected String descriptorType;

	/**
	 * (Optional) {@link Descriptor} model's identifier.
	 */
	@IgnoreSave({ IfEmpty.class })
	protected String descriptorId;

	public DescribedDatabaseModel() {
		super();
	}

	public String getDescriptorType() {
		return this.descriptorType;
	}

	public void setDescriptorType(String descriptorType) {
		this.descriptorType = descriptorType;
	}

	public String getDescriptorId() {
		return this.descriptorId;
	}

	public void setDescriptorId(String descriptorId) {
		this.descriptorId = descriptorId;
	}

	@Override
	public Descriptor getDescriptor() {
		return DescriptorImpl.withValues(this.descriptorType, this.descriptorId);
	}

	@Override
	public void setDescriptor(Descriptor descriptor) {
		if (descriptor == null) {
			this.descriptorType = null;
			this.descriptorId = null;
		} else {
			this.descriptorType = descriptor.getDescriptorType();
			this.descriptorId = descriptor.getDescriptorId();
		}
	}

}
