package com.dereekb.gae.model.stored.image.set.search.document.index;

import com.dereekb.gae.model.deprecated.stored.blob.StoredBlob;
import com.dereekb.gae.model.deprecated.stored.blob.search.document.index.StoredBlobIncludedDocumentBuilderStep;
import com.dereekb.gae.model.deprecated.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.deprecated.search.document.index.utility.SearchDocumentBuilderUtility;
import com.google.appengine.api.search.Document.Builder;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link StoredImageSetDocumentBuilderStep}.
 *
 * Additional information about the {@link StoredBlob}
 * should be added using a {@link StoredBlobIncludedDocumentBuilderStep} and
 * {@link GeoPlaceIncludedDocumentBuilderStep}, respectively.
 *
 * @author dereekb
 */
public class StoredImageSetDocumentBuilderStep
        implements StagedDocumentBuilderStep<StoredImageSet> {

	public final static String DERIVATIVE_PREFIX = "IS_";
	public final static String DERIVATIVE_FIELD_FORMAT = DERIVATIVE_PREFIX + "%s";

	public static final String LABEL_FIELD = "label";
	public static final String DETAIL_FIELD = "detail";
	public static final String TAGS_FIELD = "tags";

	@Override
	public void performStep(StoredImageSet model,
	                        Builder builder) {

		SearchDocumentBuilderUtility.addText(LABEL_FIELD, model.getLabel(), builder);
		SearchDocumentBuilderUtility.addText(DETAIL_FIELD, model.getDetail(), builder);
		SearchDocumentBuilderUtility.addText(TAGS_FIELD, model.getTags(), builder);

	}

}
