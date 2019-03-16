package com.dereekb.gae.model.stored.image.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.search.document.index.StoredBlobIncludedDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.StoredImageType;
import com.google.appengine.api.search.Document.Builder;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link StoredImageDocumentBuilderStep}.
 * <p>
 * Additional information about the {@link StoredBlob}
 * should be added using a {@link StoredBlobIncludedDocumentBuilderStep} and
 * {@link GeoPlaceIncludedDocumentBuilderStep}, respectively.
 *
 * @author dereekb
 */
public class StoredImageDocumentBuilderStep
        implements StagedDocumentBuilderStep<StoredImage> {

	public final static String DERIVATIVE_PREFIX = "SI_";
	public final static String DERIVATIVE_FIELD_FORMAT = DERIVATIVE_PREFIX + "%s";

	public static final String NAME_FIELD = "name";
	public static final String SUMMARY_FIELD = "summary";
	public static final String TAGS_FIELD = "tags";
	public static final String TYPE_FIELD = "type";

	@Override
	public void performStep(StoredImage model,
	                        Builder builder) {

		String name = model.getName();
		SearchDocumentBuilderUtility.addText(NAME_FIELD, name, builder);

		String summary = model.getSummary();
		SearchDocumentBuilderUtility.addText(SUMMARY_FIELD, summary, builder);

		String tags = model.getTags();
		SearchDocumentBuilderUtility.addText(TAGS_FIELD, tags, builder);

		StoredImageType type = model.getType();
		SearchDocumentBuilderUtility.addAtom(TYPE_FIELD, type.getTypeName(), builder);

	}

}
