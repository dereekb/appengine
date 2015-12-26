package com.dereekb.gae.model.geo.place.search.document.query;

import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.model.DescriptorSearch;
import com.dereekb.gae.model.extension.search.document.search.model.PointRadiusSearch;
import com.dereekb.gae.model.extension.search.document.search.service.model.impl.AbstractModelDocumentRequest;
import com.dereekb.gae.model.geo.place.GeoPlace;

/**
 * Search request for a {@link GeoPlace}.
 * 
 * @author dereekb
 *
 */
public class GeoPlaceSearchRequest extends AbstractModelDocumentRequest {

	private Boolean region;

	private DateSearch date;
	private PointRadiusSearch point;
	private DescriptorSearch descriptor;

	public GeoPlaceSearchRequest() {}

    public Boolean getRegion() {
    	return this.region;
    }

    public void setRegion(Boolean region) {
    	this.region = region;
    }

    public DateSearch getDate() {
    	return this.date;
    }

    public void setDate(DateSearch date) {
    	this.date = date;
    }

    public PointRadiusSearch getPoint() {
    	return this.point;
    }

    public void setPoint(PointRadiusSearch point) {
    	this.point = point;
    }

    public DescriptorSearch getDescriptor() {
    	return this.descriptor;
    }

    public void setDescriptor(DescriptorSearch descriptor) {
    	this.descriptor = descriptor;
    }

	@Override
	public String toString() {
		return "GeoPlaceSearchRequest [region=" + this.region + ", date=" + this.date + ", point=" + this.point
		        + ", descriptor=" + this.descriptor + "]";
	}

}
