package com.dereekb.gae.model.stored.image.dto;

import com.dereekb.gae.server.datastore.models.dto.DatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO of the {@link StoredImageData} class.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class StoredImageData extends DatabaseModelData {

	private static final long serialVersionUID = 1L;

	private String name;

	private String summary;

	private String tags;

	private Integer type;

	private Long blobId;

	private Long placeId;

	public StoredImageData() {}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getBlobId() {
		return this.blobId;
	}

	public void setBlobId(Long blobId) {
		this.blobId = blobId;
	}

	public Long getPlaceId() {
		return this.placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	@Override
    public String toString() {
	    return "StoredImageData [name=" + this.name + ", summary=" + this.summary + ", tags=" + this.tags + ", type=" + this.type
 + ", storedBlobId=" + this.blobId + ", geoPointId=" + this.placeId
		        + ", identifier=" + this.identifier + ", created=" + this.created + "]";
    }

}
