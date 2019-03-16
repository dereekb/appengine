package com.dereekb.gae.server.app.model.app.shared.search.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.query.parameters.AbstractDateModelQuery;
import com.dereekb.gae.server.app.model.app.misc.owned.query.MutableAppOwnedQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;

/**
 * Abstract model query for {@link AbstractSessionMatchEntity}.
 *
 * @author dereekb
 */
public class AbstractAppRelatedModelQuery extends AbstractDateModelQuery
        implements ConfigurableEncodedQueryParameters, MutableAppOwnedQuery {

	public static final String APP_FIELD = "app";

	private static final ModelKeyQueryFieldParameterBuilder APP_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NUMBER_SINGLETON;

	private ModelKeyQueryFieldParameter app;

	public AbstractAppRelatedModelQuery() {
		super();
	}

	public AbstractAppRelatedModelQuery(Map<String, String> parameters) throws IllegalArgumentException {
		super(parameters);
	}

	@Override
	public ModelKeyQueryFieldParameter getApp() {
		return this.app;
	}

	@Override
	public void setApp(ModelKey app) {
		this.app = APP_FIELD_BUILDER.make(APP_FIELD, app);
	}

	@Override
	public void setApp(String app) {
		this.app = APP_FIELD_BUILDER.makeModelKeyParameter(APP_FIELD, app);
	}

	@Override
	public void setApp(ModelKeyQueryFieldParameter app) {
		this.app = APP_FIELD_BUILDER.make(APP_FIELD, app);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();
		ParameterUtility.put(parameters, this.app);
		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		super.setParameters(parameters);
		this.setApp(parameters.get(APP_FIELD));
	}

}
