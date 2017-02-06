package com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util;

import java.util.Date;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedModel;
import com.dereekb.gae.model.extension.search.document.index.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;

public class ModelDocumentBuilderUtility {

	public static final String ID_FIELD = "id";
	public static final String DATE_FIELD = "date";
	public static final String POINT_FIELD = "point";
	public static final String DESCRIPTOR_TYPE_FIELD = "descriptorType";
	public static final String DESCRIPTOR_ID_FIELD = "descriptorId";

	// MARK: Id
	public static void addId(String id,
	                         Document.Builder builder) {
		addId("%s", id, builder);
	}

	public static void addId(String format,
	                         ModelKey key,
	                         Document.Builder builder) {
		String value = null;

		if (key != null) {
			value = key.toString();
		}

		SearchDocumentBuilderUtility.addAtom(format, ID_FIELD, value, builder);
	}

	public static void addId(String format,
	                         String id,
	                         Document.Builder builder) {
		SearchDocumentBuilderUtility.addAtom(format, ID_FIELD, id, builder);
	}

	// MARK: Date
	public static void addDate(Date date,
	                           Document.Builder builder) {
		addDate("%s", date, builder);
	}

	public static void addDate(String format,
	                           Date date,
	                           Document.Builder builder) {
		addDate(format, DATE_FIELD, date, builder);
	}

	public static void addDate(String format,
	                           String fieldName,
	                           Date date,
	                           Document.Builder builder) {
		SearchDocumentBuilderUtility.addDate(format, fieldName, date, builder);
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
		SearchDocumentBuilderUtility.addAtom(format, DESCRIPTOR_TYPE_FIELD, descriptorType, builder);

		// Info Type Id
		SearchDocumentBuilderUtility.addAtom(format, DESCRIPTOR_ID_FIELD, descriptorId, builder);
	}

	// MARK: Point
	public static void addPoint(Point point,
	                            Builder builder) {
		addPoint("%s", point, builder);
	}

	public static void addPoint(String format,
	                            Point point,
	                            Builder builder) {
		SearchDocumentBuilderUtility.addGeoPoint(format, POINT_FIELD, point, builder);
	}

}
