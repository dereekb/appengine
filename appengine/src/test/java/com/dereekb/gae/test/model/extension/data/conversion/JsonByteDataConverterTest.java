package com.dereekb.gae.test.model.extension.data.conversion;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.extension.data.conversion.json.JsonByteDataConverter;
import com.dereekb.gae.model.extension.data.conversion.json.JsonByteDataConverterDelegate;
import com.dereekb.gae.utilities.json.JsonConverter;
import com.google.gson.reflect.TypeToken;

public class JsonByteDataConverterTest {

	@Test
	public void testModelConversionWithDelegate() {

		Integer value = 1;
		TestConversionTarget<Integer> target = new TestConversionTarget<Integer>(value);

		JsonConverter jsonConverter = new JsonConverter();
		TestConversionIntegerTargetDelegate delegate = new TestConversionIntegerTargetDelegate();

		JsonByteDataConverter<TestConversionTarget<Integer>> byteConverter = new JsonByteDataConverter<TestConversionTarget<Integer>>(
		        jsonConverter, delegate);

		// Convert to Bytes
		byte[] bytes = byteConverter.convertToBytes(target);
		assertNotNull(bytes);

		// Convert Back
		TestConversionTarget<Integer> readTarget = byteConverter.convertToObject(bytes);
		assertTrue(readTarget.value == target.getValue());
	}

	@SuppressWarnings("unused")
	private static class TestConversionTarget<T> {

		private T value;

		private TestConversionTarget(T value) {
			this.value = value;
		}

		public T getValue() {
			return this.value;
		}

		public void setValue(T value) {
			this.value = value;
		}

	}

	private static class TestConversionIntegerTargetDelegate
	        implements JsonByteDataConverterDelegate<TestConversionTarget<Integer>> {

		@Override
		public TypeToken<TestConversionTarget<Integer>> getJsonConversionType() {
			TypeToken<TestConversionTarget<Integer>> type = new TypeToken<TestConversionTarget<Integer>>() {};
			return type;
		}

	}

}
