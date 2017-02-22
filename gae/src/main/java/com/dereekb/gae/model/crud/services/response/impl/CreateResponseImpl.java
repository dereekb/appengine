package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.pair.InvalidCreateTemplatePair;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Default implementation of {@link CreateResponse}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public final class CreateResponseImpl<T extends UniqueModel>
        implements CreateResponse<T> {

	private Collection<T> createdModels;
	private Collection<InvalidCreateTemplatePair<T>> invalidTemplates;

	public CreateResponseImpl(Collection<T> createdModels, Collection<InvalidCreateTemplatePair<T>> invalidTemplates) {
		this.setCreatedModels(createdModels);
		this.setInvalidTemplates(invalidTemplates);
	}

	@Override
	public Collection<T> getModels() {
		return this.createdModels;
	}

	public void setCreatedModels(Collection<T> createdModels) {
		if (createdModels == null) {
			throw new IllegalArgumentException("createdModels cannot be null.");
		}

		this.createdModels = createdModels;
	}

	@Override
	public Collection<InvalidCreateTemplatePair<T>> getInvalidTemplates() {
		return this.invalidTemplates;
	}

	public void setInvalidTemplates(Collection<InvalidCreateTemplatePair<T>> invalidTemplates) {
		if (invalidTemplates == null) {
			throw new IllegalArgumentException("invalidTemplates cannot be null.");
		}

		this.invalidTemplates = invalidTemplates;
	}

	@Override
	public String toString() {
		return "CreateResponseImpl [createdModels=" + this.createdModels + ", invalidTemplates=" + this.invalidTemplates
		        + "]";
	}

}
