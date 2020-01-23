package com.dereekb.gae.utilities.misc.env;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.jboss.logging.Logger;

import com.dereekb.gae.utilities.data.StringUtility;
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

	// MARK: Files
	/**
	 * Reads a string from the path defined in an env variable.
	 *
	 * @param env
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readStringFromFileFromEnvVar(String env) throws FileNotFoundException, IOException {
		String absoluteFilePath = readEnv(env);
		return readStringFromFile(absoluteFilePath);
	}

	public static String readStringFromFile(String absoluteFilePath) throws FileNotFoundException, IOException {
		Reader reader = new FileReader(absoluteFilePath);
		return StringUtility.readStringFromReader(reader);
	}

	// MARK: Env Variables
	public static String readEnv(String env) throws RuntimeException, UnavailableSourceObjectException {
		return readEnv(env, null);
	}

	public static String tryReadEnv(String env) throws RuntimeException, UnavailableSourceObjectException {
		return readEnv(env, null, false);
	}

	public static String readEnv(String env,
	                             String defaultValue)
	        throws RuntimeException,
	            UnavailableSourceObjectException {
		return readEnv(env, defaultValue, true);
	}

	public static String readEnv(String env,
	                             String defaultValue,
	                             boolean nonNullValueRequired)
	        throws RuntimeException,
	            UnavailableSourceObjectException {
		return new EnvStringFactory(env, defaultValue, nonNullValueRequired).loadObject();
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
