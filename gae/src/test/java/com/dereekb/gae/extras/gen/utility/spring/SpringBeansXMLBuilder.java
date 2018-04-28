package com.dereekb.gae.extras.gen.utility.spring;

import java.util.List;

import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.spring.security.SpringSecurityXMLHttpBeanBuilder;
import com.jamesmurty.utils.XMLBuilder2;

/**
 * {@link XMLBuilder2} wrapper for building Spring configurations.
 *
 * @author dereekb
 *
 */
public interface SpringBeansXMLBuilder
        extends SpringBeansXMLObject {

	// Bean
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> bean();

	/**
	 * Start a new bean element.
	 *
	 * @param id
	 * @return {@link SpringSecurityXMLHttpBeanBuilder}. Never {@code null}.
	 */
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> bean(String id);

	// Import
	/**
	 * Short for {@link #importResource(String)}.
	 *
	 * @param resource
	 *            Resource file name.
	 */
	public void imp(String resource);

	/**
	 * @param resource
	 *            Resource file name.
	 */
	public void importResource(String resource);

	/**
	 * Imports the file.
	 *
	 * @param files
	 */
	public void importResource(GenFile files);

	/**
	 * Imports each of the files by name.
	 *
	 * @param files
	 */
	public void importResources(List<GenFile> files);

	public void alias(String existingBean,
	                  String alias);

	// Comment
	public void comment(String comment);

	// Spring

	// Spring Security
	public SpringSecurityXMLHttpBeanBuilder<SpringBeansXMLBuilder> httpSecurity();

	// Utility Beans
	public <T> SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> valueBean(String id,
	                                                                      T value);

	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> stringBean(String id,
	                                                                   String value);

	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> integerBean(String id,
	                                                                    Integer value);

	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> longBean(String id,
	                                                                 Long value);

	public SpringBeansXMLListBuilder<SpringBeansXMLBuilder> list(String id);

	public SpringBeansXMLMapBuilder<SpringBeansXMLBuilder> map(String id);

}
