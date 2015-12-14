package com.dereekb.gae.model.stored.image.search.document.index;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.DerivativeDocumentBuilderStepDelegate;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.googlecode.objectify.Key;

/**
 * {@link DerivativeDocumentBuilderStepDelegate} implementation for
 * {@link StoredImage} instances.
 *
 * @author dereekb
 *
 */
public class StoredImageDerivativeDocumentBuilderStepDelegate
        implements DerivativeDocumentBuilderStepDelegate<StoredImage> {

	@Override
    public List<Descriptor> getDerivativeDescriptors(StoredImage model) {
		List<Descriptor> descriptors = new ArrayList<Descriptor>();

		Key<GeoPlace> geoPlace = model.getGeoPlace();
		Key<StoredBlob> storedBlob = model.getBlob();

		// TODO: Return descriptors.

	    return descriptors;
    }

}
