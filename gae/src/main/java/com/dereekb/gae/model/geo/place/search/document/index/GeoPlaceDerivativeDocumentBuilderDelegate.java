package com.dereekb.gae.model.geo.place.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.derivative.DerivativeDocumentBuilderDelegate;
import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.derivative.NoDerivativeTypeException;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.GeoPlaceInfoType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link DerivativeDocumentBuilderDelegate} for reading the
 * {@link GeoPlace} identifier from {@link GeoPlaceInfoType} instances.
 *
 * @author dereekb
 */
public final class GeoPlaceDerivativeDocumentBuilderDelegate<T extends GeoPlaceInfoType>
        implements DerivativeDocumentBuilderDelegate<T> {

	private final String type;

	public GeoPlaceDerivativeDocumentBuilderDelegate(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public String readDerivativeType(T model) throws NoDerivativeTypeException {
		return this.type;
	}

	@Override
	public String readDerivativeIdentifier(T model) {
		ModelKey key = model.getGeoPlaceKey();
		return key.keyAsString();
	}

}