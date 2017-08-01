package com.dereekb.gae.model.extension.inclusion.reader.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.inclusion.exception.InclusionTypeUnavailableException;
import com.dereekb.gae.model.extension.inclusion.reader.ModelInclusionReaderAnalysis;
import com.dereekb.gae.model.extension.links.system.components.Link;
import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModel;
import com.dereekb.gae.model.extension.links.system.readonly.TypedLinkModelReader;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithSet;
import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * {@link ModelInclusionReaderAnalysis} implementation that lazy-loads a model.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LazyModelInclusionReaderAnalysis<T extends UniqueModel>
        implements ModelInclusionReaderAnalysis<T> {

	private T model;
	private TypedLinkModelReader<T> accessor;
	
	private transient CaseInsensitiveMapWithSet<ModelKey> keysMap;
	
	public LazyModelInclusionReaderAnalysis(T model, TypedLinkModelReader<T> accessor) {
		this.setModel(model);
		this.setAccessor(accessor);
	}

	public T getModel() {
		return this.model;
	}

	public void setModel(T model) {
		if (model == null) {
			throw new IllegalArgumentException("model cannot be null.");
		}

		this.model = model;
	}

	public TypedLinkModelReader<T> getAccessor() {
		return this.accessor;
	}

	public void setAccessor(TypedLinkModelReader<T> accessor) {
		if (accessor == null) {
			throw new IllegalArgumentException("accessor cannot be null.");
		}

		this.accessor = accessor;
	}

	// MARK: ModelInclusionReaderAnalysis
	@Override
	public ModelKey getModelKey() {
		return this.model.getModelKey();
	}

	@Override
	public CaseInsensitiveSet getRelatedTypes() {
		return this.getRelationMap().keySet();
	}

	@Override
	public Collection<ModelKey> getKeysForType(String type) throws InclusionTypeUnavailableException {
		return this.getRelationMap().get(type);
	}

	@Override
	public CaseInsensitiveMapWithSet<ModelKey> getRelationMap() {
		if (this.keysMap == null) {
			this.keysMap = this.buildRelationMap();
		}
		
		return this.keysMap;
	}

	@Override
	public T getAnalyzedModel() {
		return this.model;
	}
	
	// MARK: Internal
	private CaseInsensitiveMapWithSet<ModelKey> buildRelationMap() {
		CaseInsensitiveMapWithSet<ModelKey> relationMap = new CaseInsensitiveMapWithSet<ModelKey>(); 
		LinkModel linkModel = this.accessor.makeLinkModel(this.model);
		List<? extends Link> links = linkModel.getLinks();
		
		for (Link link : links) {
			LinkInfo linkInfo = link.getLinkInfo();
			
			String relationLinkType = linkInfo.getRelationLinkType();
			Set<ModelKey> linkKeys = link.getLinkedModelKeys();
			
			relationMap.addAll(relationLinkType, linkKeys);
		}
		
		return relationMap;
	}

	@Override
	public String toString() {
		return "LazyModelInclusionReaderAnalysis [model=" + this.model + ", accessor=" + this.accessor + "]";
	}

}
