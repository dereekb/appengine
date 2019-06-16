package com.dereekb.gae.model.extension.links.descriptor.impl.dto;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedDatabaseModel;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescriptorImpl;
import com.dereekb.gae.server.datastore.models.dto.OwnedDatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Abstract Data Transfer Object class for {@link DescribedDatabaseModel}
 * instances.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DescribedDatabaseModelData extends OwnedDatabaseModelData {

	private static final long serialVersionUID = 1L;

	protected DescriptorImpl descriptor;

	public DescriptorImpl getDescriptor() {
		return this.descriptor;
	}

	@JsonDeserialize(as = DescriptorData.class)
	public void setDescriptor(Descriptor descriptor) {
		DescriptorData data = DescriptorData.withValue(descriptor);

		if (data != null && data.isValid()) {
			this.descriptor = DescriptorImpl.withValue(descriptor);
		} else {
			this.descriptor = null;
		}
	}

}
