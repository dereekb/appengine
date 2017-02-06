package com.dereekb.gae.model.stored.blob.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.dto.DescribedModelDataReader;
import com.dereekb.gae.model.stored.blob.StoredBlob;

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
