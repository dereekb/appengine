package com.dereekb.gae.model.extension.links.descriptor.impl.dto;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedDatabaseModel;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescriptorImpl;
import com.dereekb.gae.model.extension.search.document.search.dto.SearchableDatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Abstract Data Transfer Object class for {@link DescribedDatabaseModel}
 * instances.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DescribedDatabaseModelData extends SearchableDatabaseModelData {

	private static final long serialVersionUID = 1L;

	protected DescriptorImpl descriptor;

	public DescriptorImpl getDescriptor() {
		return this.descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = DescriptorImpl.withValue(descriptor);
	}

}
