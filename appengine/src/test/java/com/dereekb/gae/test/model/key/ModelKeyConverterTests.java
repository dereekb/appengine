package com.dereekb.gae.test.model.key;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.utilities.data.NumberRepresentation;
import com.dereekb.gae.utilities.data.StringUtility;

public class ModelKeyConverterTests {

	private static final int GENERATION_COUNT = 40;

	@Test
	public void testStringLongModelConverter() {
		List<ModelKey> keys = this.generateModelKeys();
		StringLongModelKeyConverterImpl converter = new StringLongModelKeyConverterImpl(
		        NumberRepresentation.HEXADECIMAL);

		List<String> hexStrings = converter.convertFrom(keys);
		int hexLength = StringUtility.lengthOfStrings(hexStrings);

		this.AssertIncludesAllKeys(converter, keys, hexStrings);

		converter = new StringLongModelKeyConverterImpl(NumberRepresentation.DECIMAL);

		List<String> decimalStrings = converter.convertFrom(keys);
		int decimalLength = StringUtility.lengthOfStrings(decimalStrings);
		int difference = decimalLength - hexLength;

		System.out.println("Hexadecimal length: " + hexLength);
		System.out.println("Decimal length: " + decimalLength);
		System.out.println("Hexadecimal vs Decimal String length: " + difference);
		System.out.println("Savings: " + (new Double(decimalLength - hexLength) / decimalLength));
	}

	private List<ModelKey> generateModelKeys() {
		return LongModelKeyGenerator.GENERATOR.generate(GENERATION_COUNT, null);
	}

	private void AssertIncludesAllKeys(StringLongModelKeyConverterImpl converter,
	                                   List<ModelKey> keys,
	                                   List<String> strings) {
		List<ModelKey> convertedKeys = converter.convert(strings);
		Set<ModelKey> keysSet = new HashSet<>(convertedKeys);
		assertTrue(keysSet.containsAll(keys));
	}

}
