package com.dereekb.gae.utilities.query.builder.parameters.utility;

import java.util.Map;

import com.dereekb.gae.utilities.query.builder.parameters.KeyedEncodedQueryParameter;

/**
 * Static utility type.
 * 
 * @author dereekb
 *
 */
public class ParameterUtility {

	public static void put(Map<String, String> parameters,
	                       KeyedEncodedQueryParameter parameter) {

		if (parameter != null) {
			parameters.put(parameter.getParameterKey(), parameter.getParameterString());
		}
	}

}
