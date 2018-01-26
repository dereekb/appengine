package com.dereekb.gae.model.extension.data.conversion.impl;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.ModelDataConversionInfo;
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

	private ModelDataConversionInfo<I, O> conversionInfo;

	public TypedBidirectionalConverterImpl(DirectionalConverter<I, O> inputConversion,
	        DirectionalConverter<O, I> outputConversion,
	        String modelType,
	        Class<I> inputClass,
	        Class<O> outputClass) {
		this(inputConversion, outputConversion,
		        new ModelDataConversionInfoImpl<I, O>(modelType, inputClass, outputClass));
	}

	public TypedBidirectionalConverterImpl(DirectionalConverter<I, O> inputConversion,
	        DirectionalConverter<O, I> outputConversion,
	        ModelDataConversionInfo<I, O> conversionInfo) {
		super(inputConversion, outputConversion);
		this.setConversionInfo(conversionInfo);
	}

	public ModelDataConversionInfo<I, O> getConversionInfo() {
		return this.conversionInfo;
	}

	public void setConversionInfo(ModelDataConversionInfo<I, O> conversionInfo) {
		if (conversionInfo == null) {
			throw new IllegalArgumentException("conversionInfo cannot be null.");
		}

		this.conversionInfo = conversionInfo;
	}

	// MARK: TypedBidirectionalConverter
	@Override
	public Class<I> getModelClass() {
		return this.conversionInfo.getModelClass();
	}

	@Override
	public Class<O> getModelDataClass() {
		return this.conversionInfo.getModelDataClass();
	}

	@Override
	public String getModelType() {
		return this.conversionInfo.getModelType();
	}

	@Override
	public String toString() {
		return "TypedBidirectionalConverterImpl [conversionInfo=" + this.conversionInfo + ", getInputConversion()="
		        + this.getInputConversion() + ", getOutputConversion()=" + this.getOutputConversion() + "]";
	}

}
