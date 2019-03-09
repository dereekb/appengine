package com.dereekb.gae.test.spring.web.builder;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.misc.path.PathUtility;

/**
 * {@link WebServiceRequestBuilder} implementation that automatically sets
 * servlet urls for generated requests.
 * 
 * @author dereekb
 *
 */
public class ServletAwareWebServiceRequestBuilder extends WebServiceRequestBuilderImpl {

	private PathMatcher pathMatcher = new AntPathMatcher();

	private String defaultServletPath = "";
	private Map<String, String> servletMappings = Collections.emptyMap();

	public PathMatcher getPathMatcher() {
		return this.pathMatcher;
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		if (pathMatcher == null) {
			throw new IllegalArgumentException("pathMatcher cannot be null.");
		}

		this.pathMatcher = pathMatcher;
	}

	public String getDefaultServletPath() {
		return this.defaultServletPath;
	}

	public void setDefaultServletPath(String defaultServletPath) {
		if (defaultServletPath == null) {
			throw new IllegalArgumentException("defaultServletPath cannot be null.");
		}

		this.defaultServletPath = defaultServletPath;
	}

	public Map<String, String> getServletMappings() {
		return this.servletMappings;
	}

	public void setServletMappings(Map<String, String> servletMappings) {
		if (servletMappings == null) {
			throw new IllegalArgumentException("servletMappings cannot be null.");
		}

		this.servletMappings = new CaseInsensitiveMap<String>(servletMappings);
	}

	// MARK: WebServiceRequestBuilder
	@Override
	public MockHttpServletRequestBuilder request(HttpMethod method,
	                                             String urlTemplate,
	                                             Object... uriVars) {
		String servletPath = this.getServletPath(urlTemplate);

		if (servletPath.isEmpty() == false) {
			urlTemplate = PathUtility.buildPath(servletPath, urlTemplate);
		}

		return MockMvcRequestBuilders.request(method, urlTemplate, uriVars).servletPath(servletPath);
	}

	// MARK: Internal
	public String getServletPath(String url) {
		String servletPath = this.defaultServletPath;

		for (Entry<String, String> entries : this.servletMappings.entrySet()) {
			String pattern = entries.getKey();
			String entryServletPath = entries.getValue();

			boolean isMatch = this.pathMatcher.match(pattern, url);

			if (isMatch) {
				servletPath = entryServletPath;
				break;
			}
		}

		return servletPath;
	}

}
