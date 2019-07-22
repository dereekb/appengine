package com.dereekb.gae.server.app.model.app.search.query;

import java.util.Map;

import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.auth.model.login.misc.owned.query.MutableLoginOwnedQuery;
import com.dereekb.gae.server.auth.security.model.query.impl.AbstractOwnedModelQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.dereekb.gae.utilities.query.builder.parameters.impl.IntegerQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.StringQueryFieldParameter;

/**
 * Utility used for querying an {@link App}.
 *
 * @author dereekb
 *
 */
public class AppQuery extends AbstractOwnedModelQuery
        implements ConfigurableEncodedQueryParameters, MutableLoginOwnedQuery {

	public static final String LOGIN_FIELD = "login";
	public static final String LEVEL_FIELD = "level";
	public static final String SYSTEM_KEY_FIELD = "system";

	private static final ModelKeyQueryFieldParameterBuilder LOGIN_FIELD_BUILDER = ModelKeyQueryFieldParameterBuilder.NUMBER_SINGLETON;

	private ModelKeyQueryFieldParameter login;
	private IntegerQueryFieldParameter level;
	private StringQueryFieldParameter systemKey;

	public AppQuery() {
		super();
	}

	public AppQuery(Map<String, String> parameters) throws IllegalArgumentException {
		super(parameters);
	}

	@Override
	public ModelKeyQueryFieldParameter getLogin() {
		return this.login;
	}

	@Override
	public void setLogin(ModelKey login) {
		this.login = LOGIN_FIELD_BUILDER.make(LOGIN_FIELD, login);
	}

	@Override
	public void setLogin(String login) {
		this.login = LOGIN_FIELD_BUILDER.makeModelKeyParameter(LOGIN_FIELD, login);
	}

	@Override
	public void setLogin(ModelKeyQueryFieldParameter login) {
		this.login = LOGIN_FIELD_BUILDER.make(LOGIN_FIELD, login);
	}

	public IntegerQueryFieldParameter getLevel() {
		return this.level;
	}

	public void setLevel(AppLoginSecurityLevel level) {
		Integer levelInteger = null;

		if (level != null) {
			levelInteger = level.code;
		}

		this.setLevel(levelInteger);
	}

	public void setLevel(String level) {
		this.level = IntegerQueryFieldParameter.make(LEVEL_FIELD, level);
	}

	public void setLevel(Integer level) {
		this.level = IntegerQueryFieldParameter.make(LEVEL_FIELD, level);
	}

	public StringQueryFieldParameter getSystemKey() {
		return this.systemKey;
	}

	public void setSystemKey(String systemKey) throws IllegalArgumentException {
		this.systemKey = StringQueryFieldParameter.make(SYSTEM_KEY_FIELD, systemKey);
	}

	public void setSystemKey(StringQueryFieldParameter systemKey) throws IllegalArgumentException {
		this.systemKey = StringQueryFieldParameter.make(SYSTEM_KEY_FIELD, systemKey);
	}

	// MARK: ConfigurableQueryParameters
	@Override
	public Map<String, String> getParameters() {
		Map<String, String> parameters = super.getParameters();

		ParameterUtility.put(parameters, this.login);
		ParameterUtility.put(parameters, this.level);
		ParameterUtility.put(parameters, this.systemKey);

		return parameters;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		super.setParameters(parameters);
		this.setLogin(parameters.get(LOGIN_FIELD));
		this.setLevel(parameters.get(LEVEL_FIELD));
		this.setSystemKey(parameters.get(SYSTEM_KEY_FIELD));
	}

}
