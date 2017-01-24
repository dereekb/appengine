package com.dereekb.gae.utilities.misc.parameters.utility;

import java.util.Map;

import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;

/**
 * Static utility type.
 * 
 * @author dereekb
 *
 */
public class ParameterUtility {

	public static void put(Map<String, String> parameters,
	                       KeyedEncodedParameter parameter) {

		if (parameter != null) {
			parameters.put(parameter.getParameterKey(), parameter.getParameterString());
		}
	}

}
