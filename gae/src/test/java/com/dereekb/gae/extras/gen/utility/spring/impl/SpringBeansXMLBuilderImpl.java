package com.dereekb.gae.extras.gen.utility.spring.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpMethod;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLArrayBuilder;
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
import com.dereekb.gae.extras.gen.utility.spring.security.RoleConfig;
import com.dereekb.gae.extras.gen.utility.spring.security.SpringSecurityXMLCustomFilterBuilder;
import com.dereekb.gae.extras.gen.utility.spring.security.SpringSecurityXMLHttpBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.security.SpringSecurityXMLInterceptUrlBuilder;
import com.dereekb.gae.extras.gen.utility.spring.security.bean.SpringSecurityXMLCustomFilterBean;
import com.dereekb.gae.extras.gen.utility.spring.security.bean.SpringSecurityXMLHttpBean;
import com.dereekb.gae.extras.gen.utility.spring.security.bean.SpringSecurityXMLInterceptUrlBean;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.data.ValueUtility;
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
	public SpringSecurityXMLHttpBeanBuilder<SpringBeansXMLBuilder> httpSecurity() {
		return new SpringSecurityXMLHttpBeanBuilderImpl<SpringBeansXMLBuilder>(this, this.beansBuilder);
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
	public SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> enumBean(String id, Enum<?> value) {
		return this.bean(id).beanClass(value.getClass()).factoryMethod("valueOf").c().value(value.name()).up();
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

		@Override
		public SpringBeansXMLBeanBuilder<T> factoryBean(String ref) {
			this.builder.a(SpringBeansXMLBean.FACTORY_BEAN_ATTRIBUTE, ref);
			return this;
		}

		@Override
		public SpringBeansXMLBeanBuilder<T> scope(String scope) {
			this.builder.a(SpringBeansXMLBean.SCOPE_ATTRIBUTE, scope);
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

		// MARK: SpringBeansXMLListBuilder
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

		@Override
		public SpringBeansXMLListBuilder<T> values(String... values) {
			for (String value : values) {
				this.value(value);
			}

			return this;
		}

		@Override
		public SpringBeansXMLBeanBuilder<SpringBeansXMLListBuilder<T>> bean() {
			return new SpringBeansXMLBeanBuilderImpl<>(this, this.builder);
		}

	}

	private class SpringBeansXMLArrayBuilderImpl<T> extends AbstractSpringBeansXMLUtilBeanBuilderEntityImpl<T>
	        implements SpringBeansXMLArrayBuilder<T> {

		public SpringBeansXMLArrayBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
		}

		public SpringBeansXMLArrayBuilderImpl(T parent, XMLBuilder2 builder, String beanId) {
			super(parent, builder);
			this.id(beanId);
		}

		@Override
		protected String getRootBeanName() {
			return SpringBeansXMLArrayBuilder.ROOT_ARRAY_ELEMENT;
		}

		@Override
		protected String getNonRootBeanName() {
			return SpringBeansXMLArrayBuilder.ARRAY_ELEMENT;
		}

		// MARK: SpringBeansXMLArrayBuilder
		@Override
		public SpringBeansXMLArrayBuilder<T> id(String beanId) {
			this.builder.a(SpringBeansXMLBean.ID_ATTRIBUTE, beanId);
			return this;
		}

		@Override
		public SpringBeansXMLArrayBuilder<T> type(Class<T> type) {
			this.builder.a(SpringBeansXMLArrayBuilder.VALUE_CLASS_ATTRIBUTE, type.getCanonicalName());
			return this;
		}

		@Override
		public SpringBeansXMLArrayBuilder<T> ref(String ref) {
			this.builder.e("ref").a("bean", ref);
			return this;
		}

		@Override
		public SpringBeansXMLArrayBuilder<T> value(String value) {
			this.builder.e("value").text(value);
			return this;
		}

		@Override
		public SpringBeansXMLBeanBuilder<SpringBeansXMLArrayBuilder<T>> bean() {
			return new SpringBeansXMLBeanBuilderImpl<>(this, this.builder);
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
			return SpringBeansXMLMapBuilder.ROOT_MAP_ELEMENT;
		}

		@Override
		protected String getNonRootBeanName() {
			return SpringBeansXMLMapBuilder.MAP_ELEMENT;
		}

		// MARK: SpringBeansXMLMapBuilder
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

		@Override
		public SpringBeansXMLMapBuilder<T> value(String key,
		                                         String value) {
			this.entry().key(key).value(value);
			return this;
		}

		@Override
		public SpringBeansXMLMapBuilder<T> keyValueRefEntries(Map<String, String> keyValueRefsMap) {
			for (Entry<String, String> entry : keyValueRefsMap.entrySet()) {
				this.keyValueRefEntry(entry.getKey(), entry.getValue());
			}
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

		// MARK: SpringSecurityXMLInterceptUrlBuilder
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

		@Override
		public SpringBeansXMLMapEntryBuilder<T> value(String value) {
			this.builder.a(SpringBeansXMLMapEntryBuilder.VALUE_ATTRIBUTE, value);
			return this;
		}

	}

	private abstract class AbstractSpringBeansXMLUtilBeanBuilderEntityImpl<T> extends AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringBeansXMLUtilBeanBuilderEntity<T> {

		private boolean isRootBean;

		public AbstractSpringBeansXMLUtilBeanBuilderEntityImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
		}

		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			this.isRootBean = builder.equals(builder.root());

			if (this.isRootBean) {
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

		private Integer nextArgIndex;

		public SpringBeansXMLBeanConstructorBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder, true);
			this.nextArgIndex = ValueUtility.defaultTo(this.nextArgIndex, 0);
		}

		// MARK: Init
		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			return builder;	// Only use the builder here directly!
		}

		@Override
		protected void initForLoadedBuilderElement() {
			this.nextArgIndex = 0;

			NodeList children = this.builder.getElement().getChildNodes();

			int count = children.getLength();

			if (count == 0) {
				return;
			}

			for (int i = 0; i < count; i += 1) {
				Node child = children.item(i);
				String name = child.getNodeName();

				if (name == SpringBeansXMLBeanConstructor.ARG_ELEMENT) {
					this.nextArgIndex += 1;
				}
			}
		}

		// MARK: SpringBeansXMLBeanConstructorBuilder
		@Override
		public XMLBuilder2 nextArgBuilder() {
			Integer arg = this.nextArgIndex;
			this.nextArgIndex += 1;
			return this.builder.e(SpringBeansXMLBeanConstructor.ARG_ELEMENT)
			        .a(SpringBeansXMLBeanConstructor.ARG_INDEX_ATTRIBUTE, arg.toString());
		}

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

		@Override
		public SpringBeansXMLBeanConstructorBuilder<T> nullArg() {
			this.nextArgBuilder().e(SpringBeansXMLBeanConstructor.NULL_ELEMENT);
			return this;
		}

		@Override
		public SpringBeansXMLBeanConstructorBuilder<T> enumBean(Enum<?> value) {
			return this.bean().beanClass(value.getClass()).factoryMethod("valueOf").c().value(value.name()).up().up();
		}

		@Override
		public SpringBeansXMLBeanBuilder<SpringBeansXMLBeanConstructorBuilder<T>> bean() {
			return new SpringBeansXMLBeanBuilderImpl<SpringBeansXMLBeanConstructorBuilder<T>>(this,
			        this.nextArgBuilder());
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

		@Override
		public SpringBeansXMLArrayBuilder<SpringBeansXMLBeanConstructorBuilder<T>> array() {
			XMLBuilder2 builder = this.nextArgBuilder();
			return new SpringBeansXMLArrayBuilderImpl<SpringBeansXMLBeanConstructorBuilder<T>>(this, builder);
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

		// MARK: SpringSecurityXMLInterceptUrlBuilder
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

	// MARK: HTTP Security
	private class SpringSecurityXMLHttpBeanBuilderImpl<T> extends AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringSecurityXMLHttpBeanBuilder<T> {

		public SpringSecurityXMLHttpBeanBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
		}

		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			return builder.e(SpringSecurityXMLHttpBean.ELEMENT);
		}

		// MARK: SpringSecurityXMLHttpBeanBuilder
		@Override
		public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> i() {
			return this.intercept();
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> i(String pattern,
		                                                                                   RoleConfig access) {
			return this.intercept(pattern, access);
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> i(String pattern,
		                                                                                   RoleConfig access,
		                                                                                   HttpMethod method) {
			return this.intercept(pattern, access, method);
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> i(String pattern,
		                                                                                   String access,
		                                                                                   HttpMethod method) {
			return this.intercept(pattern, access, method);
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> intercept() {
			return new SpringSecurityXMLInterceptUrlBuilderImpl<SpringSecurityXMLHttpBeanBuilder<T>>(this,
			        this.builder);
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> intercept(String pattern,
		                                                                                           RoleConfig access) {
			return this.intercept(pattern, access.getAccess(), null);
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> intercept(String pattern,
		                                                                                           RoleConfig access,
		                                                                                           HttpMethod method) {
			return this.intercept(pattern, access.getAccess(), method);
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> intercept(String pattern,
		                                                                                           String access,
		                                                                                           HttpMethod method) {
			SpringSecurityXMLInterceptUrlBuilder<SpringSecurityXMLHttpBeanBuilder<T>> builder = this.intercept()
			        .pattern(pattern).access(access);

			if (method != null) {
				builder.method(method);
			}

			return builder;
		}

		@Override
		public SpringSecurityXMLCustomFilterBuilder<SpringSecurityXMLHttpBeanBuilder<T>> filter() {
			return new SpringSecurityXMLCustomFilterBuilderImpl<SpringSecurityXMLHttpBeanBuilder<T>>(this,
			        this.builder);
		}

		@Override
		public SpringSecurityXMLCustomFilterBuilder<SpringSecurityXMLHttpBeanBuilder<T>> filter(String ref,
		                                                                                        String position) {
			return this.filter().ref(ref).position(position);
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> accessDeniedHandlerRef(String ref) {
			this.builder.e(SpringSecurityXMLHttpBean.ACCESS_DENIED_HANDLER_ELEMENT).a("ref", ref);
			return this;
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> useExpressions() {
			this.builder.a(SpringSecurityXMLHttpBean.USE_EXPRESSIONS, new Boolean(true).toString());
			return this;
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> noAnonymous() {
			return this.anonymous(false);
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> anonymous(boolean enabled) {
			this.builder.e(SpringSecurityXMLHttpBean.ANONYMOUS_ELEMENT).a("enabled", new Boolean(enabled).toString());
			return this;
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> noCsrf() {
			return this.csrf(false);
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> csrf(boolean enabled) {
			this.builder.e(SpringSecurityXMLHttpBean.CSRF_ELEMENT).a("disabled", new Boolean(!enabled).toString());
			return this;
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> stateless() {
			return this.createSession("stateless");
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> createSession(String createSessionOption) {
			this.builder.a(SpringSecurityXMLHttpBean.CREATE_SESSION_ATTRIBUTE, createSessionOption);
			return this;
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> requestMatcherRef(String ref) {
			this.builder.a(SpringSecurityXMLHttpBean.REQUEST_MATCHER_REF_ATTRIBUTE, ref);
			return this;
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> entryPointRef(String ref) {
			this.builder.a(SpringSecurityXMLHttpBean.ENTRY_POINT_REF_ATTRIBUTE, ref);
			return this;
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> pattern(String pattern) {
			this.builder.a(SpringSecurityXMLHttpBean.PATTERN_ATTRIBUTE, pattern);
			return this;
		}

		@Override
		public SpringSecurityXMLHttpBeanBuilder<T> security(String security) {
			this.builder.a(SpringSecurityXMLHttpBean.SECURITY_ATTRIBUTE, security);
			return this;
		}

	}

	// MARK:
	private class SpringSecurityXMLInterceptUrlBuilderImpl<T> extends AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringSecurityXMLInterceptUrlBuilder<T> {

		public SpringSecurityXMLInterceptUrlBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
		}

		// MARK: Init
		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			return builder.e(SpringSecurityXMLInterceptUrlBean.ELEMENT);
		}

		// MARK: SpringSecurityXMLInterceptUrlBuilder
		@Override
		public SpringSecurityXMLInterceptUrlBuilder<T> access(String string) {
			this.builder.a(SpringSecurityXMLInterceptUrlBean.ACCESS_ATTRIBUTE, string);
			return this;
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<T> method(HttpMethod method) {
			this.builder.a(SpringSecurityXMLInterceptUrlBean.METHOD_ATTRIBUTE, method.name());
			return this;
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<T> pattern(String urlPattern) {
			this.builder.a(SpringSecurityXMLInterceptUrlBean.PATTERN_ATTRIBUTE, urlPattern);
			return this;
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<T> access(RoleConfig access) {
			return this.access(access.getAccess());
		}

		@Override
		public SpringSecurityXMLInterceptUrlBuilder<T> matcherRef(String matcher) {
			this.builder.a(SpringSecurityXMLInterceptUrlBean.REQUEST_MATCHER_REF_ATTRIBUTE, matcher);
			return this;
		}

	}

	private class SpringSecurityXMLCustomFilterBuilderImpl<T> extends AnstractSpringBeansXMLBuilderEntityImpl<T>
	        implements SpringSecurityXMLCustomFilterBuilder<T> {

		public SpringSecurityXMLCustomFilterBuilderImpl(T parent, XMLBuilder2 builder) {
			super(parent, builder);
		}

		// MARK: Init
		@Override
		protected XMLBuilder2 makeBuilderForElement(XMLBuilder2 builder) {
			return builder.e(SpringSecurityXMLCustomFilterBean.ELEMENT);
		}

		// MARK: SpringSecurityXMLCustomFilterUrlBuilder
		@Override
		public SpringSecurityXMLCustomFilterBuilder<T> ref(String ref) {
			this.builder.a(SpringSecurityXMLCustomFilterBean.REF_ATTRIBUTE, ref);
			return this;
		}

		@Override
		public SpringSecurityXMLCustomFilterBuilder<T> position(String position) {
			this.builder.a(SpringSecurityXMLCustomFilterBean.POSITION_ATTRIBUTE, position);
			return this;
		}

	}

	// MARK: Abstract
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
