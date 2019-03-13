package com.dereekb.gae.extras.gen.utility.spring.security;

import org.springframework.http.HttpMethod;

import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilderEntity;

/**
 * Intercept URL for a {@link SpringSecurityXMLHttpBeanBuilder}.
 *
 * @author dereekb
 *
 * @param <T>
 *            up type
 */
public interface SpringSecurityXMLInterceptUrlBuilder<T>
        extends SpringBeansXMLBuilderEntity<T> {

	public SpringSecurityXMLInterceptUrlBuilder<T> access(String string);

	public SpringSecurityXMLInterceptUrlBuilder<T> access(RoleConfig access);

	public SpringSecurityXMLInterceptUrlBuilder<T> method(HttpMethod method);

	public SpringSecurityXMLInterceptUrlBuilder<T> pattern(String urlPattern);

	public SpringSecurityXMLInterceptUrlBuilder<T> matcherRef(String matcher);

}
