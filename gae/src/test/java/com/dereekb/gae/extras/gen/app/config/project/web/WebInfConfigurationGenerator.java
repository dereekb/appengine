package com.dereekb.gae.extras.gen.app.config.project.web;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.web.WebXMLBuilderImpl;
import com.jamesmurty.utils.XMLBuilder2;

/**
 * Used for generating web-inf related configurations, such as web.xml for an
 * app.
 *
 * @author dereekb
 *
 */
public class WebInfConfigurationGenerator extends AbstractConfigurationFileGenerator {

	public static final String WEB_XML_FILE_NAME = "web";

	public WebInfConfigurationGenerator(AbstractConfigurationFileGenerator generator) {
		super(generator);
	}

	public WebInfConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	public WebInfConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolder generateConfigurations() {
		GenFolderImpl folder = new GenFolderImpl();

		folder.addFile(this.makeWebFile());

		// TODO: Build other configurations (queue.xml, etc.)

		return folder;
	}

	/**
	 * Generates web.xm for an application.
	 *
	 * @return {@link GenFile}. Never {@code null}.
	 */
	public GenFile makeWebFile() {

		WebXMLBuilderImpl builder = WebXMLBuilderImpl.make();

		// Display Name
		String displayName = this.getAppConfig().getAppName() + " API";
		builder.addDisplayName(displayName);

		// Configuration Contexts
		builder.getRawXMLBuilder().comment("Shared App Context");
		builder.addSpringContextParam("/WEB-INF/spring/context.xml");

		// Listener
		builder.addDefaultSpringContextListener();

		// API
		String apiServletName = "api";
		builder.getRawXMLBuilder().comment("API Servlet");
		builder.addSpringServlet(apiServletName, "/WEB-INF/spring/api.xml", 0);

		builder.addSpringServletMapping(apiServletName, this.getAppConfig().getAppServiceConfigurationInfo().getRootAppApiPath() + "/*");

		// Taskqueue
		String taskqueueServletName = "taskqueue";
		builder.getRawXMLBuilder().comment("Taskqueue Servlet");
		builder.addSpringServlet(taskqueueServletName, "/WEB-INF/spring/taskqueue.xml", null);

		String taskQueuePattern = "taskqueue/*";

		builder.addSpringServletMapping(taskqueueServletName, taskQueuePattern);

		// Security Constraint
		builder.getRawXMLBuilder().comment("Restrict outside access to taskqueue API calls.");
		XMLBuilder2 adminTaskQueueConstraint = builder.makeSecurityContraintElement(taskqueueServletName, taskQueuePattern);
		builder.addAuthConstraintElementsTo(adminTaskQueueConstraint, "admin");

		// MARK: Filters
		builder.getRawXMLBuilder().comment("Filters");
		builder.getRawXMLBuilder().comment("Objectify");
		String objectifyFilterName = "ObjectifyFilter";

		builder.addFilter(objectifyFilterName, com.googlecode.objectify.ObjectifyFilter.class);
		builder.addFilterMapping(objectifyFilterName, apiServletName);
		builder.addFilterMapping(objectifyFilterName, taskqueueServletName);

		builder.getRawXMLBuilder().comment("Spring Security");
		String springSecurityFilterChainName = "springSecurityFilterChain";

		builder.addFilter(springSecurityFilterChainName, org.springframework.web.filter.DelegatingFilterProxy.class);
		builder.addFilterMapping(springSecurityFilterChainName, apiServletName);
		builder.addFilterMapping(springSecurityFilterChainName, taskqueueServletName);

		// MARK: Route Protection
		builder.getRawXMLBuilder().comment("HTTPS Security Enforcement");
		XMLBuilder2 httpsSecurityConstraint = builder.makeSecurityContraintElement("all", "/*");
		builder.addHttpsTransportConstraint(httpsSecurityConstraint);

		return this.makeFileWithXML(WEB_XML_FILE_NAME, builder);
	}

}
