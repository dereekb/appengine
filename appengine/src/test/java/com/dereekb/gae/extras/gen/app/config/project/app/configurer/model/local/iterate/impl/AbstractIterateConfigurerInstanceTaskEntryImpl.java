package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.iterate.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.iterate.IterateConfigurerInstanceTaskEntry;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.utilities.data.StringUtility;

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

	/**
	 * Sets the task key the same as the task name. The proper casings will be
	 * applied.
	 *
	 * @param taskName
	 */
	public AbstractIterateConfigurerInstanceTaskEntryImpl(String taskName) {
		this(taskName, taskName);
	}

	/**
	 * Creates a new instance with the specified Task Name and Task Key.
	 * <p>
	 * The taskName is used to build the bean ids for the task and it's key.
	 * <p>
	 * The taskKey should be unique to the taskqueue, but is referenced via a
	 * key-string bean that is generated.
	 *
	 * @param taskName
	 * @param taskKey
	 */
	public AbstractIterateConfigurerInstanceTaskEntryImpl(String taskName, String taskKey) {
		super();
		this.setTaskKey(taskKey);
		this.setTaskName(taskName);
	}

	public boolean getAddModelPrefix() {
		return this.addModelPrefix;
	}

	public void setAddModelPrefix(boolean addModelPrefix) {
		this.addModelPrefix = addModelPrefix;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		if (taskName == null) {
			throw new IllegalArgumentException("taskName cannot be null.");
		}

		this.taskName = StringUtility.firstLetterUpperCase(taskName);
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setTaskKey(String taskKey) {
		if (taskKey == null) {
			throw new IllegalArgumentException("taskKey cannot be null.");
		}

		this.taskKey = StringUtility.firstLetterLowerCase(taskKey);
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
		return this.getTaskBeanId(modelConfig, "");
	}

	protected String getTaskBeanId(LocalModelConfiguration modelConfig, String suffix) {
		String prefix = (this.addModelPrefix) ? modelConfig.getModelBeanPrefix() : "";
		return StringUtility.firstLetterLowerCase(prefix + this.taskName + suffix + "Task");
	}

}