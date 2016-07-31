package com.dereekb.gae.model.stored.image.dto;

import java.util.List;

import com.dereekb.gae.model.extension.search.document.search.dto.SearchableDatabaseModelData;
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
public final class StoredImageData extends SearchableDatabaseModelData {

	private static final long serialVersionUID = 1L;

	private String name;

	private String summary;

	private String tags;

	private Integer type;

	private Long blob;

	private Long geoPlace;

	private List<Long> imageSets;

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

	public Long getBlob() {
		return this.blob;
	}

	public void setBlob(Long blob) {
		this.blob = blob;
	}

	public Long getGeoPlace() {
		return this.geoPlace;
	}

	public void setGeoPlace(Long geoPlace) {
		this.geoPlace = geoPlace;
	}

	public List<Long> getImageSets() {
		return this.imageSets;
	}

	public void setImageSets(List<Long> imageSets) {
		this.imageSets = imageSets;
	}

	@Override
	public String toString() {
		return "StoredImageData [name=" + this.name + ", summary=" + this.summary + ", tags=" + this.tags + ", type="
		        + this.type + ", blob=" + this.blob + ", geoPlace=" + this.geoPlace + ", imageSets=" + this.imageSets
		        + ", searchIdentifier=" + this.searchIdentifier + ", identifier=" + this.key + ", created="
		        + this.created + "]";
	}

}
