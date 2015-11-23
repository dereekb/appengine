package com.dereekb.gae.model.stored.image.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.builder.impl.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.search.document.index.GeoPlaceDerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.search.document.index.StoredBlobDerivativeDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.StoredImageType;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link StoredImageDocumentBuilderStep}.
 *
 * Additional information about the {@link StoredBlob} and {@link GeoPlace}
 * should be added using a {@link StoredBlobDerivativeDocumentBuilderStep} and
 * {@link GeoPlaceDerivativeDocumentBuilderStep}, respectively.
 *
 * @author dereekb
 */
public final class StoredImageDocumentBuilderStep
        implements StagedDocumentBuilderStep<StoredImage> {

	@Override
	public void updateBuilder(StoredImage model,
	                          Builder builder) {

		String name = model.getName();
		Field.Builder nameField = SearchDocumentBuilderUtility.textField("name", name);
		builder.addField(nameField);

		String summary = model.getSummary();
		Field.Builder summaryField = SearchDocumentBuilderUtility.textField("summary", summary);
		builder.addField(summaryField);

		String tags = model.getTags();
		Field.Builder tagsField = SearchDocumentBuilderUtility.textField("tags", tags);
		builder.addField(tagsField);

		StoredImageType type = model.getType();
		Field.Builder typeField = SearchDocumentBuilderUtility.atomField("type", type.getTypeName());
		builder.addField(typeField);

	}

}
