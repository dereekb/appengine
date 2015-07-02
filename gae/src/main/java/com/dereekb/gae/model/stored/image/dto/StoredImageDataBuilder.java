package com.dereekb.gae.model.stored.image.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.stored.image.StoredImage;

/**
 * {@link DirectionalConverter} for converting a {@link StoredImage} to
 * {@link StoredImageData}.
 *
 * @author dereekb
 */
public class StoredImageDataBuilder
        implements DirectionalConverter<StoredImage, StoredImageData> {

	public StoredImageDataBuilder() {}

	@Override
	public List<StoredImageData> convert(Collection<StoredImage> input) throws ConversionFailureException {
		List<StoredImageData> list = new ArrayList<StoredImageData>();

		for (StoredImage image : input) {
			StoredImageData data = this.convert(image);
			list.add(data);
		}

		return list;
	}

	public StoredImageData convert(StoredImage image) {
		StoredImageData data = new StoredImageData();

		data.setIdentifier(image.getModelKey());

		data.setName(image.getName());
		data.setSummary(image.getSummary());
		data.setTags(image.getTags());
		data.setType(image.getTypeId());

		data.setBlobId(image.getBlobId());
		data.setPlaceId(image.getPlaceId());

		return data;
	}

}
