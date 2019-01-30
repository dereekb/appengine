package com.dereekb.gae.model.extension.links.system.modification.components.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;

/**
 * {@link LinkModificationResult} implementation.
 * 
 * @author dereekb
 * 
 */
@Deprecated
public class LinkModificationResultSetImpl
        implements LinkModificationResultSet {

	private boolean isModifiedModel = false;
	private Set<LinkModificationResult> results = new HashSet<LinkModificationResult>();

	public LinkModificationResultSetImpl() {}

	public static LinkModificationResultSetImpl make(Iterable<LinkModificationResultSet> resultSets) {
		LinkModificationResultSetImpl resultSet = new LinkModificationResultSetImpl();

		resultSet.addResultSets(resultSets);

		return resultSet;
	}

	public void addResultSets(Iterable<LinkModificationResultSet> resultSets) {
		for (LinkModificationResultSet resultSet : resultSets) {
			this.addResultSet(resultSet);
		}
	}

	public void addResultSet(LinkModificationResultSet resultSet) {
		Set<LinkModificationResult> results = resultSet.getResults();

		this.results.addAll(results);
		this.isModifiedModel = this.isModifiedModel || resultSet.isModelModified();
	}

	// MARK: LinkModificationResultSet
	@Override
	public boolean isModelModified() {
		return this.isModifiedModel;
	}

	@Override
	public Set<LinkModificationResult> getResults() {
		return this.results;
	}

	public void addResults(List<LinkModificationResult> results) {
		boolean modified = this.isModifiedModel;

		for (LinkModificationResult result : results) {
			this.results.add(result);
			modified = modified || result.isModelModified();
		}

		this.isModifiedModel = modified;
	}

	public void addResult(LinkModificationResult result) {
		this.results.add(result);
		this.isModifiedModel = this.isModifiedModel || result.isModelModified();
	}

	@Override
	public String toString() {
		return "LinkModificationResultSetImpl [isModifiedModel=" + this.isModifiedModel + ", results=" + this.results
		        + "]";
	}

}
