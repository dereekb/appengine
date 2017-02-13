package com.dereekb.gae.web.api.util.attribute.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.util.attribute.AttributeUpdateFailure;
import com.dereekb.gae.web.api.util.attribute.KeyedAttributeUpdateFailure;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedAttributeUpdateFailureData;

/**
 * Used for resolving {@link KeyedAttributeUpdateFailure} types.
 * 
 * @author dereekb
 *
 */
public class KeyedAttributeUpdateFailureApiResponseBuilder {

	public static final String ERROR_CODE = "ATTRIBUTE_UPDATE_FAILURE";
	public static final String ERROR_TITLE = "Update Failure";
	public static final String ERROR_DETAIL = "One or more modules failed to update properly.";

	// MARK: ApiResponseErrorConvertable
	public static ApiResponseError make(AttributeUpdateFailure failure) {
		List<KeyedAttributeUpdateFailureData> data = makeDataForAttributes(new SingleItem<>(failure));
		return make(data);
	}

	public static ApiResponseErrorImpl make(KeyedAttributeUpdateFailure failure) {
		return make(new SingleItem<KeyedAttributeUpdateFailure>(failure));
	}

	public static ApiResponseErrorImpl make(Iterable<? extends KeyedAttributeUpdateFailure> failures) {
		List<KeyedAttributeUpdateFailureData> data = makeDataForKeyedAttributes(failures);
		ApiResponseErrorImpl error = null;

		if (data.isEmpty() == false) {
			error = make(data);
		}

		return error;
	}

	public static ApiResponseErrorImpl make(Collection<KeyedAttributeUpdateFailureData> data) {
		ApiResponseErrorImpl impl = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, ERROR_DETAIL);
		impl.setData(data);
		return impl;
	}

	public static List<KeyedAttributeUpdateFailureData> makeDataForAttributes(Iterable<? extends AttributeUpdateFailure> failures) {
		List<KeyedAttributeUpdateFailureData> dataList = new ArrayList<KeyedAttributeUpdateFailureData>();

		for (AttributeUpdateFailure failure : failures) {
			dataList.add(new KeyedAttributeUpdateFailureData(failure));
		}

		return dataList;
	}

	public static List<KeyedAttributeUpdateFailureData> makeDataForKeyedAttributes(Iterable<? extends KeyedAttributeUpdateFailure> failures) {
		List<KeyedAttributeUpdateFailureData> dataList = new ArrayList<KeyedAttributeUpdateFailureData>();

		for (KeyedAttributeUpdateFailure failure : failures) {
			dataList.add(new KeyedAttributeUpdateFailureData(failure));
		}

		return dataList;
	}

}
