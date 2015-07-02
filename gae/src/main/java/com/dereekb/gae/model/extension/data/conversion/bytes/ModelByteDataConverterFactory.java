package com.dereekb.gae.model.extension.data.conversion.bytes;

import com.dereekb.gae.model.extension.data.conversion.KeyedBidirectionalConverter;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

public class ModelByteDataConverterFactory<T, A, W>
        implements Factory<ModelByteDataConverter<T, A, W>> {

	private String conversionName;
	private Factory<KeyedBidirectionalConverter<T, A>> modelConverterFactory;
	private ModelPackager<A, W> sharedModelPackager;
	private ByteDataConverter<W> sharedByteConverter;

	@Override
	public ModelByteDataConverter<T, A, W> make() throws FactoryMakeFailureException {

		if (this.modelConverterFactory == null) {
			throw new FactoryMakeFailureException("Null Model Converter Factory.");
		}

		if (this.sharedModelPackager == null) {
			throw new FactoryMakeFailureException("Null Shared Model Packager.");
		}

		if (this.sharedByteConverter == null) {
			throw new FactoryMakeFailureException("Null Shared Byte Converter.");
		}

		KeyedBidirectionalConverter<T, A> modelConverter = this.modelConverterFactory.make();
		ModelByteDataConverter<T, A, W> byteConverter = new ModelByteDataConverter<T, A, W>(modelConverter,
		        this.sharedModelPackager,
 this.sharedByteConverter);
		byteConverter.setConversionName(this.conversionName);
		return byteConverter;
	}

	public String getConversionName() {
		return this.conversionName;
	}

	public void setConversionName(String conversionName) {
		this.conversionName = conversionName;
	}

	public Factory<KeyedBidirectionalConverter<T, A>> getModelConverterFactory() {
		return this.modelConverterFactory;
	}

	public void setModelConverterFactory(Factory<KeyedBidirectionalConverter<T, A>> modelConverterFactory) {
		this.modelConverterFactory = modelConverterFactory;
	}

	public ModelPackager<A, W> getSharedModelPackager() {
		return this.sharedModelPackager;
	}

	public void setSharedModelPackager(ModelPackager<A, W> sharedModelPackager) {
		this.sharedModelPackager = sharedModelPackager;
	}

	public ByteDataConverter<W> getSharedByteConverter() {
		return this.sharedByteConverter;
	}

	public void setSharedByteConverter(ByteDataConverter<W> sharedByteConverter) {
		this.sharedByteConverter = sharedByteConverter;
	}

}
