package com.dereekb.gae.model.extension.links.components.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.links.components.exception.LinkSaveConditionException;
import com.dereekb.gae.model.extension.links.components.exception.LinkSaveException;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelChange;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelSetChange;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Implementation of {@link LinkModelSet}.
 *
 * Provides lazy loading through the delegate.
 *
 * @author dereekb
 *
 */
public final class LinkModelSetImpl<T extends UniqueModel>
        implements LinkModelSet {

	/**
	 * Map of models that have already been loaded.
	 */
	private List<LinkModelImpl<T>> typedLoaded = new ArrayList<LinkModelImpl<T>>();
	private Map<ModelKey, LinkModel> loaded = new HashMap<ModelKey, LinkModel>();

	private Set<ModelKey> allKeys = new HashSet<ModelKey>();
	private Set<ModelKey> loadedKeys = new HashSet<ModelKey>();
	private Set<ModelKey> missingKeys = new HashSet<ModelKey>();

	/**
	 * Keys that have not yet been loaded.
	 */
	private Set<ModelKey> waitingKeys = new HashSet<ModelKey>();

	private final LinkModelImplDelegate<T> modelDelegate;
	private final LinkModelSetImplDelegate<T> setDelegate;

	public LinkModelSetImpl(LinkModelImplDelegate<T> modelDelegate, LinkModelSetImplDelegate<T> setDelegate) {
		this.modelDelegate = modelDelegate;
		this.setDelegate = setDelegate;
	}

	@Override
	public Set<ModelKey> getModelKeys() {
		return new HashSet<ModelKey>(this.allKeys);
	}

	@Override
	public Collection<LinkModel> getLinkModels() {
		this.loadValues();
		return this.loaded.values();
	}

	@Override
	public LinkModel getModelForKey(ModelKey key) {
		this.loadValues();
		return this.loaded.get(key);
	}

	@Override
	public List<LinkModel> getModelsForKeys(Collection<ModelKey> keys) {
		this.loadValues();
		List<LinkModel> models = new ArrayList<LinkModel>();

		for (ModelKey key : keys) {
			LinkModel model = this.loaded.get(key);
			models.add(model);
		}

		return models;
	}

	@Override
    public Set<ModelKey> getAvailableModelKeys() {
		return this.loadedKeys;
    }

	@Override
	public Set<ModelKey> getMissingModelKeys() {
		return this.missingKeys;
	}

	@Override
	public void loadModels(Collection<ModelKey> keys) {
		this.waitingKeys.addAll(keys);
	}

	@Override
	public void validateChanges() throws LinkSaveConditionException {
		LinkModelSetChange changes = this.getChanges();
		List<T> models = this.getLoadedModels();
		this.validateChanges(models, changes);
	}

	@Override
	public void save(boolean validate) throws LinkSaveException, LinkSaveConditionException {
		LinkModelSetChange changes = this.getChanges();
		List<T> models = this.getLoadedModels();

		if (validate) {
			this.validateChanges(models, changes);
		}

		this.setDelegate.saveModels(models);
		this.setDelegate.reviewModels(models, changes);
	}

	private void validateChanges(List<T> models,
	                             LinkModelSetChange changes) {
		this.setDelegate.validateModels(models, changes);
	}

	// Internal
	/**
	 * Loads all models that haven't yet been loaded.
	 */
	private void loadValues() {
		if (this.waitingKeys.isEmpty() == false) {
			ReadResponse<T> response = this.setDelegate.readModels(this.waitingKeys);
			Collection<T> models = response.getModels();

			for (T model : models) {
				ModelKey key = model.getModelKey();
				LinkModelImpl<T> linkModel = this.loadLinkModel(model);
				this.loaded.put(key, linkModel);
				this.typedLoaded.add(linkModel);
				this.loadedKeys.add(key);
				this.waitingKeys.remove(key);
			}

			this.missingKeys.addAll(this.waitingKeys);
			this.waitingKeys = new HashSet<ModelKey>();
		}
	};

	private LinkModelImpl<T> loadLinkModel(T model) {
		LinkModelImpl<T> linkModel = null;

		if (model != null) {
			linkModel = new LinkModelImpl<T>(model, this.modelDelegate);
		}

		return linkModel;
	}

	private List<T> getLoadedModels() {
		List<T> models = new ArrayList<T>();

		for (LinkModelImpl<T> linkModel : this.typedLoaded) {
			models.add(linkModel.getModel());
		}

		return models;
	}

	@Override
	public LinkModelSetChangeImpl getChanges() {
		Map<ModelKey, LinkModelChange> modelChanges = new HashMap<ModelKey, LinkModelChange>();

		for (Entry<ModelKey, LinkModel> entry : this.loaded.entrySet()) {
			LinkModel model = entry.getValue();
			LinkModelChange modelChange = model.getModelChanges();

			if (modelChange.hasChanges()) {
				modelChanges.put(entry.getKey(), modelChange);
			}
		}

		LinkModelSetChangeImpl changes = new LinkModelSetChangeImpl(this, modelChanges);
		return changes;
	}

	@Override
	public String toString() {
		return "LinkModelSetImpl [typedLoaded=" + this.typedLoaded + ", allKeys=" + this.allKeys + "]";
    }


}
