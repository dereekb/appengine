package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntry;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntryInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeBuilder;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelInstance;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.components.impl.LinkModificationResultSetImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModelAccessorPair;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.task.Task;

/**
 * Abstract {@link LinkModificationSystemEntry} implementation for changes on
 * the same microservice.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemEntryImpl<T extends UniqueModel>
        implements LinkModificationSystemEntry {

	private MutableLinkModelAccessor<T> accessor;

	private Task<T> reviewTask;

	private LinkModificationSystemModelChangeBuilder changeBuilder = LinkModificationSystemModelChangeBuilderImpl.SINGLETON;

	// MARK: LinkModificationSystemEntry
	@Override
	public LinkModificationSystemEntryInstance makeInstance() {
		return new LinkModificationSystemEntryInstanceImpl();
	}

	// MARK: Internal
	protected class LinkModificationSystemEntryInstanceImpl extends AbstractLinkModificationSystemChangeInstance
	        implements LinkModificationSystemEntryInstance {

		private final Set<ModelKey> unavailableModels = new HashSet<ModelKey>();
		private final Map<ModelKey, LinkModificationSystemModelInstance<T>> modelsCache = new HashMap<ModelKey, LinkModificationSystemModelInstance<T>>();

		// MARK: LinkModificationSystemEntryInstance
		@Override
		public LinkModificationResultSet performModifications(HashMapWithList<ModelKey, LinkModification> keyedMap) {
			this.loadModelInstancesForKeys(keyedMap.keySet());

			List<LinkModificationResultSet> resultSets = new ArrayList<LinkModificationResultSet>();

			for (Entry<ModelKey, List<LinkModification>> entry : keyedMap.entrySet()) {
				LinkModificationResultSet resultSet = this.makeChangesToModel(entry.getKey(), entry.getValue());
				resultSets.add(resultSet);
			}

			return LinkModificationResultSetImpl.make(resultSets);
		}

		private LinkModificationResultSet makeChangesToModel(ModelKey key,
		                                                     List<LinkModification> value) {
			LinkModificationSystemModelInstance<T> instance = this.modelsCache.get(key);

			if (instance == null) {
				// TODO: Assert all changes are optional.
				return null;
			} else {
				return this.makeChangesToModel(instance, value);
			}
		}

		private LinkModificationResultSet makeChangesToModel(LinkModificationSystemModelInstance<T> instance,
		                                                     List<LinkModification> modifications) {
			List<LinkModificationSystemModelChange> changes = this.buildChangesForModifications(modifications);

			for (LinkModificationSystemModelChange change : changes) {
				instance.queueChange(change);
			}

			return instance.applyChanges();
		}

		protected List<LinkModificationSystemModelChange> buildChangesForModifications(List<LinkModification> modifications) {
			return LinkModificationSystemEntryImpl.this.changeBuilder.convert(modifications);
		}

		// MARK: Internal
		protected void loadModelInstancesForKeys(Collection<ModelKey> modelKeySet) {
			Set<ModelKey> currentKeys = new HashSet<ModelKey>(this.modelsCache.keySet());
			Collection<ModelKey> newKeys = SetUtility.makeSetDifference(currentKeys, modelKeySet).getDifference();
			newKeys.removeAll(this.unavailableModels);

			ReadRequest readRequest = new KeyReadRequest(newKeys, false);
			ReadResponse<? extends MutableLinkModelAccessorPair<T>> response = LinkModificationSystemEntryImpl.this.accessor
			        .readMutableLinkModels(readRequest);

			Collection<ModelKey> failed = response.getFailed();
			this.unavailableModels.addAll(failed);

			for (MutableLinkModelAccessorPair<T> pair : response.getModels()) {
				LinkModificationSystemModelInstance<T> instance = this.makeInstanceForAccessorPair(pair);
				this.modelsCache.put(pair.keyValue(), instance);
			}
		}

		protected LinkModificationSystemModelInstance<T> makeInstanceForAccessorPair(MutableLinkModelAccessorPair<T> pair) {
			return new LinkModificationSystemModelInstanceImpl<T>(pair.getModel(), pair.getMutableLinkModel());
		}

		// MARK: AbstractLinkModificationSystemChangeInstance
		@Override
		public Iterable<? extends LinkModificationSystemChangeInstance> getInstances() {
			return this.modelsCache.values();
		}

	}

}
