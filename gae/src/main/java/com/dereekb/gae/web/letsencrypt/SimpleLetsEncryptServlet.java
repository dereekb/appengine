package com.dereekb.gae.web.letsencrypt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link LetsEncryptServlet} that is simpler to configure.
 *
 * @author dereekb
 *
 */
public class SimpleLetsEncryptServlet extends LetsEncryptServlet {

	private static final long serialVersionUID = 1L;

	public static final String JOINER = ".";

	public SimpleLetsEncryptServlet(List<String> keys, String suffix) {
		super(makeChallenges(keys, suffix));
	}

	private static Map<String, String> makeChallenges(List<String> keys,
	                                                  String suffix) {
		Map<String, String> map = new HashMap<String, String>();

		for (String key : keys) {
			String response = key + JOINER + suffix;
			map.put(key, response);
		}

		return map;
	}

	@Override
	public String toString() {
		return "SimpleLetsEncryptServlet []";
	}

}
