package com.dereekb.gae.model.extension.inclusion.service.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.service.InclusionRequest;

/**
 * {@link InclusionRequest} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class InclusionRequestImpl<T>
        implements InclusionRequest<T> {

	private Collection<T> targets;
	private Set<String> typeFilter;

	public InclusionRequestImpl(Collection<T> targets) throws IllegalArgumentException {
		this.setTargets(targets);
	}

	public InclusionRequestImpl(Collection<T> targets, Set<String> typeFilter) throws IllegalArgumentException {
		this.setTargets(targets);
		this.setTypeFilter(typeFilter);
	}

	@Override
	public Collection<T> getTargets() {
		return this.targets;
	}

	public void setTargets(Collection<T> targets) throws IllegalArgumentException {
		if (targets == null || targets.isEmpty()) {
			throw new IllegalArgumentException("Targets cannot be null and must include atleast one element.");
		}

		this.targets = targets;
	}

	@Override
	public Set<String> getTypeFilter() {
		return this.typeFilter;
	}

	public void setTypeFilter(Set<String> typeFilter) {
		if (typeFilter != null && typeFilter.isEmpty()) {
			typeFilter = null;
		}

		this.typeFilter = typeFilter;
	}

	@Override
	public String toString() {
		return "InclusionRequestImpl [typeFilter=" + this.typeFilter + ", targets=" + this.targets + "]";
	}

}
