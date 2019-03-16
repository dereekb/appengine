package com.dereekb.gae.model.extension.data.conversion.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.KeyedBidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;

/**
 * Default implementation of {@link KeyedBidirectionalConverter}.
 *
 * @author dereekb
 */
public final class KeyedBidirectionalConverterImpl<I, O>
        implements KeyedBidirectionalConverter<I, O> {

	private final String defaultKey;
	private final Map<String, DirectionalConverter<I, O>> outputConversions;
	private final DirectionalConverter<O, I> inputConversion;

	public KeyedBidirectionalConverterImpl(DirectionalConverter<I, O> outputConversion,
	        DirectionalConverter<O, I> inputConversion) {
		this.defaultKey = "default";
		this.outputConversions = new HashMap<String, DirectionalConverter<I, O>>();
		this.outputConversions.put(this.defaultKey, outputConversion);
		this.inputConversion = inputConversion;
	}

	public KeyedBidirectionalConverterImpl(String defaultKey,
	        Map<String, DirectionalConverter<I, O>> outputConversions,
	        DirectionalConverter<O, I> inputConversion) {
		this.defaultKey = defaultKey;
		this.outputConversions = outputConversions;
		this.inputConversion = inputConversion;
	}

	@Override
	public List<O> convertTo(Collection<? extends I> input) throws ConversionFailureException {
		return this.convertTo(this.defaultKey, input);
	}

	@Override
	public List<I> convertFrom(Collection<? extends O> output) throws ConversionFailureException {
		return this.inputConversion.convert(output);
	}

	@Override
	public List<O> convertTo(String key,
	                         Collection<? extends I> input)
	        throws ConversionFailureException {

		if (key == null) {
			key = this.defaultKey;
		}

		DirectionalConverter<I, O> converter = this.outputConversions.get(key);

		if (converter == null) {
			String reason = String.format("No converter available for type '%s'.", key);
			throw new ConversionFailureException(reason);
		}

		List<O> output = converter.convert(input);
		return output;
	}

}
