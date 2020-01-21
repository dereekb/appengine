package com.dereekb.gae.utilities.misc.env;

import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;

/**
 * {@link Source} and {@link Factory} implementation for retrieving a variable
 * from the environment.
 *
 * @author dereekb
 *
 */
public class EnvStringFactory
        implements Source<String>, Factory<String> {

	private String env;
	private String defaultValue;

	/**
	 * Whether or not a value is required. If false, {@code null} is acceptable.
	 */
	private boolean required = true;

	public EnvStringFactory(String env) {
		this(env, null);
	}

	public EnvStringFactory(String env, String defaultValue) {
		this(env, defaultValue, true);
	}

	public EnvStringFactory(String env, String defaultValue, boolean required) {
		super();
		this.setEnv(env);
		this.setDefaultValue(defaultValue);
		this.setRequired(required);
	}

	public String getEnv() {
		return this.env;
	}

	public void setEnv(String env) {
		if (StringUtility.isEmptyString(env)) {
			throw new IllegalArgumentException("env cannot be null or empty.");
		}

		this.env = env;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isRequired() {
		return this.required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	// MARK: Factory
	@Override
	public String make() throws FactoryMakeFailureException {
		try {
			return this.loadObject();
		} catch (RuntimeException e) {
			throw new FactoryMakeFailureException(e);
		}
	}

	// MARK: Source
	@Override
	public String loadObject() throws RuntimeException, UnavailableSourceObjectException {

		String value = System.getenv(this.env);

		if (StringUtility.isEmptyString(value)) {
			value = this.defaultValue;
		}

		if (StringUtility.isEmptyString(value) && (this.required || value != null)) {
			throw new UnavailableSourceObjectException("The environment variable '" + this.env + "' was not set.");
		}

		return value;
	}

	@Override
	public String toString() {
		return "EnvStringFactory [env=" + this.env + ", defaultValue=" + this.defaultValue + ", required="
		        + this.required + "]";
	}

}
