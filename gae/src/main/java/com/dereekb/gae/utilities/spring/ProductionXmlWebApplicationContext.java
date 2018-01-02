package com.dereekb.gae.utilities.spring;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;

/**
 * Used to disable XML validation for Spring. This will improve performance, but
 * is undesirable for development.
 *
 * @author dereekb
 * @see https://cloud.google.com/appengine/articles/spring_optimization
 */
public class ProductionXmlWebApplicationContext extends XmlWebApplicationContext {

	// MARK: Override
	@Override
	protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
		super.initBeanDefinitionReader(beanDefinitionReader);

		if (this.shouldDisableXmlValidation()) {
			beanDefinitionReader.setValidating(false);
			beanDefinitionReader.setNamespaceAware(true);
		}
	}

	public boolean shouldDisableXmlValidation() {
		return GoogleAppEngineUtility.isProductionEnvironment();
	}

}