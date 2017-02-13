package com.dereekb.gae.web.api.util.attribute.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.util.attribute.AttributeUpdateFailure;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedInvalidAttributeData;

/**
 * Used for resolving {@link KeyedInvalidAttribute} types.
 * 
 * @author dereekb
 *
 */
public class KeyedInvalidAttributeApiResponseBuilder {

	public static final String ERROR_CODE = "INVALID_ATTRIBUTE";
	public static final String ERROR_TITLE = "Invalid Attribute";
	public static final String ERROR_DETAIL = "One or more input values failed due to invalid attributes.";

	// MARK: ApiResponseErrorConvertable
	public static ApiResponseError make(AttributeUpdateFailure failure) {
		List<KeyedInvalidAttributeData> data = makeDataForAttributes(new SingleItem<>(failure));
		return make(data);
	}

	public static ApiResponseErrorImpl make(KeyedInvalidAttribute failure) {
		return make(new SingleItem<KeyedInvalidAttribute>(failure));
	}

	public static ApiResponseErrorImpl make(Iterable<? extends KeyedInvalidAttribute> failures) {
		List<KeyedInvalidAttributeData> data = makeDataForKeyedAttributes(failures);
		ApiResponseErrorImpl error = null;

		if (data.isEmpty() == false) {
			error = make(data);
		}

		return error;
	}

	public static ApiResponseErrorImpl make(Collection<KeyedInvalidAttributeData> data) {
		ApiResponseErrorImpl impl = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE, ERROR_DETAIL);
		impl.setData(data);
		return impl;
	}

	public static List<KeyedInvalidAttributeData> makeDataForAttributes(Iterable<? extends AttributeUpdateFailure> failures) {
		List<KeyedInvalidAttributeData> dataList = new ArrayList<KeyedInvalidAttributeData>();

		for (AttributeUpdateFailure failure : failures) {
			dataList.add(new KeyedInvalidAttributeData(failure));
		}

		return dataList;
	}

	public static List<KeyedInvalidAttributeData> makeDataForKeyedAttributes(Iterable<? extends KeyedInvalidAttribute> failures) {
		List<KeyedInvalidAttributeData> dataList = new ArrayList<KeyedInvalidAttributeData>();

		for (KeyedInvalidAttribute failure : failures) {
			dataList.add(new KeyedInvalidAttributeData(failure));
		}

		return dataList;
	}

}
