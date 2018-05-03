package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.iterate.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.iterate.IterateConfigurerInstanceTaskEntry;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;

/**
 * Abstract {@link IterateConfigurerInstanceTaskEntry} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractIterateConfigurerInstanceTaskEntryImpl
        implements IterateConfigurerInstanceTaskEntry {

	private boolean addModelPrefix = true;

	private String taskName;
	private String taskKey;

	public AbstractIterateConfigurerInstanceTaskEntryImpl(String taskName, String taskKey) {
		super();
		this.setTaskKey(taskKey);
		this.setTaskName(taskName);
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		if (taskName == null) {
			throw new IllegalArgumentException("taskName cannot be null.");
		}

		this.taskName = taskName;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setTaskKey(String taskKey) {
		if (taskKey == null) {
			throw new IllegalArgumentException("taskKey cannot be null.");
		}

		this.taskKey = taskKey;
	}

	// MARK: IterateConfigurerInstanceTaskEntry
	@Override
	public void configureMapEntry(LocalModelConfiguration modelConfig,
	                              SpringBeansXMLMapBuilder<?> map) {
		map.entry(this.getTaskKeyBeanId(modelConfig), true).valueRef(this.getTaskBeanId(modelConfig));
	}

	@Override
	public final void configureTaskComponents(AppConfiguration appConfig,
	                                          LocalModelConfiguration modelConfig,
	                                          SpringBeansXMLBuilder builder) {
		builder.comment(this.getTaskName() + " Task");
		builder.comment(" ---- ");
		this.configureTaskKey(appConfig, modelConfig, builder);
		this.configureTaskBeans(appConfig, modelConfig, builder);
		builder.comment(" ---- ");
	}

	protected void configureTaskKey(AppConfiguration appConfig,
	                                LocalModelConfiguration modelConfig,
	                                SpringBeansXMLBuilder builder) {
		builder.stringBean(this.getTaskKeyBeanId(modelConfig), this.taskKey);
	}

	protected abstract void configureTaskBeans(AppConfiguration appConfig,
	                                           LocalModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder);

	// MARK: Internal
	protected String getTaskKeyBeanId(LocalModelConfiguration modelConfig) {
		return this.getTaskBeanId(modelConfig) + "Key";
	}

	protected String getTaskBeanId(LocalModelConfiguration modelConfig) {
		String prefix = (this.addModelPrefix) ? modelConfig.getModelBeanPrefix() : "";
		return prefix + this.taskName + "Task";
	}

}