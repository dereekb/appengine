package com.dereekb.gae.extras.gen.utility.spring;

import com.dereekb.gae.extras.gen.utility.spring.bean.SpringBeansXMLBean;

/**
 * Specific builder for a Spring Bean.
 * <p>
 * The bean is accessible at all times for other uses.
 *
 * @author dereekb
 *
 */
public interface SpringBeansXMLBeanBuilder<T>
        extends SpringBeansXMLBuilderEntity<T> {

	public SpringBeansXMLBeanBuilder<T> id(String beanId);

	public SpringBeansXMLBeanBuilder<T> beanClass(Class<?> beanClass);

	public SpringBeansXMLBeanBuilder<T> beanClass(String beanClass);

	// Constructor
	public SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<T>> c();

	public SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<T>> constructor();

	// Bean
	public SpringBeansXMLBean getBean();

}
