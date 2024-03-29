package com.dereekb.gae.test.app.mock.web.builder;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.dereekb.gae.test.app.mock.web.builder.impl.WebServiceRequestBuilderImpl;
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

	private WebServiceRequestBuilder requestBuilder = WebServiceRequestBuilderImpl.SINGLETON;

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

	public WebServiceRequestBuilder getRequestBuilder() {
		return this.requestBuilder;
	}

	public void setRequestBuilder(WebServiceRequestBuilder requestBuilder) {
		if (requestBuilder == null) {
			throw new IllegalArgumentException("requestBuilder cannot be null.");
		}

		this.requestBuilder = requestBuilder;
	}

	// MARK: WebServiceRequestBuilder
	@Override
	public MockHttpServletRequestBuilder request(HttpMethod method,
	                                             URI uri) {
		String servletPath = this.getServletPath(uri.getRawPath());

		if (servletPath.isEmpty() == false) {
			uri = URI.create(PathUtility.quickMerge(servletPath, uri.toString()));
		}

		return this.requestBuilder.request(method, uri).servletPath(servletPath);
	}

	@Override
	public MockHttpServletRequestBuilder request(HttpMethod method,
	                                             String urlTemplate,
	                                             Object... uriVars) {
		String servletPath = this.getServletPath(urlTemplate);

		if (servletPath.isEmpty() == false) {
			urlTemplate = PathUtility.quickMerge(servletPath, urlTemplate);
		}

		return this.requestBuilder.request(method, urlTemplate, uriVars).servletPath(servletPath);
	}

	@Override
	public MockMultipartHttpServletRequestBuilder multipartRequest(URI uri) {
		String servletPath = this.getServletPath(uri.getRawPath());

		if (servletPath.isEmpty() == false) {
			uri = URI.create(PathUtility.quickMerge(servletPath, uri.toString()));
		}

		MockMultipartHttpServletRequestBuilder builder = this.requestBuilder.multipartRequest(uri);
		builder.servletPath(servletPath);
		return builder;
	}

	@Override
	public MockMultipartHttpServletRequestBuilder multipartRequest(String urlTemplate,
	                                                               Object... uriVars) {
		String servletPath = this.getServletPath(urlTemplate);

		if (servletPath.isEmpty() == false) {
			urlTemplate = PathUtility.quickMerge(servletPath, urlTemplate);
		}

		MockMultipartHttpServletRequestBuilder builder = this.requestBuilder.multipartRequest(urlTemplate, uriVars);
		builder.servletPath(servletPath);
		return builder;
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

	@Override
	public String toString() {
		return "ServletAwareWebServiceRequestBuilder [pathMatcher=" + this.pathMatcher + ", defaultServletPath="
		        + this.defaultServletPath + ", servletMappings=" + this.servletMappings + ", requestBuilder="
		        + this.requestBuilder + "]";
	}

}
