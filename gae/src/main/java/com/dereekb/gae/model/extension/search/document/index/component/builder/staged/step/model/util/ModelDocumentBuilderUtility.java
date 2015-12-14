package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util;

import java.util.Date;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedModel;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.general.geo.Point;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

public class ModelDocumentBuilderUtility {

	// MARK: Id
	public static void addId(String id,
	                         Document.Builder builder) {
		addId("%s", id, builder);
	}

	public static void addId(String format,
	                         String id,
	                         Document.Builder builder) {
		String idName = String.format(format, "id");
		Field.Builder idField = SearchDocumentBuilderUtility.atomField(idName, id);
		builder.addField(idField);
	}

	// MARK: Date
	public static void addDate(Date date,
	                           Document.Builder builder) {
		addDate("%s", date, builder);
	}

	public static void addDate(String format,
	                           Date date,
	                           Document.Builder builder) {
		String dateName = String.format(format, "date");
		Field.Builder dateField = SearchDocumentBuilderUtility.dateField(dateName, date);
		builder.addField(dateField);
	}

	// MARK: Descriptor
	public static void addDescriptorInfo(DescribedModel model,
	                                     Document.Builder builder) {
		addDescriptorInfo("%s", model, builder);
	}

	public static void addDescriptorInfo(String format,
	                                     DescribedModel model,
	                                     Document.Builder builder) {

		Descriptor descriptor = model.getDescriptor();

		String descriptorId = null;
		String descriptorType = null;

		if (descriptor != null) {
			descriptorId = descriptor.getDescriptorId();
			descriptorType = descriptor.getDescriptorType();
		}

		// Descriptor Info
		String typeName = String.format(format, "descriptorType");
		Field.Builder descriptorField = SearchDocumentBuilderUtility.atomField(typeName, descriptorType);
		builder.addField(descriptorField);

		// Info Type Id
		String idName = String.format(format, "descriptorId");
		Field.Builder descriptorIdField = SearchDocumentBuilderUtility.atomField(idName, descriptorId);
		builder.addField(descriptorIdField);

	}

	// MARK: Point
	public static void addPoint(Point point,
	                            Builder builder) {
		addPoint("%s", point, builder);
	}

	public static void addPoint(String format,
	                            Point point,
	                            Builder builder) {
		String pointName = String.format(format, "point");
		Field.Builder pointField = SearchDocumentBuilderUtility.geoPointField(pointName, point);
		builder.addField(pointField);
	}

}
