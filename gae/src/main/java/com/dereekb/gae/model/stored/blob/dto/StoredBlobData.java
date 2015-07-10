package com.dereekb.gae.model.stored.blob.dto;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.DescriptorImpl;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO of the {@link StoredBlob} class.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class StoredBlobData extends DatabaseModelData {

	private static final long serialVersionUID = 1L;

	private String download;

	private DescriptorImpl descriptor;

	public String getDownload() {
		return this.download;
	}

	public void setDownload(String download) {
		this.download = download;
	}

	public DescriptorImpl getDescriptor() {
		return this.descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = DescriptorImpl.withValue(descriptor);
	}

	@Override
	public String toString() {
		return "StoredBlobData [download=" + this.download + ", descriptor=" + this.descriptor + "]";
	}

}
