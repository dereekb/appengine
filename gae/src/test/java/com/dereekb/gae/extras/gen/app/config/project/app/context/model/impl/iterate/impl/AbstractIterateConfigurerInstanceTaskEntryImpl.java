package com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.iterate.impl;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.iterate.IterateConfigurerInstanceTaskEntry;
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
	public void configureMapEntry(AppModelConfiguration modelConfig,
	                              SpringBeansXMLMapBuilder<?> map) {
		map.entry(this.getTaskKeyBeanId(modelConfig), true).valueRef(this.getTaskBeanId(modelConfig));
	}

	@Override
	public final void configureTaskComponents(AppConfiguration appConfig,
	                                          AppModelConfiguration modelConfig,
	                                          SpringBeansXMLBuilder builder) {
		builder.comment(this.getTaskName() + " Task");
		builder.comment(" ---- ");
		this.configureTaskKey(appConfig, modelConfig, builder);
		this.configureTaskBeans(appConfig, modelConfig, builder);
		builder.comment(" ---- ");
	}

	protected void configureTaskKey(AppConfiguration appConfig,
	                                AppModelConfiguration modelConfig,
	                                SpringBeansXMLBuilder builder) {
		builder.stringBean(this.getTaskKeyBeanId(modelConfig), this.taskKey);
	}

	protected abstract void configureTaskBeans(AppConfiguration appConfig,
	                                           AppModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder);

	// MARK: Internal
	protected String getTaskKeyBeanId(AppModelConfiguration modelConfig) {
		return this.getTaskBeanId(modelConfig) + "Key";
	}

	protected String getTaskBeanId(AppModelConfiguration modelConfig) {
		String prefix = (this.addModelPrefix) ? modelConfig.getModelBeanPrefix() : "";
		return prefix + this.taskName + "Task";
	}

}