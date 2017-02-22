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
 *            Input type.
 * @param <O>
 *            Output type.
 */
public class BidirectionalConverterImpl<I, O>
        implements BidirectionalConverter<I, O>, DirectionalConverter<I, O> {

	private DirectionalConverter<I, O> outputConversion;
	private DirectionalConverter<O, I> inputConversion;

	public BidirectionalConverterImpl() {}

	public BidirectionalConverterImpl(DirectionalConverter<I, O> outputConversion,
	        DirectionalConverter<O, I> inputConversion) {
		this.outputConversion = outputConversion;
		this.inputConversion = inputConversion;
	}

	public DirectionalConverter<I, O> getOutputConversion() {
		return this.outputConversion;
	}

	public void setOutputConversions(DirectionalConverter<I, O> outputConversion) {
		this.outputConversion = outputConversion;
	}

	public DirectionalConverter<O, I> getInputConversion() {
		return this.inputConversion;
	}

	public void setInputConversion(DirectionalConverter<O, I> inputConversion) {
		this.inputConversion = inputConversion;
	}

	// BidirectionalConverter
	@Override
	public List<O> convertTo(Collection<? extends I> input) throws ConversionFailureException {
		return this.outputConversion.convert(input);
	}

	@Override
	public List<I> convertFrom(Collection<? extends O> output) throws ConversionFailureException {
		return this.inputConversion.convert(output);
	}

	// DirectionalConverter
	@Override
	public List<O> convert(Collection<? extends I> input) throws ConversionFailureException {
		return this.convertTo(input);
	}

	@Override
	public String toString() {
		return "BidirectionalConverterImpl [outputConversion=" + this.outputConversion + ", inputConversion="
		        + this.inputConversion + "]";
	}

}
