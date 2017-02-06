package com.dereekb.gae.model.extension.links.task.impl;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.task.LinkModelChangeTaskFactory.ModelLinkChangeTaskDelegate;
import com.dereekb.gae.model.extension.links.task.LinkModelChangeTaskFactoryDelegate;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeySetQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link LinkModelChangeTaskFactoryDelegate} implementation that decodes a set
 * of keys from the task's parameters, then unlinks using them.
 * 
 * @author dereekb
 *
 */
public class ModelKeySetQueryFieldLinkChanger
        implements LinkModelChangeTaskFactoryDelegate {

	private String linkName;
	private String queryParameter;

	private ModelKeySetQueryFieldParameterBuilder builder;

	public ModelKeySetQueryFieldLinkChanger(String linkName, String queryParameter, ModelKeyType keyType) {
		this(linkName, queryParameter, ModelKeySetQueryFieldParameterBuilder.make(keyType));
	}

	public ModelKeySetQueryFieldLinkChanger(String linkName,
	        String queryParameter,
	        ModelKeySetQueryFieldParameterBuilder builder) {
		this.setLinkName(linkName);
		this.setQueryParameter(queryParameter);
		this.setBuilder(builder);
	}

	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) throws IllegalArgumentException {
		if (linkName == null || linkName.isEmpty()) {
			throw new IllegalArgumentException("Link name cannot be null or empty.");
		}

		this.linkName = linkName;
	}

	public String getQueryParameter() {
		return this.queryParameter;
	}

	public void setQueryParameter(String queryParameter) throws IllegalArgumentException {
		if (queryParameter == null || queryParameter.isEmpty()) {
			throw new IllegalArgumentException("Query Parameter cannot be null or empty.");
		}

		this.queryParameter = queryParameter;
	}

	public ModelKeySetQueryFieldParameterBuilder getBuilder() {
		return this.builder;
	}

	public void setBuilder(ModelKeySetQueryFieldParameterBuilder builder) throws IllegalArgumentException {
		if (builder == null) {
			throw new IllegalArgumentException("Builder cannot be null or empty.");
		}

		this.builder = builder;
	}

	// MARK: LinkModelChangeTaskFactoryDelegate
	@Override
	public ModelLinkChangeTaskDelegate makeTaskDelegate(IterateTaskInput input) throws IllegalArgumentException {

		Map<String, String> parameters = input.getParameters();
		String parameter = parameters.get(this.queryParameter);

		List<ModelKey> keys = this.builder.decodeModelKeysFromParameter(parameter);

		return new Delegate(keys);
	}

	// MARK: Delegate
	private class Delegate
	        implements ModelLinkChangeTaskDelegate {

		private final List<ModelKey> keys;

		private Delegate(List<ModelKey> keys) {
			this.keys = keys;
		}

		@Override
		public void modifyLinkModel(LinkModel model) throws FailedTaskException {
			Link link = model.getLink(ModelKeySetQueryFieldLinkChanger.this.linkName);
			Relation relation = new RelationImpl(this.keys);
			link.removeRelation(relation);
		}

		@Override
		public String toString() {
			return "ModelKeySetQueryFieldLinkChanger.Delegate [keys=" + this.keys + "]";
		}

	}

}
