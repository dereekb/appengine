package com.dereekb.gae.utilities.misc.parameters.utility;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.utilities.misc.parameters.KeyedEncodedParameter;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.utilities.misc.parameters.impl.KeyedEncodedParameterImpl;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameters;

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

	public static void putAll(Map<String, String> parameters,
	                          Iterable<KeyedEncodedParameter> keyedParameters) {
		if (keyedParameters != null) {
			for (KeyedEncodedParameter parameter : keyedParameters) {
				parameters.put(parameter.getParameterKey(), parameter.getParameterString());
			}
		}
	}

	public static List<KeyedEncodedParameterImpl> toParametersList(EncodedQueryParameters parameters) {
		Map<String, String> map = parameters.getParameters();
		return KeyedEncodedParameterImpl.makeParametersWithMap(map);
	}

	public static Parameters safe(Parameters parameters) {
		if (parameters == null) {
			return new ParametersImpl();
		} else {
			return parameters;
		}
	}

}
