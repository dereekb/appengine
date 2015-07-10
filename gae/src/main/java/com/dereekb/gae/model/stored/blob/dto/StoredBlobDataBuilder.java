package com.dereekb.gae.model.stored.blob.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.stored.blob.StoredBlob;

/**
 * {@link DirectionalConverter} for converting a {@link StoredBlob} to
 * {@link StoredBlobData}.
 *
 * @author dereekb
 */
public final class StoredBlobDataBuilder
        implements DirectionalConverter<StoredBlob, StoredBlobData> {

	private final StoredBlobDataDownloadBuilder downloadBuilder;

	public StoredBlobDataBuilder(StoredBlobDataDownloadBuilder downloadBuilder) {
		this.downloadBuilder = downloadBuilder;
	}

	@Override
	public List<StoredBlobData> convert(Collection<StoredBlob> input) throws ConversionFailureException {
		List<StoredBlobData> list = new ArrayList<StoredBlobData>();

		for (StoredBlob blob : input) {
			StoredBlobData data = this.convert(blob);
			list.add(data);
		}

		return list;
	}

	public StoredBlobData convert(StoredBlob blob) {
		StoredBlobData data = new StoredBlobData();

		data.setIdentifier(blob.getModelKey());
		data.setCreated(blob.getDate());

		String download = this.downloadBuilder.downloadLinkForStoredBlob(blob);
		data.setDownload(download);

		data.setDescriptor(blob.getDescriptor());

		return data;
	}

}
