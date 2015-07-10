package com.dereekb.gae.model.geo.place.search.document.index;

import java.util.Date;

import com.dereekb.gae.model.extension.search.document.index.component.builder.StagedDocumentBuilderStep;
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
	public void updateBuilder(GeoPlace model,
	                          Builder builder) {

		Long identifier = model.getIdentifier();
		Date date = model.getDate();

		Point point = model.getPoint();
		boolean isRegion = model.isRegion();

		String descriptorType = model.getDescriptorType();
		String descriptorId = model.getDescriptorId();

		// Place Identifier
		Field.Builder identifierField = SearchDocumentBuilderUtility.atomField("id", identifier.toString());
		builder.addField(identifierField);

		// Creation Date
		Field.Builder dateField = SearchDocumentBuilderUtility.dateField("date", date);
		builder.addField(dateField);

		// Point Field
		Field.Builder pointField = SearchDocumentBuilderUtility.geoPointField("point", point);
		builder.addField(pointField);

		// Is Region Field
		Field.Builder isRegionField = SearchDocumentBuilderUtility.booleanField("isRegion", isRegion);
		builder.addField(isRegionField);

		// Descriptor Info
		Field.Builder descriptorField = SearchDocumentBuilderUtility.atomField("descriptorType", descriptorType);
		builder.addField(descriptorField);

		// Info Type Id
		Field.Builder descriptorIdField = SearchDocumentBuilderUtility.atomField("descriptorId", descriptorId);
		builder.addField(descriptorIdField);

	}

}
