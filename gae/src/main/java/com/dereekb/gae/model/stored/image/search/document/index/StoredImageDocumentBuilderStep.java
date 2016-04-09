package com.dereekb.gae.model.stored.image.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.search.document.index.GeoPlaceDerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.search.document.index.StoredBlobDerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.StoredImageType;
import com.google.appengine.api.search.Document.Builder;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link StoredImageDocumentBuilderStep}.
 * <p>
 * Additional information about the {@link StoredBlob} and {@link GeoPlace}
 * should be added using a {@link StoredBlobDerivativeDocumentBuilderStep} and
 * {@link GeoPlaceDerivativeDocumentBuilderStep}, respectively.
 *
 * @author dereekb
 */
public class StoredImageDocumentBuilderStep
        implements StagedDocumentBuilderStep<StoredImage> {

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
