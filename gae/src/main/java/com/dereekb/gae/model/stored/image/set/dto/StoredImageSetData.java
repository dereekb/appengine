package com.dereekb.gae.model.stored.image.set.dto;

import java.util.List;

import javax.validation.constraints.Size;

import com.dereekb.gae.model.extension.search.document.search.dto.SearchableDatabaseModelData;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO for {@link StoredImageSet}.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class StoredImageSetData extends SearchableDatabaseModelData {

    private static final long serialVersionUID = 1L;

	public static final int MAX_LABEL_LENGTH = 50;
	public static final int MAX_DETAIL_LENGTH = 200;
	public static final int MAX_TAGS_LENGTH = 200;

	@Size(max = MAX_LABEL_LENGTH)
	private String label;

	@Size(max = MAX_DETAIL_LENGTH)
	private String detail;

	@Size(max = MAX_TAGS_LENGTH)
	private String tags;

	private Long icon;
	private List<Long> images;

	public StoredImageSetData() {}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Long getIcon() {
		return this.icon;
	}

	public void setIcon(Long icon) {
		this.icon = icon;
	}

	public List<Long> getImages() {
		return this.images;
	}

	public void setImages(List<Long> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "StoredImageSetData [label=" + this.label + ", detail=" + this.detail + ", tags=" + this.tags
		        + ", icon=" + this.icon + ", images=" + this.images + ", searchIdentifier=" + this.searchIdentifier
		        + ", identifier=" + this.key + ", created=" + this.created + "]";
	}

}
