package com.dereekb.gae.extras.gen.test.model.foo.dto;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelDataBuilder;

/**
 * {@link DirectionalConverter} for converting a {@link Foo} to
 * {@link FooData}.
 *
 * @author dereekb
 */
public final class FooDataBuilder extends DatabaseModelDataBuilder<Foo, FooData> {

	public FooDataBuilder() {
		super(FooData.class);
	}

	// Single Directional Converter
	@Override
	public FooData convertSingle(Foo input) throws ConversionFailureException {
		FooData data = super.convertSingle(input);

		// Data
		data.setNumber(input.getNumber());
		data.setNumberList(input.getNumberList());
		data.setStringSet(input.getStringSet());

		// Links

		return data;
	}

}
