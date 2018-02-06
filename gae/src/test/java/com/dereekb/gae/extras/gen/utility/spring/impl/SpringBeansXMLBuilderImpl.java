package com.dereekb.gae.extras.gen.utility.spring.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanPropertyBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilderEntity;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapEntryBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLUtilBeanBuilderEntity;
import com.dereekb.gae.extras.gen.utility.spring.bean.SpringBeansXMLBean;
import com.dereekb.gae.extras.gen.utility.spring.bean.SpringBeansXMLBeanConstructor;
import com.dereekb.gae.extras.gen.utility.spring.bean.SpringBeansXMLProperty;
import com.dereekb.gae.extras.gen.utility.spring.bean.impl.SpringBeansXMLBeanImpl;
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
	public static final String ALIAS_ELEMENT = "alias";

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

	@Override
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> bean() {
		return new SpringBeansXMLBeanBuilderImpl<SpringBeansXMLBuilder>(this, this.beansBuilder);
	}

	@Override
	public void alias(String existingBeanRef,
	                  String alias) {
		this.beansBuilder.e(ALIAS_ELEMENT).attr("name", existingBeanRef).attr("alias", alias);
	}

	@Override
	public <T> SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> valueBean(String id,
	                                                                      T value) {
		return this.bean(id).beanClass(value.getClass()).constructor().value(value.toString()).up();
	}

	@Override
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> stringBean(String id,
	                                                                   String value) {
		return this.bean(id).beanClass(String.class).constructor().value(value).up();
	}

	@Override
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> integerBean(String id,
	                                                                    Integer value) {
		return this.bean(id).beanClass(Integer.class).constructor().value(value.toString()).up();
	}

	@Override
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> longBean(String id,
	                                                                 Long value) {
		return this.bean(id).beanClass(Long.class).constructor().value(value.toString()).up();
	}

	@Override
	public SpringBeansXMLListBuilder<SpringBeansXMLBuilder> list(String id) {
		return new SpringBeansXMLListBuilderImpl<SpringBeansXMLBuilder>(this, this.beansBuilder, id);
	}

	@Override
	public SpringBeansXMLMapBuilder<SpringBeansXMLBuilder> map(String id) {
		return new SpringBeansXMLMapBuilderImpl<SpringBeansXMLBuilder>(this, this.beansBuilder, id);
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
		public SpringBeansXMLBeanPropertyBuilder<SpringBeansXMLBeanBuilder<T>> property(String name) {
			return new SpringBeansXMLBeanPropertyBuilderImpl<SpringBeansXMLBeanBuilder<T>>(this, this.builder)
			        .name(name);
		}

		@Override
		public SpringBeansXMLBean getBean() {
			return new SpringBeansXMLBeanImpl(this.builder);
		}

		@Override
		public SpringBeansXMLBeanBuilder<T> primary() {
			return this.primary(true);
		}

		@Override
		public SpringBeansXMLBeanBuilder<T> primary(boolean primary) {
			this.builder.a(SpringBeansXMLBean.PRIMARY_ATTRIBUTE, String.valueOf(primary));
			return this;
		}

		@Override
		public SpringBeansXMLBeanBuilder<T> lazy() {
			return this.lazy(true);
		}

		@Override
		public SpringBeansXMLBeanBuilder<T> lazy(boolean lazy) {
			this.builder.a(SpringBeansXMLBean.LAZY_INIT_ATTRIBUTE, String.valueOf(lazy));
			return this;
		}

		@Override
		public SpringBeansXMLBeanBuilder<T> factoryMethod(String method) {
			this.builder.a(SpringBeansXMLBean.FACTORY_METHOD_ATTRIBUTE, method);
			return this;
		}

	}

	private class SpringBeansXMLListBuilderImpl<T> extends AbstractSpringBeansXMLUtilBeanBuilderEntityImpl<T>
	        implements SpringBeansXMLListBuilder<T> {

		public SpringBeansXMLListBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
		}

		public SpringBeansXMLListBuilderImpl(T parent, XMLBuilder2 builder, String beanId) {
			super(parent, builder);
			this.id(beanId);
		}

		@Override
		protected String getRootBeanName() {
			return SpringBeansXMLListBuilder.ROOT_LIST_ELEMENT;
		}

		@Override
		protected String getNonRootBeanName() {
			return SpringBeansXMLListBuilder.LIST_ELEMENT;
		}

		// MARK: SpringBeansXMLBeanBuilder
		@Override
		public SpringBeansXMLListBuilder<T> id(String beanId) {
			this.builder.a(SpringBeansXMLBean.ID_ATTRIBUTE, beanId);
			return this;
		}

		@Override
		public SpringBeansXMLListBuilder<T> type(Class<T> type) {
			this.builder.a(SpringBeansXMLListBuilder.VALUE_CLASS_ATTRIBUTE, type.getCanonicalName());
			return this;
		}

		@Override
		public SpringBeansXMLListBuilder<T> ref(String ref) {
			this.builder.e("ref").a("bean", ref);
			return this;
		}

		@Override
		public SpringBeansXMLListBuilder<T> value(String value) {
			this.builder.e("value").text(value);
			return this;
		}

	}

	private class SpringBeansXMLMapBuilderImpl<T> extends AbstractSpringBeansXMLUtilBeanBuilderEntityImpl<T>
	        implements SpringBeansXMLMapBuilder<T> {

		public SpringBeansXMLMapBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
			this.keyType(String.class);
		}

		public SpringBeansXMLMapBuilderImpl(T parent, XMLBuilder2 builder, String beanId) {
			this(parent, builder);
			this.id(beanId);
		}

		@Override
		protected String getRootBeanName() {
			return SpringBeansXMLMapBuilder.ROOT_LIST_ELEMENT;
		}

		@Override
		protected String getNonRootBeanName() {
			return SpringBeansXMLMapBuilder.LIST_ELEMENT;
		}

		// MARK: SpringBeansXMLBeanBuilder
		@Override
		public SpringBeansXMLMapBuilder<T> id(String beanId) {
			this.builder.a(SpringBeansXMLBean.ID_ATTRIBUTE, beanId);
			return this;
		}

		@Override
		public SpringBeansXMLMapBuilder<T> keyType(Class<?> type) {
			this.builder.a(SpringBeansXMLMapBuilder.KEY_TYPE_ATTRIBUTE, type.getCanonicalName());
			return this;
		}

		@Override
		public SpringBeansXMLMapBuilder<T> valueType(Class<?> type) {
			this.builder.a(SpringBeansXMLMapBuilder.VALUE_TYPE_ATTRIBUTE, type.getCanonicalName());
			return this;
		}

		private SpringBeansXMLMapEntryBuilder<SpringBeansXMLMapBuilder<T>> entry() {
			return this.entry(null, false);
		}

		@Override
		public SpringBeansXMLMapEntryBuilder<SpringBeansXMLMapBuilder<T>> entry(String key) {
			return this.entry(key, false);
		}

		@Override
		public SpringBeansXMLMapEntryBuilder<SpringBeansXMLMapBuilder<T>> entry(String key,
		                                                                        boolean ref) {
			SpringBeansXMLMapEntryBuilder<SpringBeansXMLMapBuilder<T>> builder = new SpringBeansXMLMapEntryBuilderImpl<SpringBeansXMLMapBuilder<T>>(
			        this, this.builder);

			if (key != null) {
				return builder.key(key, ref);
			}

			return builder;
		}

		@Override
		public SpringBeansXMLMapBuilder<T> keyValueRefEntry(String key,
		                                                    String valueRef) {
			this.entry(key).valueRef(valueRef);
			return this;
		}

		@Override
		public SpringBeansXMLMapBuilder<T> keyRefValueRefEntry(String keyRef,
		                                                       String valueRef) {
			this.entry().keyRef(keyRef).valueRef(valueRef);
			return this;
		}

	}

	private class SpringBeansXMLMapEntryBuilderImpl<T> extends AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringBeansXMLMapEntryBuilder<T> {

		public SpringBeansXMLMapEntryBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
		}

		// MARK: Init
		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			return builder.e(SpringBeansXMLMapEntryBuilder.ELEMENT);
		}

		// MARK: SpringBeansXMLBeanPropertyBuilder
		@Override
		public SpringBeansXMLMapEntryBuilder<T> key(String key,
		                                            boolean ref) {
			if (ref) {
				return this.keyRef(key);
			} else {
				return this.key(key);
			}
		}

		@Override
		public SpringBeansXMLMapEntryBuilder<T> key(String key) {
			this.builder.a(SpringBeansXMLMapEntryBuilder.KEY_ATTRIBUTE, key);
			return this;
		}

		@Override
		public SpringBeansXMLMapEntryBuilder<T> keyRef(String keyRef) {
			this.builder.a(SpringBeansXMLMapEntryBuilder.KEY_REF_ATTRIBUTE, keyRef);
			return this;
		}

		@Override
		public SpringBeansXMLMapEntryBuilder<T> valueRef(String beanRef) {
			this.builder.a(SpringBeansXMLMapEntryBuilder.VALUE_REF_ATTRIBUTE, beanRef);
			return this;
		}

	}

	private abstract class AbstractSpringBeansXMLUtilBeanBuilderEntityImpl<T> extends AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringBeansXMLUtilBeanBuilderEntity<T> {

		private final boolean isRootBean;

		public AbstractSpringBeansXMLUtilBeanBuilderEntityImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
			this.isRootBean = builder.equals(SpringBeansXMLBuilderImpl.this.beansBuilder);
		}

		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			if (this.isRootBean()) {
				return builder.e(this.getRootBeanName());
			} else {
				return builder.e(this.getNonRootBeanName());
			}
		}

		// MARK: SpringBeansXMLUtilBeanBuilderEntity
		@Override
		public boolean isRootBean() {
			return this.isRootBean;
		}

		// MARK: Internal
		protected abstract String getRootBeanName();

		protected abstract String getNonRootBeanName();

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

		@Override
		public SpringBeansXMLBeanConstructorBuilder<T> value(String value) {
			this.nextArgBuilder().a(SpringBeansXMLBeanConstructor.ARG_VALUE_ATTRIBUTE, value);
			return this;
		}

		public XMLBuilder2 nextArgBuilder() {
			Integer arg = this.nextArgIndex;
			this.nextArgIndex += 1;
			return this.builder.e(SpringBeansXMLBeanConstructor.ARG_ELEMENT)
			        .a(SpringBeansXMLBeanConstructor.ARG_INDEX_ATTRIBUTE, arg.toString());
		}

		@Override
		public SpringBeansXMLMapBuilder<SpringBeansXMLBeanConstructorBuilder<T>> map() {
			XMLBuilder2 builder = this.nextArgBuilder();
			return new SpringBeansXMLMapBuilderImpl<SpringBeansXMLBeanConstructorBuilder<T>>(this, builder);
		}

		@Override
		public SpringBeansXMLListBuilder<SpringBeansXMLBeanConstructorBuilder<T>> list() {
			XMLBuilder2 builder = this.nextArgBuilder();
			return new SpringBeansXMLListBuilderImpl<SpringBeansXMLBeanConstructorBuilder<T>>(this, builder);
		}

	}

	// MARK: Property
	private class SpringBeansXMLBeanPropertyBuilderImpl<T> extends AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringBeansXMLBeanPropertyBuilder<T> {

		public SpringBeansXMLBeanPropertyBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
		}

		// MARK: Init
		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			return builder.e(SpringBeansXMLProperty.ELEMENT);
		}

		@Override
		protected void initForLoadedBuilderElement() {
			// TODO: Could initialize the internal property...
		}

		// MARK: SpringBeansXMLBeanPropertyBuilder
		@Override
		public SpringBeansXMLBeanPropertyBuilder<T> name(String name) {
			this.builder.a(SpringBeansXMLProperty.NAME_ATTRIBUTE, name);
			return this;
		}

		@Override
		public SpringBeansXMLBeanPropertyBuilder<T> value(String value) {
			this.builder.a(SpringBeansXMLProperty.VALUE_ATTRIBUTE, value);
			return this;
		}

		@Override
		public SpringBeansXMLBeanPropertyBuilder<T> ref(String ref) {
			this.builder.a(SpringBeansXMLProperty.REF_ATTRIBUTE, ref);
			return this;
		}

		@Override
		public SpringBeansXMLBeanBuilder<SpringBeansXMLBeanPropertyBuilder<T>> bean() {
			return new SpringBeansXMLBeanBuilderImpl<>(this, this.builder);
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
