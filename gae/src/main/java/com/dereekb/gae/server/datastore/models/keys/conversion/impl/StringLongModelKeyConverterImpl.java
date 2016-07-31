package com.dereekb.gae.server.datastore.models.keys.conversion.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.utilities.data.NumberRepresentation;

/**
 * {@link StringModelKeyConverter} implementation for converting a number value
 * within a {@link String} to a {@link ModelKey}.
 *
 * @author dereekb
 */
public class StringLongModelKeyConverterImpl
        implements StringModelKeyConverter {

	/**
	 * Statically available {@link StringLongModelKeyConverter} instance.
	 */
	public static final StringLongModelKeyConverterImpl CONVERTER = new StringLongModelKeyConverterImpl();

	private ConversionDelegate delegate = new DecimalConversion();

	public StringLongModelKeyConverterImpl() {}

	public StringLongModelKeyConverterImpl(ConversionDelegate delegate) {
		this.setDelegate(delegate);
	}

	public StringLongModelKeyConverterImpl(NumberRepresentation representation) {
		this.setDefaultDelegate(representation);
	}

	public ConversionDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ConversionDelegate delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException();
		}

		this.delegate = delegate;
	}

	public void setDefaultDelegate(NumberRepresentation representation) throws IllegalArgumentException {
		ConversionDelegate delegate;

		switch (representation) {
			case HEXADECIMAL:
				delegate = new HexConversion();
				break;
			case DECIMAL:
				delegate = new DecimalConversion();
				break;
			default:
				throw new IllegalArgumentException();
		}

		this.delegate = delegate;
	}

	// Bidirectional Converter
	@Override
	public List<ModelKey> convertTo(Collection<String> input) throws ConversionFailureException {
		return this.convert(input);
	}

	@Override
	public List<String> convertFrom(Collection<ModelKey> input) throws ConversionFailureException {
		List<String> keys = new ArrayList<>();

		for (ModelKey modelKey : input) {
			Long id = modelKey.getId();

			if (id == null) {
				throw new ConversionFailureException("The ModelKey did not have an identifier set.");
			}

			keys.add(this.delegate.convert(id));
		}

		return keys;
	}

	// Directional Converter
	@Override
	public List<ModelKey> convert(Collection<String> input) throws ConversionFailureException {
		List<ModelKey> keys = new ArrayList<>();

		try {
			for (String string : input) {
				Long number = this.delegate.convert(string);
				ModelKey key = new ModelKey(number);
				keys.add(key);
			}
		} catch (NumberFormatException e) {
			throw new ConversionFailureException(e);
		}

		return keys;
	}

	// SingleDirectionalConverter
	@Override
	public ModelKey convertSingle(String input) throws ConversionFailureException {
		ModelKey key;

		try {
			Long number = this.delegate.convert(input);
			key = new ModelKey(number);
		} catch (NumberFormatException e) {
			throw new ConversionFailureException(e);
		}

		return key;
	}

	@Override
    public ModelKey safeConvert(String input) {
		ModelKey key = null;

		if (input != null) {
			try {
				key = this.convertSingle(input);
			} catch (ConversionFailureException e) {

			}
		}

		return key;
	}

	public interface ConversionDelegate {

		public String convert(long number);

		public Long convert(String string) throws NumberFormatException;

	}

	public class DecimalConversion
	        implements ConversionDelegate {

		@Override
		public String convert(long number) {
			return Long.toString(number);
		}

		@Override
		public Long convert(String string) throws NumberFormatException {
			return Long.parseLong(string, 10);
		}

	}

	public class HexConversion
	        implements ConversionDelegate {

		@Override
		public String convert(long number) {
			return Long.toHexString(number);
		}

		@Override
		public Long convert(String string) throws NumberFormatException {
			return Long.parseLong(string, 16);
		}

	}

}
