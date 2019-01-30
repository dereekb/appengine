package com.dereekb.gae.model.extension.data.conversion.bytes;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.KeyedBidirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;

/**
 * Class for converting a model type to a DTO and then to byte data.
 *
 * @author dereekb
 * @param <T>
 */
public final class ModelByteDataConverter<T, A, W> {

	private String conversionName;
	private KeyedBidirectionalConverter<T, A> converter;
	private ModelPackager<A, W> modelPackager;
	private ByteDataConverter<W> byteConverter;

	public ModelByteDataConverter() {}

	public ModelByteDataConverter(KeyedBidirectionalConverter<T, A> converter,
	        ModelPackager<A, W> modelPackager,
	        ByteDataConverter<W> sharedByteConverter) {
		super();
		this.converter = converter;
		this.modelPackager = modelPackager;
		this.byteConverter = sharedByteConverter;
	}

	public String getConversionName() {
		return this.conversionName;
	}

	public void setConversionName(String conversionName) {
		this.conversionName = conversionName;
	}

	public KeyedBidirectionalConverter<T, A> getConverter() {
		return this.converter;
	}

	public void setConverter(KeyedBidirectionalConverter<T, A> converter) {
		this.converter = converter;
	}

	public ModelPackager<A, W> getModelPackager() {
		return this.modelPackager;
	}

	public void setModelPackager(ModelPackager<A, W> modelPackager) {
		this.modelPackager = modelPackager;
	}

	public ByteDataConverter<W> getByteConverter() {
		return this.byteConverter;
	}

	public void setByteConverter(ByteDataConverter<W> byteConverter) {
		this.byteConverter = byteConverter;
	}

	public String getByteContentType() {
		return this.byteConverter.getByteContentType();
	}

	public byte[] convertToBytes(Collection<T> objects) throws ConversionFailureException {
		List<A> conversion = this.converter.convertTo(this.conversionName, objects);
		W wrapper = this.modelPackager.packObjects(conversion);
		byte[] data = this.byteConverter.convertToBytes(wrapper);
		return data;
	}

	public List<T> convertToObjects(byte[] data) throws ConversionFailureException {
		W wrapper = this.byteConverter.convertToObject(data);
		Collection<A> unwrapped = this.modelPackager.unpackObjects(wrapper);
		List<T> models = this.converter.convertFrom(unwrapped);
		return models;
	}

}
