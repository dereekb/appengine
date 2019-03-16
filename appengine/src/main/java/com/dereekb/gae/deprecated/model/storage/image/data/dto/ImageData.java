package com.thevisitcompany.gae.deprecated.model.storage.image.data.dto;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thevisitcompany.gae.deprecated.model.storage.support.data.StorageModelData;
import com.thevisitcompany.gae.model.general.geo.Point;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageData extends StorageModelData {

	private static final long serialVersionUID = 1L;

	@Size(min = 0, max = 50)
	private String name;

	@Size(min = 0, max = 400)
	private String summary;

	@Valid
	private Point location;

	public ImageData() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

}
