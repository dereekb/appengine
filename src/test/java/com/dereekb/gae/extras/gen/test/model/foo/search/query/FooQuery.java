package com.dereekb.gae.extras.gen.test.model.foo.search.query;

import java.util.Map;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.server.auth.security.model.query.impl.AbstractOwnedModelQuery;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.IntegerQueryFieldParameter;

/**
 * Utility used for querying a {@link Foo}.
 *
 * @author dereekb
 *
 */
public class FooQuery extends AbstractOwnedModelQuery
        implements ConfigurableEncodedQueryParameters {

	public static final String NUMBER_FIELD = "number";

	private IntegerQueryFieldParameter number;

	public FooQuery() {
		super();
	}

	public FooQuery(Map<String, String> parameters) throws IllegalArgumentException {
		super(parameters);
	}

	public IntegerQueryFieldParameter getNumber() {
		return this.number;
	}


	public void setNumber(String number) {
		this.number = IntegerQueryFieldParameter.make(NUMBER_FIELD, number);
	}

	public void setNumber(Integer number) {
		this.number = IntegerQueryFieldParameter.make(NUMBER_FIELD, number);
	}

	// TODO

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();
		ParameterUtility.put(parameters, this.number);
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		super.setParameters(parameters);
		this.setNumber(parameters.get(NUMBER_FIELD));
	}

}
