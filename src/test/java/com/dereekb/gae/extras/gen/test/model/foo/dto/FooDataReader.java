package com.dereekb.gae.extras.gen.test.model.foo.dto;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.dto.DatabaseModelDataReader;

/**
 * {@link DirectionalConverter} for converting a {@link FooData} to
 * {@link Foo}.
 *
 * @author dereekb
 */
public final class FooDataReader extends DatabaseModelDataReader<Foo, FooData> {

	public FooDataReader() {
		super(Foo.class);
	}

	@Override
	public Foo convertSingle(FooData input) throws ConversionFailureException {
		Foo model = super.convertSingle(input);

		// Data
		model.setNumber(input.getNumber());
		model.setNumberList(input.getNumberList());
		model.setStringSet(input.getStringSet());

		return model;
	}

}
