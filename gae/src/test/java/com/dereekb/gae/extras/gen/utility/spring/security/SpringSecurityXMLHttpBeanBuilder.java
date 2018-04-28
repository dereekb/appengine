package com.dereekb.gae.extras.gen.utility.spring.security;

import org.springframework.http.HttpMethod;

import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilderEntity;

/**
 * Specific builder for a Spring Bean.
 * <p>
 * The bean is accessible at all times for other uses.
 *
 * @author dereekb
 *
 * @param <T>
 *            return type
 */
public interface SpringSecurityXMLHttpBeanBuilder<T>
        extends SpringBeansXMLBuilderEntity<T> {

	// Intercept URL
	public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> i();

	public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> i(String pattern,
	                                                                                   RoleConfig access);

	public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> i(String pattern,
	                                                                                   RoleConfig access,
	                                                                                   HttpMethod method);

	public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> i(String pattern,
	                                                                                   String access,
	                                                                                   HttpMethod method);

	public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> intercept();

	public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> intercept(String pattern,
	                                                                                           RoleConfig access);

	public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> intercept(String pattern,
	                                                                                           RoleConfig access,
	                                                                                           HttpMethod method);

	public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> intercept(String pattern,
	                                                                                           String access,
	                                                                                           HttpMethod method);

	// Custom Filter
	public SpringSecurityXMLCustomFilterBuilder<SpringSecurityXMLHttpBeanBuilder<T>> filter();

	public SpringSecurityXMLCustomFilterBuilder<SpringSecurityXMLHttpBeanBuilder<T>> filter(String ref,
	                                                                                        String position);

	// Access Denied Handler
	/**
	 * Adds a child element for access-denied-handler, then returns the builder.
	 */
	public SpringSecurityXMLHttpBeanBuilder<T> accessDeniedHandlerRef(String ref);

	// Anonymous
	/**
	 * Calls {@link #anonymous(boolean)} with false as the argument.
	 */
	public SpringSecurityXMLHttpBeanBuilder<T> noAnonymous();

	/**
	 * Adds a child element for anonymous, then returns the builder.
	 */
	public SpringSecurityXMLHttpBeanBuilder<T> anonymous(boolean enabled);

	// CSRF
	/**
	 * Calls {@link #csrf(boolean)} with false as the argument.
	 */
	public SpringSecurityXMLHttpBeanBuilder<T> noCsrf();

	/**
	 * Adds a child element for csrf, then returns the builder.
	 */
	public SpringSecurityXMLHttpBeanBuilder<T> csrf(boolean enabled);

	// Config
	public SpringSecurityXMLHttpBeanBuilder<T> useExpressions();

	/**
	 * Short-hand for {@link #createSession(String)} with "stateless" as the
	 * argument.
	 */
	public SpringSecurityXMLHttpBeanBuilder<T> stateless();

	public SpringSecurityXMLHttpBeanBuilder<T> createSession(String createSessionOption);

	public SpringSecurityXMLHttpBeanBuilder<T> requestMatcherRef(String ref);

	public SpringSecurityXMLHttpBeanBuilder<T> entryPointRef(String ref);

	public SpringSecurityXMLHttpBeanBuilder<T> pattern(String pattern);

	public SpringSecurityXMLHttpBeanBuilder<T> security(String security);

}
