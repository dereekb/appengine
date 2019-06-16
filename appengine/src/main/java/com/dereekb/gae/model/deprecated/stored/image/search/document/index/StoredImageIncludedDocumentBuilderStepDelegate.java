package com.dereekb.gae.model.stored.image.search.document.index;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.deprecated.stored.blob.StoredBlob;
import com.dereekb.gae.model.deprecated.stored.image.StoredImage;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.derivative.IncludedDocumentBuilderStepDelegate;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescriptorUtility;
import com.googlecode.objectify.Key;

/**
 * {@link IncludedDocumentBuilderStepDelegate} implementation for
 * {@link StoredImage} instances.
 * <p>
 * Used for retrieving all the derivative types from a {@link StoredImage}
 * instance.
 *
 * @author dereekb
 *
 */
public class StoredImageIncludedDocumentBuilderStepDelegate
        implements IncludedDocumentBuilderStepDelegate<StoredImage> {

	@Override
	public List<Descriptor> getIncludedModelDescriptors(StoredImage model) {
		List<Descriptor> descriptors = new ArrayList<Descriptor>();

		Key<StoredBlob> storedBlob = model.getStoredBlob();

		if (storedBlob != null) {
			descriptors.add(DescriptorUtility.withKeyId(storedBlob));
		}

		return descriptors;
	}

}
