package com.dereekb.gae.model.stored.image.set.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.search.document.index.GeoPlaceDerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.search.document.index.StoredBlobDerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link StoredImageSetDocumentBuilderStep}.
 *
 * Additional information about the {@link StoredBlob} and {@link GeoPlace}
 * should be added using a {@link StoredBlobDerivativeDocumentBuilderStep} and
 * {@link GeoPlaceDerivativeDocumentBuilderStep}, respectively.
 *
 * @author dereekb
 */
public class StoredImageSetDocumentBuilderStep
        implements StagedDocumentBuilderStep<StoredImageSet> {

	@Override
	public void performStep(StoredImageSet model,
	                        Builder builder) {

		// TODO: Complete stored image searching.

		String tags = model.getTags();
		Field.Builder tagsField = SearchDocumentBuilderUtility.textField("tags", tags);
		builder.addField(tagsField);

	}

}
