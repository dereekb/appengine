package com.thevisitcompany.gae.deprecated.model.storage.image.search.query;

import com.thevisitcompany.gae.deprecated.model.mod.search.query.ObjectifyModelQueryRequest;
import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.deprecated.model.storage.image.ImageType;
import com.thevisitcompany.gae.deprecated.model.storage.support.search.query.StorageModelDefaultStringToQueryConverter;

public class ImageDefaultStringToQueryConverter extends StorageModelDefaultStringToQueryConverter<Image> {

	private static final String IS_IMAGE_QUERY = "image";
	private static final String IS_ICON_QUERY = "icon";
	private static final String IS_LOCATION_IMAGE_QUERY = "location";

	private ImageModelQueryRequestBuilder builder;

	public ImageDefaultStringToQueryConverter() {
		this.setBuilder(new ImageModelQueryRequestBuilder());
	}

	@Override
	public ObjectifyModelQueryRequest<Image> makeQuery(String string,
	                                                   Integer limit) {
		ObjectifyModelQueryRequest<Image> request = null;

		switch (string) {
			case IS_IMAGE_QUERY: {
				request = builder.typeSearch(null);
			}
				break;
			case IS_ICON_QUERY: {
				request = builder.typeSearch(ImageType.ICON.getBit());
			}
				break;
			case IS_LOCATION_IMAGE_QUERY: {
				request = builder.typeSearch(ImageType.LOCATION.getBit());
			}
				break;
			default: {
				request = super.makeQuery(string, limit);
			}
				break;
		}

		this.appendLimitToRequest(request, limit);
		return request;
	}

	public ImageModelQueryRequestBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(ImageModelQueryRequestBuilder builder) {
		super.setBuilder(builder);
		this.builder = builder;
	}

}
