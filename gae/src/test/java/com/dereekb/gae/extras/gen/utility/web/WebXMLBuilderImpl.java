package com.dereekb.gae.extras.gen.utility.web;

import com.dereekb.gae.extras.gen.utility.spring.XMLBuilderObject;
import com.jamesmurty.utils.XMLBuilder2;

/**
 * Utility that starts off a properly configured WebXML file.
 *
 * @author dereekb
 *
 */
public class WebXMLBuilderImpl implements XMLBuilderObject {

	public static final String ROOT_BEANS_ELEMENT = "web-app";

	public static final String DISPLAY_NAME_ELEMENT = "display-name";

	public static final String CONTEXT_PARAM_ELEMENT = "context-param";
	public static final String PARAM_NAME_ELEMENT = "param-name";
	public static final String PARAM_VALUE_ELEMENT = "param-value";

	public static final String LISTENER_ELEMENT = "listener";
	public static final String LISTENER_CLASS_ELEMENT = "listener-class";

	public static final String SERVLET_ELEMENT = "servlet";
	public static final String SERVLET_NAME_ELEMENT = "servlet-name";
	public static final String SERVLET_CLASS_ELEMENT = "servlet-class";

	public static final String SERVLET_INIT_PARAM_ELEMENT = "init-param";
	public static final String SERVLET_LOAD_ON_STARTUP_ELEMENT = "load-on-startup";

	public static final String SERVLET_MAPPING_ELEMENT = "servlet-mapping";
	public static final String URL_PATTERN_ELEMENT = "url-pattern";

	public static final String SECURITY_CONSTRAINT_ELEMENT = "security-constraint";
	public static final String WEB_RESOURCE_COLLECTION_ELEMENT = "web-resource-collection";
	public static final String WEB_RESOURCE_NAME_ELEMENT = "web-resource-name";

	public static final String AUTH_CONSTRAINT_ELEMENT = "auth-constraint";
	public static final String ROLE_NAME_ELEMENT = "role-name";

	public static final String FILTER_ELEMENT = "filter";
	public static final String FILTER_NAME_ELEMENT = "filter-name";
	public static final String FILTER_CLASS_ELEMENT = "filter-class";
	public static final String FILTER_MAPPING_ELEMENT = "filter-mapping";

	public static final String USER_DATA_CONSTRAINT_ELEMENT = "user-data-constraint";
	public static final String TRANSPORT_GURANTEE_ELEMENT = "transport-guarantee";

	private XMLBuilder2 builder;

	public WebXMLBuilderImpl(XMLBuilder2 builder) {
		super();
		this.setBuilder(builder);
	}

	public static WebXMLBuilderImpl make() {

		XMLBuilder2 builder = XMLBuilder2.create(ROOT_BEANS_ELEMENT)
		        .attr("xmlns", "http://java.sun.com/xml/ns/javaee")
		        .attr("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
		        .attr("xsi:schemaLocation", "http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd")
		        .attr("version", "2.5");

		return new WebXMLBuilderImpl(builder);
	}

	@Override
	public XMLBuilder2 getRawXMLBuilder() {
		return this.builder;
	}

	public XMLBuilder2 getBuilder() {
		return this.builder;
	}

	private void setBuilder(XMLBuilder2 builder) {
		if (builder == null) {
			throw new IllegalArgumentException("builder cannot be null.");
		}

		this.builder = builder;
	}

	// MARK: Utilities
	public void addDisplayName(String displayName) {
		this.builder.element(DISPLAY_NAME_ELEMENT).text(displayName);
	}

	public void addDefaultSpringContextListener() {
		this.addListener(org.springframework.web.context.ContextLoaderListener.class);
	}

	public void addListener(Class<?> listenerClass) {
		this.builder.element(LISTENER_ELEMENT).element(LISTENER_CLASS_ELEMENT).text(listenerClass.getCanonicalName());
	}

	public void addSpringContextParam(String path) {
		XMLBuilder2 contextElement = this.builder.element(CONTEXT_PARAM_ELEMENT);
		this.addParamElementsTo(contextElement, "contextConfigLocation", path);
	}

	public void addSpringServlet(String name, String configPath, Integer loadOnStartup) {
		XMLBuilder2 servletElement = this.builder.element(SERVLET_ELEMENT);
		servletElement.element(SERVLET_NAME_ELEMENT).text(name);
		servletElement.element(SERVLET_CLASS_ELEMENT).text(org.springframework.web.servlet.DispatcherServlet.class.getCanonicalName());

		XMLBuilder2 initElement = servletElement.element(SERVLET_INIT_PARAM_ELEMENT);
		this.addParamElementsTo(initElement, "contextConfigLocation", configPath);

		if (loadOnStartup != null) {
			servletElement.comment("Load On Startup.");
			servletElement.element(SERVLET_LOAD_ON_STARTUP_ELEMENT).text(loadOnStartup.toString());
		}
	}

	public void addSpringServletMapping(String name, String pattern) {
		XMLBuilder2 servletElement = this.builder.element(SERVLET_MAPPING_ELEMENT);
		servletElement.element(SERVLET_NAME_ELEMENT).text(name);
		servletElement.element(URL_PATTERN_ELEMENT).text(pattern);
	}

	public void addParamElementsTo(XMLBuilder2 element, String name, String value) {
		element.element(PARAM_NAME_ELEMENT).text(name);
		element.element(PARAM_VALUE_ELEMENT).text(value);
	}

	public XMLBuilder2 makeSecurityContraintElement(String resource, String pattern) {
		XMLBuilder2 securityElement = this.builder.element(SECURITY_CONSTRAINT_ELEMENT);

		this.addWebResourceCollectionElementsTo(securityElement, resource, pattern);

		return securityElement;
	}

	public void addWebResourceCollectionElementsTo(XMLBuilder2 element, String resource, String pattern) {
		XMLBuilder2 resourceElement = element.element(WEB_RESOURCE_COLLECTION_ELEMENT);

		resourceElement.element(WEB_RESOURCE_NAME_ELEMENT).text(resource);
		resourceElement.element(URL_PATTERN_ELEMENT).text(pattern);
	}

	public void addAuthConstraintElementsTo(XMLBuilder2 element, String roleName) {
		XMLBuilder2 constraintElement = element.element(AUTH_CONSTRAINT_ELEMENT);
		constraintElement.element(ROLE_NAME_ELEMENT).text(roleName);
	}

	public void addFilter(String name, Class<?> type) {
		XMLBuilder2 filterElement = this.builder.element(FILTER_ELEMENT);
		filterElement.element(FILTER_NAME_ELEMENT).text(name);
		filterElement.element(FILTER_CLASS_ELEMENT).text(type.getCanonicalName());
	}

	public void addFilterMapping(String filterName, String servletName) {
		XMLBuilder2 filterElement = this.builder.element(FILTER_MAPPING_ELEMENT);
		filterElement.element(FILTER_NAME_ELEMENT).text(filterName);
		filterElement.element(SERVLET_NAME_ELEMENT).text(servletName);
	}

	public void addHttpsTransportConstraint(XMLBuilder2 httpsSecurityConstraint) {
		httpsSecurityConstraint.element(USER_DATA_CONSTRAINT_ELEMENT).element(TRANSPORT_GURANTEE_ELEMENT).text("CONFIDENTIAL");
	}

}
