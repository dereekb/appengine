package com.dereekb.gae.model.geo.place.dto;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.DescriptorImpl;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.Region;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * DTO of the {@link GeoPlace} class.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoPlaceData extends DatabaseModelData {

	private static final long serialVersionUID = 1L;

	private Point point;

	private Region region;

	private Long parent;

	private DescriptorImpl descriptor;

	public GeoPlaceData() {}

	public Point getPoint() {
		return this.point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Long getParent() {
		return this.parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public DescriptorImpl getDescriptor() {
		return this.descriptor;
	}

	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = DescriptorImpl.withValue(descriptor);
	}

	@Override
	public String toString() {
		return "GeoPlaceData [point=" + this.point + ", region=" + this.region + ", parent=" + this.parent
		        + ", descriptor=" + this.descriptor + "]";
	}

}
