package com.dereekb.gae.utilities.misc.env;

import org.jboss.logging.Logger;

import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;

/**
 * Environment String utility.
 *
 * @author dereekb
 *
 */
public class EnvStringUtility {

	public static final String IS_PRODUCTION_ENV_VAR = "GAE_PRODUCTION";

	private static final Logger LOGGER = Logger.getLogger(EnvStringUtility.class);

	// MARK: Utility
	public static String readEnv(String env) throws RuntimeException, UnavailableSourceObjectException {
		return readEnv(env, null);
	}

	public static String readEnv(String env,
	                             String defaultValue)
	        throws RuntimeException,
	            UnavailableSourceObjectException {
		return new EnvStringFactory(env, defaultValue).loadObject();
	}

	// MARK: Production
	public static String readProdEnv(String env) throws RuntimeException, UnavailableSourceObjectException {
		return readProdEnv(env, null);
	}

	/**
	 * Similar to {{@link #readEnv(String, String)} but ignores the default
	 * value in a production environment.
	 *
	 * @param env
	 *            {@link String} env key. Never {@code null} or empty.
	 * @return {@link String}. Never {@code null}.
	 * @throws RuntimeException
	 * @throws UnavailableSourceObjectException
	 */
	public static String readProdEnv(String env,
	                                 String defaultValue)
	        throws RuntimeException,
	            UnavailableSourceObjectException {
		try {
			if (isProduction()) {
				return readEnv(env);
			} else {
				return readEnv(env, defaultValue);
			}
		} catch (RuntimeException e) {
			LOGGER.error("Required environment variable '" + env + "' was not available in production setting.");
			throw e;
		}
	}

	public static boolean isProduction() throws RuntimeException, UnavailableSourceObjectException {
		String isProduction = readEnv(IS_PRODUCTION_ENV_VAR, "false");
		return isProduction.equalsIgnoreCase("true");
	}

}
