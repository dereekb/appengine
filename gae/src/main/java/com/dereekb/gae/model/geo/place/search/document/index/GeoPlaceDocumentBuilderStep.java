package com.dereekb.gae.model.geo.place.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.StagedDocumentBuilderStep;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * Implementation of {@link StagedDocumentBuilderStep} for adding
 * {@link GeoPlaceDocumentBuilderStep}.
 *
 * @author dereekb
 */
public final class GeoPlaceDocumentBuilderStep
        implements StagedDocumentBuilderStep<GeoPlace> {

	@Override
	public void performStep(GeoPlace model,
	                        Builder builder) {

		// Creation Date
		Date date = model.getDate();
		Field.Builder dateField = SearchDocumentBuilderUtility.dateField("date", date);
		builder.addField(dateField);

		// Point Field
		Point point = model.getPoint();
		Field.Builder pointField = SearchDocumentBuilderUtility.geoPointField("point", point);
		builder.addField(pointField);

		// Is Region Field
		boolean isRegion = model.isRegion();
		Field.Builder isRegionField = SearchDocumentBuilderUtility.booleanField("isRegion", isRegion);
		builder.addField(isRegionField);

		/*
		// Descriptor
		String descriptorType = model.getDescriptorType();
		String descriptorId = model.getDescriptorId();

		// Descriptor Info
		Field.Builder descriptorField = SearchDocumentBuilderUtility.atomField("descriptorType", descriptorType);
		builder.addField(descriptorField);

		// Info Type Id
		Field.Builder descriptorIdField = SearchDocumentBuilderUtility.atomField("descriptorId", descriptorId);
		builder.addField(descriptorIdField);
		 */
	}

}
