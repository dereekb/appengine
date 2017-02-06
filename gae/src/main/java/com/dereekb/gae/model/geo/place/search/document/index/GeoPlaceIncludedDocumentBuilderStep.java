package com.dereekb.gae.model.geo.place.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.IncludedDocumentBuilderStep;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.GeoPlaceInfoType;

/**
 * {@link IncludedDocumentBuilderStep} implementation for
 * {@link GeoPlaceInfoType} instances for adding {@link GeoPlace} types.
 *
 * @author dereekb
 *
 */
public class GeoPlaceIncludedDocumentBuilderStep extends GeoPlaceDocumentBuilderStep {

	public GeoPlaceIncludedDocumentBuilderStep() {
		super(DERIVATIVE_FIELD_FORMAT, true);
	}

}
