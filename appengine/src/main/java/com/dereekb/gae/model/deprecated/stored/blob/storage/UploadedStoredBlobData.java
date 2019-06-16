package com.dereekb.gae.model.stored.blob.storage;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link StoredBlobUploadHandler} response data.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
public class UploadedStoredBlobData {

	private String identifier;
	private String downloadKey;
	private Long size;

	private String descriptorType;
	private String descriptorId;

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDownloadKey() {
		return this.downloadKey;
	}

	public void setDownloadKey(String downloadKey) {
		this.downloadKey = downloadKey;
	}

	public Long getSize() {
		return this.size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public void setModelKey(ModelKey key) {
		this.identifier = key.keyAsString();
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

	public void setDescriptor(Descriptor descriptor) {
		this.descriptorId = descriptor.getDescriptorId();
		this.descriptorType = descriptor.getDescriptorType();
	}

	@Override
	public String toString() {
		return "UploadedStoredBlobData [identifier=" + this.identifier + ", descriptorType=" + this.descriptorType
		        + ", descriptorId=" + this.descriptorId + "]";
	}

}
