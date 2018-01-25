package com.dereekb.gae.model.extension.data.conversion.impl;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.TypedBidirectionalConverter;

/**
 * {@link BidirectionalConverter} implementation.
 *
 * @author dereekb
 *
 * @param <I>
 *            input type
 * @param <O>
 *            output type
 */
public class TypedBidirectionalConverterImpl<I, O> extends BidirectionalConverterImpl<I, O>
        implements TypedBidirectionalConverter<I, O> {

	private String modelType;

	public TypedBidirectionalConverterImpl(DirectionalConverter<I, O> inputConversion,
	        DirectionalConverter<O, I> outputConversion,
	        String modelType) {
		super(inputConversion, outputConversion);
		this.setModelType(modelType);
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	@Override
	public String toString() {
		return "TypedBidirectionalConverterImpl [modelType=" + this.modelType + ", getOutputConversion()="
		        + this.getOutputConversion() + ", getInputConversion()=" + this.getInputConversion() + "]";
	}

}
