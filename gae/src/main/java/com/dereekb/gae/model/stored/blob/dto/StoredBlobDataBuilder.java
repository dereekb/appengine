package com.dereekb.gae.model.stored.blob.dto;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.search.document.dto.DescribedModelDataBuilder;
import com.dereekb.gae.model.stored.blob.StoredBlob;

/**
 * {@link DirectionalConverter} for converting a {@link StoredBlob} to
 * {@link StoredBlobData}.
 *
 * @author dereekb
 */
public final class StoredBlobDataBuilder extends DescribedModelDataBuilder<StoredBlob, StoredBlobData> {

	private StoredBlobDataDownloadBuilder downloadBuilder;

	public StoredBlobDataBuilder(StoredBlobDataDownloadBuilder downloadBuilder) throws IllegalArgumentException {
		super(StoredBlobData.class);
		this.setDownloadBuilder(downloadBuilder);
	}

	public StoredBlobDataDownloadBuilder getDownloadBuilder() {
		return this.downloadBuilder;
	}

	public void setDownloadBuilder(StoredBlobDataDownloadBuilder downloadBuilder) throws IllegalArgumentException {
		if (downloadBuilder == null) {
			throw new IllegalArgumentException("downloadBuilder cannot be null.");
		}

		this.downloadBuilder = downloadBuilder;
	}

	@Override
	public StoredBlobData convertSingle(StoredBlob input) throws ConversionFailureException {
		StoredBlobData data = super.convertSingle(input);

		// Data
		data.setDate(input.getDate());
		data.setType(input.getTypeId());

		String download = this.downloadBuilder.downloadLinkForStoredBlob(input);
		data.setDownload(download);

		// Links
		data.setDescriptor(input.getDescriptor());

		return data;
	}

}
