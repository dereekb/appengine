package com.dereekb.gae.test.extras.gen.utility.spring.impl;

import java.util.List;

import com.dereekb.gae.test.extras.gen.utility.GenFile;
import com.dereekb.gae.test.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.test.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.test.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.test.extras.gen.utility.spring.SpringBeansXMLBuilderEntity;
import com.dereekb.gae.test.extras.gen.utility.spring.bean.SpringBeansXMLBean;
import com.dereekb.gae.test.extras.gen.utility.spring.bean.SpringBeansXMLBeanConstructor;
import com.dereekb.gae.test.extras.gen.utility.spring.bean.impl.SpringBeansXMLBeanImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.jamesmurty.utils.XMLBuilder2;

/**
 * {@link SpringBeansXMLBuilder} implementation.
 *
 * @author dereekb
 *
 */
public class SpringBeansXMLBuilderImpl
        implements SpringBeansXMLBuilder {

	public static final String ROOT_BEANS_ELEMENT = "beans";
	public static final String BEAN_ELEMENT = "bean";

	private XMLBuilder2 beansBuilder;

	public SpringBeansXMLBuilderImpl(XMLBuilder2 builder) {
		super();
		this.setBeansBuilder(builder);
	}

	public static SpringBeansXMLBuilder make() {

		XMLBuilder2 builder = XMLBuilder2.create(ROOT_BEANS_ELEMENT)
		        .attr("xmlns", "http://www.springframework.org/schema/beans")
		        .attr("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
		        .attr("xmlns:p", "http://www.springframework.org/schema/p")
		        .attr("xmlns:context", "http://www.springframework.org/schema/context")
		        .attr("xmlns:mvc", "http://www.springframework.org/schema/mvc")
		        .attr("xmlns:security", "http://www.springframework.org/schema/security")
		        .attr("xmlns:util", "http://www.springframework.org/schema/util").attr("xsi:schemaLocation",
		                "http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd");

		return new SpringBeansXMLBuilderImpl(builder);
	}

	@Override
	public XMLBuilder2 getRawXMLBuilder() {
		return this.beansBuilder;
	}

	public XMLBuilder2 getBeansBuilder() {
		return this.beansBuilder;
	}

	public void setBeansBuilder(XMLBuilder2 beansBuilder) {
		if (beansBuilder == null) {
			throw new IllegalArgumentException("beansBuilder cannot be null.");
		}

		this.beansBuilder = beansBuilder;
	}

	// MARK: Builder
	@Override
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> bean(String id) {
		return new SpringBeansXMLBeanBuilderImpl<SpringBeansXMLBuilder>(this, this.beansBuilder, id);
	}

	@Override
	public void imp(String resource) {
		this.importResource(resource);
	}

	@Override
	public void importResources(List<GenFile> files) {
		for (GenFile file : files) {
			this.importResource(file);
		}
	}

	@Override
	public void importResource(GenFile file) {
		this.importResource(file.getOutputFileName());
	}

	@Override
	public void importResource(String resource) {
		resource = StringUtility.startWith("/", resource);
		this.beansBuilder.e(IMPORT_ELEMENT).attr(IMPORT_ELEMENT_RESOURCE_ATTR, resource);
	}

	@Override
	public void comment(String comment) {
		this.beansBuilder.comment(comment);
	}

	// MARK: Bean
	public static final String IMPORT_ELEMENT = "import";
	public static final String IMPORT_ELEMENT_RESOURCE_ATTR = "resource";

	private class SpringBeansXMLBeanBuilderImpl<T> extends AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringBeansXMLBeanBuilder<T> {

		public SpringBeansXMLBeanBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
		}

		public SpringBeansXMLBeanBuilderImpl(T parent, XMLBuilder2 builder, String beanId) {
			super(parent, builder);
			this.id(beanId);
		}

		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			return builder.e(BEAN_ELEMENT);
		}

		// MARK: SpringBeansXMLBeanBuilder
		@Override
		public SpringBeansXMLBeanBuilder<T> id(String beanId) {
			this.builder.a(SpringBeansXMLBean.ID_ATTRIBUTE, beanId);
			return this;
		}

		@Override
		public SpringBeansXMLBeanBuilder<T> beanClass(Class<?> beanClass) {
			return this.beanClass(beanClass.getCanonicalName());
		}

		@Override
		public SpringBeansXMLBeanBuilder<T> beanClass(String beanClass) {
			this.builder.a(SpringBeansXMLBean.CLASS_ATTRIBUTE, beanClass);
			return this;
		}

		@Override
		public SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<T>> c() {
			return this.constructor();
		}

		@Override
		public SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<T>> constructor() {
			return new SpringBeansXMLBeanConstructorBuilderImpl<>(this, this.builder);
		}

		@Override
		public SpringBeansXMLBean getBean() {
			return new SpringBeansXMLBeanImpl(this.builder);
		}

	}

	// MARK: Bean Constructor Arg
	private class SpringBeansXMLBeanConstructorBuilderImpl<T> extends AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringBeansXMLBeanConstructorBuilder<T> {

		private int nextArgIndex = 0;

		public SpringBeansXMLBeanConstructorBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder, true);
		}

		// MARK: Init
		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			return builder;	// Only use the builder here directly!
		}

		@Override
		protected void initForLoadedBuilderElement() {
			// TODO: Could initialize the nextArgIndex, etc.
		}

		// MARK: SpringBeansXMLBeanConstructorBuilder
		@Override
		public SpringBeansXMLBeanConstructorBuilder<T> ref(String ref) {
			this.nextArgBuilder().a(SpringBeansXMLBeanConstructor.ARG_REF_ATTRIBUTE, ref);
			return this;
		}

		public XMLBuilder2 nextArgBuilder() {
			Integer arg = this.nextArgIndex;
			this.nextArgIndex += 1;
			return this.builder.e(SpringBeansXMLBeanConstructor.ARG_ELEMENT)
			        .a(SpringBeansXMLBeanConstructor.ARG_INDEX_ATTRIBUTE, arg.toString());
		}

	}

	/**
	 * Abstract entity that wraps the builder and "up".
	 *
	 * @author dereekb
	 *
	 * @param <T>
	 */
	protected abstract class AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringBeansXMLBuilderEntity<T> {

		protected final T parent;
		protected final XMLBuilder2 builder;

		public AnstractSpringBeansXMLBuilderEntityImpl(T parent, XMLBuilder2 builder) {
			this(parent, builder, false);
		}

		public AnstractSpringBeansXMLBuilderEntityImpl(T parent, XMLBuilder2 builder, boolean load) {
			this.parent = parent;

			if (!load) {
				this.builder = this.makeBuilderForElement(builder);
			} else {
				this.builder = builder;
				this.initForLoadedBuilderElement();
			}
		}

		public XMLBuilder2 getBuilder() {
			return this.builder;
		}

		protected abstract XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder);

		protected void initForLoadedBuilderElement() {
			// Do nothing by default.
		}

		// MARK: SpringBeansXMLBuilderEntity
		@Override
		public XMLBuilder2 getRawXMLBuilder() {
			return this.getBuilder();
		}

		@Override
		public T up() {
			return this.parent;
		}

		@Override
		public SpringBeansXMLBuilder done() {
			return SpringBeansXMLBuilderImpl.this;
		}

	}

}
