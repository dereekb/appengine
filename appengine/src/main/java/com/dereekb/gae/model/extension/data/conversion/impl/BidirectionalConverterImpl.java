package com.dereekb.gae.model.extension.data.conversion.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;

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
public class BidirectionalConverterImpl<I, O>
        implements BidirectionalConverter<I, O>, DirectionalConverter<I, O> {

	private DirectionalConverter<I, O> inputConversion;
	private DirectionalConverter<O, I> outputConversion;

	public BidirectionalConverterImpl() {}

	public BidirectionalConverterImpl(DirectionalConverter<I, O> inputConversion,
	        DirectionalConverter<O, I> outputConversion) {
		this.setInputConversion(inputConversion);
		this.setOutputConversion(outputConversion);
	}

	public DirectionalConverter<I, O> getInputConversion() {
		return this.inputConversion;
	}

	public void setInputConversion(DirectionalConverter<I, O> inputConversion) {
		if (inputConversion == null) {
			throw new IllegalArgumentException("inputConversion cannot be null.");
		}

		this.inputConversion = inputConversion;
	}

	public DirectionalConverter<O, I> getOutputConversion() {
		return this.outputConversion;
	}

	public void setOutputConversion(DirectionalConverter<O, I> outputConversion) {
		if (outputConversion == null) {
			throw new IllegalArgumentException("outputConversion cannot be null.");
		}

		this.outputConversion = outputConversion;
	}

	// BidirectionalConverter
	@Override
	public List<O> convertTo(Collection<? extends I> input) throws ConversionFailureException {
		return this.inputConversion.convert(input);
	}

	@Override
	public List<I> convertFrom(Collection<? extends O> output) throws ConversionFailureException {
		return this.outputConversion.convert(output);
	}

	// DirectionalConverter
	@Override
	public List<O> convert(Collection<? extends I> input) throws ConversionFailureException {
		return this.convertTo(input);
	}

	@Override
	public String toString() {
		return "BidirectionalConverterImpl [inputConversion=" + this.inputConversion + ", outputConversion="
		        + this.outputConversion + "]";
	}

}
