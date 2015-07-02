package com.dereekb.gae.model.stored.blob.dto;

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

	private String infoType;

	private String infoIdentifier;

	public String getDownload() {
		return this.download;
	}

	public void setDownload(String download) {
		this.download = download;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getInfoIdentifier() {
		return this.infoIdentifier;
	}

	public void setInfoIdentifier(String infoIdentifier) {
		this.infoIdentifier = infoIdentifier;
	}

	@Override
	public String toString() {
		return "StoredBlobData [download=" + this.download + ", infoType=" + this.infoType + ", infoIdentifier="
		        + this.infoIdentifier + ", identifier=" + this.identifier + ", created=" + this.created + "]";
	}

}
