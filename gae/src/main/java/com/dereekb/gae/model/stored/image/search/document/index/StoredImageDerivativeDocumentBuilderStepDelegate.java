package com.dereekb.gae.model.stored.image.search.document.index;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescriptorUtility;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.DerivativeDocumentBuilderStepDelegate;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.googlecode.objectify.Key;

/**
 * {@link DerivativeDocumentBuilderStepDelegate} implementation for
 * {@link StoredImage} instances.
 * <p>
 * Used for retrieving all the derivative types from a {@link StoredImage}
 * instance.
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

		if (geoPlace != null) {
			descriptors.add(DescriptorUtility.withKeyId(geoPlace));
		}

		Key<StoredBlob> storedBlob = model.getStoredBlob();

		if (storedBlob != null) {
			descriptors.add(DescriptorUtility.withKeyId(storedBlob));
		}

	    return descriptors;
    }

}
