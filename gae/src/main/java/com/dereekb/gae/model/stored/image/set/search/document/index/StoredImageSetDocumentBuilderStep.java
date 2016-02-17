package com.dereekb.gae.model.stored.image.set.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.search.document.index.GeoPlaceDerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.search.document.index.StoredBlobDerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.google.appengine.api.search.Document.Builder;

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

	public static final String LABEL_FIELD = "label";
	public static final String DETAIL_FIELD = "detail";
	public static final String TAGS_FIELD = "tags";

	@Override
	public void performStep(StoredImageSet model,
	                        Builder builder) {

		SearchDocumentBuilderUtility.addAtom(LABEL_FIELD, model.getLabel(), builder);
		SearchDocumentBuilderUtility.addText(DETAIL_FIELD, model.getDetail(), builder);
		SearchDocumentBuilderUtility.addText(TAGS_FIELD, model.getTags(), builder);

	}

}
