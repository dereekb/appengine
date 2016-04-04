package com.dereekb.gae.model.geo.place.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.DerivativeDocumentBuilderStep;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.GeoPlaceInfoType;

/**
 * {@link DerivativeDocumentBuilderStep} implementation for
 * {@link GeoPlaceInfoType} instances for adding {@link GeoPlace} types.
 *
 * @author dereekb
 *
 */
public class GeoPlaceDerivativeDocumentBuilderStep extends GeoPlaceDocumentBuilderStep {

	public GeoPlaceDerivativeDocumentBuilderStep() {
		super(DERIVATIVE_FIELD_FORMAT, true);
	}

}
