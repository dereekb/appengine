package com.dereekb.gae.model.stored.blob.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;

/**
 * {@link DirectionalConverter} for converting a {@link StoredBlobData} to
 * {@link StoredBlob}.
 *
 * @author dereekb
 */
public final class StoredBlobDataReader extends AbstractDirectionalConverter<StoredBlobData, StoredBlob> {

	private static final StringModelKeyConverter KEY_CONVERTER = StringLongModelKeyConverterImpl.CONVERTER;

	@Override
	public StoredBlob convertSingle(StoredBlobData input) throws ConversionFailureException {
		StoredBlob blob = new StoredBlob();

		// Identifier
		String stringIdentifier = input.getKey();
		blob.setModelKey(KEY_CONVERTER.safeConvert(stringIdentifier));
		blob.setSearchIdentifier(input.getSearchIdentifier());

		// Date
		blob.setDate(input.getDateValue());

		// Links
		blob.setDescriptor(input.getDescriptor());

		return blob;
	}

}
