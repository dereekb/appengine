package com.dereekb.gae.model.extension.data.conversion.json;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.lang.reflect.Type;
import java.nio.charset.Charset;

import com.dereekb.gae.model.extension.data.conversion.bytes.ByteDataConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.utilities.json.JsonConverter;
import com.google.gson.reflect.TypeToken;

/**
 * GSON implementation of {@link ByteDataConverter} that converts to UTF8
 * encoded JSON files.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class JsonByteDataConverter<T>
        implements ByteDataConverter<T> {

	public static final String JSON_DATA_TYPE = "application/json";

	private final Charset charset = UTF_8;
	private final JsonConverter converter;
	private Type conversionType;

	public JsonByteDataConverter(JsonConverter converter) throws NullPointerException {
		if (converter == null) {
			throw new NullPointerException("JsonConverter cannot be null.");
		}

		this.converter = converter;
	}

	public JsonByteDataConverter(JsonConverter converter, Type conversionType) throws NullPointerException {
		this(converter);
		this.setConversionType(conversionType);
	}

	public JsonByteDataConverter(JsonConverter converter, JsonByteDataConverterDelegate<T> delegate)
	        throws NullPointerException {
		this(converter);
		this.setDelegate(delegate);
	}

	@Override
	public String getByteContentType() {
		return JSON_DATA_TYPE;
	}

	@Override
	public byte[] convertToBytes(T object) throws ConversionFailureException {
		String json = this.converter.convertToJson(object);
		byte[] bytes = json.getBytes(this.charset);
		return bytes;
	}

	@Override
	public T convertToObject(byte[] bytes) throws ConversionFailureException {
		String json = new String(bytes, this.charset);
		T object = this.converter.convertToObject(json, this.conversionType);
		return object;
	}

	public boolean canConvertToObject() {
		return this.conversionType != null;
	}

	public Type getConversionType() {
		return this.conversionType;
	}

	public void setConversionType(Type conversionType) throws IllegalArgumentException {
		if (this.conversionType == null) {
			throw new IllegalArgumentException("Conversion type must not be null.");
		}

		this.conversionType = conversionType;
	}

	public void setDelegate(JsonByteDataConverterDelegate<T> delegate) {
		if (delegate != null) {
			TypeToken<T> tokenType = delegate.getJsonConversionType();
			this.conversionType = tokenType.getType();
		}
	}

}
