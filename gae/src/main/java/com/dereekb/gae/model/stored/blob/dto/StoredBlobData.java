package com.dereekb.gae.model.stored.blob.dto;

import com.dereekb.gae.model.extension.links.descriptor.impl.dto.DescribedDatabaseModelData;
import com.dereekb.gae.model.stored.blob.StoredBlob;
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
public final class StoredBlobData extends DescribedDatabaseModelData {

	private static final long serialVersionUID = 1L;

	private String download;

	public String getDownload() {
		return this.download;
	}

	public void setDownload(String download) {
		this.download = download;
	}

	@Override
	public String toString() {
		return "StoredBlobData [download=" + this.download + ", descriptor=" + this.descriptor + ", searchIdentifier="
		        + this.searchIdentifier + ", identifier=" + this.identifier + ", created=" + this.created + "]";
	}

}
