package com.thevisitcompany.gae.deprecated.model.storage.image.search.document.index;

import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.GeoPoint;
import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.deprecated.model.storage.image.ImageType;
import com.thevisitcompany.gae.deprecated.model.storage.support.search.document.StorageModelSearchDocument;
import com.thevisitcompany.gae.model.general.geo.Point;
import com.thevisitcompany.gae.model.general.geo.utility.PointConverter;

public final class ImageSearchDocument extends StorageModelSearchDocument<Image> {

	public ImageSearchDocument(Image model) {
		super(model);
	}

	@Override
	protected void addModelVariables(Builder document) {
		super.addModelVariables(document);

		String name = this.model.getName();
		Field.Builder nameField = Field.newBuilder().setName("name").setText(name);
		document.addField(nameField);

		String summary = this.model.getSummary();
		if (summary != null) {
			Field.Builder summaryField = Field.newBuilder().setName("summary").setText(summary);
			document.addField(summaryField);
		}

		Point location = this.model.getLocation();
		if (location != null) {
			GeoPoint point = PointConverter.convertToGeopoint(location);
			Field.Builder locationField = Field.newBuilder().setName("location").setGeoPoint(point);
			document.addField(locationField);
		}

		ImageType type = this.model.getImageType();
		Field.Builder typeField = Field.newBuilder().setName("type").setAtom(type.getAbbreviation());
		document.addField(typeField);
	}

	@Override
	public void savedDocumentWithId(String indexedDocumentId) {
		this.model.setSearchIdentifier(indexedDocumentId);
	}

}
