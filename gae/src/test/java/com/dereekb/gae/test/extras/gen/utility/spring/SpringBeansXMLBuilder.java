package com.dereekb.gae.test.extras.gen.utility.spring;

import java.util.List;

import com.dereekb.gae.test.extras.gen.utility.GenFile;
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
	/**
	 * Start a new bean element.
	 *
	 * @param id
	 * @return {@link SpringBeansXMLBeanBuilder}. Never {@code null}.
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

}
