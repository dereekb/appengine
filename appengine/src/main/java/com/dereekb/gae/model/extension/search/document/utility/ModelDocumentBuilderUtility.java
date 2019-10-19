package com.dereekb.gae.model.extension.search.document.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedModel;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.search.document.utility.SearchDocumentBuilderUtility;
import com.dereekb.gae.utilities.misc.keyed.Keyed;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.ScoredDocument;

public class ModelDocumentBuilderUtility {

	public static final String ID_FIELD = "id";
	public static final String DATE_FIELD = "date";
	public static final String POINT_FIELD = "point";
	public static final String DESCRIPTOR_TYPE_FIELD = "descriptorType";
	public static final String DESCRIPTOR_ID_FIELD = "descriptorId";

	// MARK: Document

	public static void setDocumentId(Keyed<ModelKey> model,
	                                 Builder builder) {
		setDocumentId(ModelKey.readStringKey(model), builder);
	}

	public static void setDocumentId(String documentId,
	                                 Document.Builder builder) {
		setDocumentId("%s", documentId, builder);
	}

	public static void setDocumentId(String format,
	                                 String documentId,
	                                 Document.Builder builder) {
		builder.setId(String.format(format, documentId));
	}

	// MARK: Id
	public static void addKey(String id,
	                          Document.Builder builder) {
		addKey("%s", id, builder);
	}

	public static void addKey(Keyed<ModelKey> model,
	                          Document.Builder builder) {
		addKey(model.keyValue(), builder);
	}

	public static void addKey(ModelKey modelKey,
	                          Document.Builder builder) {
		addKey("%s", modelKey, builder);
	}

	public static void addKey(String format,
	                          ModelKey key,
	                          Document.Builder builder) {
		String value = null;

		if (key != null) {
			value = key.toString();
		}

		SearchDocumentBuilderUtility.addAtom(format, ID_FIELD, value, builder);
	}

	public static void addKey(String format,
	                          String id,
	                          Document.Builder builder) {
		SearchDocumentBuilderUtility.addAtom(format, ID_FIELD, id, builder);
	}

	public static List<String> readStringKeysFromDocuments(Iterable<ScoredDocument> documents) {
		return readStringKeysFromDocuments(documents, ID_FIELD);
	}

	public static List<String> readStringKeysFromDocuments(Iterable<ScoredDocument> documents,
	                                                       String idFieldName) {
		List<String> keys = new ArrayList<String>();

		for (ScoredDocument document : documents) {
			Field idField = document.getOnlyField(idFieldName);
			String stringKey = idField.getAtom();
			keys.add(stringKey);
		}

		return keys;
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
	                            Document.Builder builder) {
		addPoint("%s", point, builder);
	}

	public static void addPoint(String format,
	                            Point point,
	                            Document.Builder builder) {
		SearchDocumentBuilderUtility.addGeoPoint(format, POINT_FIELD, point, builder);
	}

}
