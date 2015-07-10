package com.dereekb.gae.model.stored.blob.dto;

import java.util.Date;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringLongModelKeyConverter;

/**
 * {@link DirectionalConverter} for converting a {@link StoredBlobData} to
 * {@link StoredBlob}.
 *
 * @author dereekb
 */
public final class StoredBlobDataReader extends AbstractDirectionalConverter<StoredBlobData, StoredBlob> {

	private static final StringLongModelKeyConverter keyConverter = StringLongModelKeyConverter.CONVERTER;

	@Override
	public StoredBlob convertSingle(StoredBlobData input) throws ConversionFailureException {
		StoredBlob blob = new StoredBlob();

		// Identifier
		String stringIdentifier = input.getIdentifier();
		if (stringIdentifier != null) {
			ModelKey key = keyConverter.convertSingle(stringIdentifier);
			Long identifier = key.getId();
			blob.setIdentifier(identifier);
		}

		// Date
		Long created = input.getCreated();

		if (created != null) {
			Date date = new Date(created);
			blob.setDate(date);
		}

		// Links
		blob.setDescriptor(input.getDescriptor());

		return blob;
	}

}
