package com.dereekb.gae.model.stored.image.set.search.document.index;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.deprecated.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.derivative.IncludedDocumentBuilderStepDelegate;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;

/**
 * {@link IncludedDocumentBuilderStepDelegate} implementation for
 * {@link StoredImageSet} instances.
 *
 * @author dereekb
 *
 */
@Deprecated
public class StoredImageSetIncludedDocumentBuilderStepDelegate
        implements IncludedDocumentBuilderStepDelegate<StoredImageSet> {

	@Override
	public List<Descriptor> getIncludedModelDescriptors(StoredImageSet model) {
		List<Descriptor> descriptors = new ArrayList<Descriptor>();

	    return descriptors;
    }

}
