package com.dereekb.gae.model.stored.image.set.search.document.index;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescriptorUtility;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.derivative.DerivativeDocumentBuilderStepDelegate;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.googlecode.objectify.Key;

/**
 * {@link DerivativeDocumentBuilderStepDelegate} implementation for
 * {@link StoredImageSet} instances.
 *
 * @author dereekb
 *
 */
public class StoredImageSetDerivativeDocumentBuilderStepDelegate
        implements DerivativeDocumentBuilderStepDelegate<StoredImageSet> {

	@Override
	public List<Descriptor> getDerivativeDescriptors(StoredImageSet model) {
		List<Descriptor> descriptors = new ArrayList<Descriptor>();

		Key<StoredImage> icon = model.getIcon();

		if (icon != null) {
			descriptors.add(DescriptorUtility.withKeyId(icon));
		}

	    return descriptors;
    }

}
