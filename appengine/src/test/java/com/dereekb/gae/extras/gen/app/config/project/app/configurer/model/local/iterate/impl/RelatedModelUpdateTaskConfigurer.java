package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.iterate.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdateType;
import com.dereekb.gae.model.taskqueue.updater.task.RelatedModelUpdateTask;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link RelatedModelUpdateTask} task configurer.
 *
 * @author dereekb
 *
 */
public class RelatedModelUpdateTaskConfigurer extends AbstractIterateConfigurerInstanceTaskEntryImpl {

	private String updaterFactoryBeanId;
	private RelatedModelUpdateType type = RelatedModelUpdateType.UPDATE;

	public RelatedModelUpdateTaskConfigurer(String updaterFactoryBeanId) {
		this(updaterFactoryBeanId, updaterFactoryBeanId, RelatedModelUpdateType.UPDATE);
	}

	public RelatedModelUpdateTaskConfigurer(String taskName, String updaterFactoryBeanId) {
		this(taskName, updaterFactoryBeanId, RelatedModelUpdateType.UPDATE);
	}

	public RelatedModelUpdateTaskConfigurer(String taskName, String updaterFactoryBeanId, RelatedModelUpdateType type) {
		super(taskName);
		this.setUpdaterFactoryBeanId(updaterFactoryBeanId);
		this.setType(type);
	}

	public String getUpdaterFactoryBeanId() {
		return this.updaterFactoryBeanId;
	}

	public void setUpdaterFactoryBeanId(String updaterFactoryBeanId) {
		if (StringUtility.isEmptyString(updaterFactoryBeanId)) {
			throw new IllegalArgumentException("updaterFactoryBeanId cannot be null.");
		}

		this.updaterFactoryBeanId = updaterFactoryBeanId;
	}

	public RelatedModelUpdateType getType() {
		return this.type;
	}

	public void setType(RelatedModelUpdateType type) {
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null.");
		}

		this.type = type;
	}

	// MARK: AbstractIterateConfigurerInstanceTaskEntryImpl
	@Override
	protected void configureTaskBeans(AppConfiguration appConfig,
	                                  LocalModelConfiguration modelConfig,
	                                  SpringBeansXMLBuilder builder) {
		builder.bean(this.getTaskBeanId(modelConfig)).beanClass(RelatedModelUpdateTask.class).c()
		        .value(this.type.toString()).ref(this.updaterFactoryBeanId);
	}

}
