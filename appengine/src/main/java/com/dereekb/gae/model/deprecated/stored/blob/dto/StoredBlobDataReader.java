package com.dereekb.gae.model.stored.blob.dto;

import com.dereekb.gae.model.deprecated.stored.blob.StoredBlob;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.deprecated.search.document.dto.DescribedModelDataReader;

/**
 * {@link DirectionalConverter} for converting a {@link StoredBlobData} to
 * {@link StoredBlob}.
 *
 * @author dereekb
 */
public final class StoredBlobDataReader extends DescribedModelDataReader<StoredBlob, StoredBlobData> {

	public StoredBlobDataReader() {
		super(StoredBlob.class);
	}

	@Override
	public StoredBlob convertSingle(StoredBlobData input) throws ConversionFailureException {
		StoredBlob blob = super.convertSingle(input);

		// Date
		blob.setDate(input.getDateValue());
		blob.setTypeId(input.getType());

		// Links
		blob.setDescriptor(input.getDescriptor());

		return blob;
	}

}
