package com.dereekb.gae.model.stored.blob.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.stored.blob.StoredBlob;

/**
 * {@link DirectionalConverter} for converting a {@link StoredBlob} to
 * {@link StoredBlobData}.
 *
 * @author dereekb
 */
public final class StoredBlobDataBuilder extends AbstractDirectionalConverter<StoredBlob, StoredBlobData> {

	private final StoredBlobDataDownloadBuilder downloadBuilder;

	public StoredBlobDataBuilder(StoredBlobDataDownloadBuilder downloadBuilder) {
		this.downloadBuilder = downloadBuilder;
	}

	@Override
	public StoredBlobData convertSingle(StoredBlob input) throws ConversionFailureException {
		StoredBlobData data = new StoredBlobData();

		data.setIdentifier(input.getModelKey());
		data.setCreated(input.getDate());

		String download = this.downloadBuilder.downloadLinkForStoredBlob(input);
		data.setDownload(download);

		data.setDescriptor(input.getDescriptor());

		return data;
	}

}
