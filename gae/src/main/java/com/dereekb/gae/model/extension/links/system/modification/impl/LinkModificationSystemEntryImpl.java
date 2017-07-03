package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntry;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntryInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeBuilder;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstanceSet;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeSet;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.components.impl.LinkModificationResultSetImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModelAccessorPair;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;

/**
 * Abstract {@link LinkModificationSystemEntry} implementation for changes on
 * the same microservice.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemEntryImpl<T extends UniqueModel>
        implements LinkModificationSystemEntry {

	protected final int INSTANCE_BATCH_SIZE = 20;
	protected final Partitioner PARTITIONER = new PartitionerImpl(this.INSTANCE_BATCH_SIZE);

	private MutableLinkModelAccessor<T> accessor;
	private Updater<T> updater;

	private TaskRequestSender<T> reviewTaskSender;

	private LinkModificationSystemModelChangeBuilder changeBuilder = LinkModificationSystemModelChangeBuilderImpl.SINGLETON;

	// MARK: LinkModificationSystemEntry
	@Override
	public LinkModificationSystemEntryInstance makeInstance() {
		return new LinkModificationSystemEntryInstanceImpl();
	}

	// MARK: Internal
	protected class LinkModificationSystemEntryInstanceImpl
	        implements LinkModificationSystemEntryInstance {

		private List<ModificationBatchSet> batchSets = new ArrayList<ModificationBatchSet>();

		// MARK: LinkModificationSystemEntryInstance
		@Override
		public LinkModificationResultSet performModifications(HashMapWithList<ModelKey, LinkModification> keyedMap) {
			ModificationBatchSet batchSet = this.makeBatchSetForChanges(keyedMap);

			return batchSet.performChangesInTransactions();
		}

		private ModificationBatchSet makeBatchSetForChanges(HashMapWithList<ModelKey, LinkModification> keyed) {
			List<List<ModelKey>> keyBatches = LinkModificationSystemEntryImpl.this.PARTITIONER
			        .makePartitions(keyed.keySet());
			List<ModificationBatch> batches = new ArrayList<ModificationBatch>(keyBatches.size());

			for (List<ModelKey> keyBatch : keyBatches) {
				ModificationBatch batch = new ModificationBatch();

				for (ModelKey key : keyBatch) {
					List<LinkModification> modifications = keyed.get(key);
					batch.addModificationSet(key, modifications);
				}

				batches.add(batch);
			}

			return new ModificationBatchSet(batches);
		}

		@Override
		public void commitChanges() {
			Set<T> modified = new HashSet<T>();

			for (ModificationBatchSet batchSet : this.batchSets) {
				Set<T> batchKeys = batchSet.getAllModifiedModels();
				modified.addAll(batchKeys);
			}

			LinkModificationSystemEntryImpl.this.reviewTaskSender.sendTasks(modified);
		}

		@Override
		public void undoChanges() {
			for (ModificationBatchSet batchSet : this.batchSets) {
				batchSet.undoChanges();
			}
		}

	}

	/**
	 * A set of {@link ModificationBatch} instances.
	 * 
	 * 
	 * @author dereekb
	 *
	 */
	private class ModificationBatchSet {

		private List<ModificationBatch> batches;

		public ModificationBatchSet(List<ModificationBatch> batches) {
			this.batches = batches;
		}

		// MARK: Actions
		public LinkModificationResultSet performChangesInTransactions() throws UnavailableModelException {
			LinkModificationResultSetImpl result = new LinkModificationResultSetImpl();

			for (ModificationBatch batch : this.batches) {
				ModificationBatchResult batchResult = batch.performChanges();
				LinkModificationResultSet resultSet = batchResult.getResultSet();
				result.addResultSet(resultSet);
			}

			return result;
		}

		public void undoChanges() {
			for (ModificationBatch batch : this.batches) {
				batch.undoChanges();
			}
		}

		// MARK: Internal
		public Set<T> getAllModifiedModels() {
			Set<T> models = new HashSet<T>();

			for (ModificationBatch batch : this.batches) {
				ModificationBatchResult batchResult = batch.getBatchResult();
				models.addAll(batchResult.getModifiedModels());
			}

			return models;
		}

	}

	private class ModificationBatch {

		private Map<ModelKey, LinkModificationSystemModelChangeSet> changeInstances = new HashMap<ModelKey, LinkModificationSystemModelChangeSet>();
		private ModificationBatchResult batchResult;

		public ModificationBatch() {
			super();
		}

		public ModificationBatchResult getBatchResult() {
			return this.batchResult;
		}

		// MARK: Internal
		public void addModificationSet(ModelKey key,
		                               List<LinkModification> modifications) {
			LinkModificationSystemModelChangeSet changeSet = LinkModificationSystemEntryImpl.this.changeBuilder
			        .makeChangeSet(modifications);
			this.changeInstances.put(key, changeSet);
		}

		public ModificationBatchResult performChanges() throws UnavailableModelException {

			Set<ModelKey> keys = ModificationBatch.this.changeInstances.keySet();
			final ReadRequest request = new KeyReadRequest(keys, false);

			// Perform the changes in a transaction
			this.batchResult = ObjectifyService.ofy().transactNew(new Work<ModificationBatchResult>() {

				@Override
				public ModificationBatchResult run() {

					// Load Models
					ReadResponse<? extends MutableLinkModelAccessorPair<T>> response = LinkModificationSystemEntryImpl.this.accessor
					        .readMutableLinkModels(request);
					Collection<ModelKey> failed = response.getFailed();

					// Assert unavailable models are
					if (failed.isEmpty() == false) {
						ModificationBatch.this.assertUnavailableModelsAreOptional(failed);
					}

					Collection<? extends MutableLinkModelAccessorPair<T>> pairs = response.getModels();

					List<T> modifiedModels = new ArrayList<T>();
					Set<ModelKey> unmodifiedModels = new HashSet<ModelKey>();
					Map<ModelKey, LinkModificationSystemModelChangeInstanceSet> instanceMap = new HashMap<ModelKey, LinkModificationSystemModelChangeInstanceSet>();

					// Apply Changes per model
					LinkModificationResultSetImpl totalResultSet = new LinkModificationResultSetImpl();

					for (MutableLinkModelAccessorPair<T> pair : pairs) {
						MutableLinkModel linkModel = pair.getMutableLinkModel();
						ModelKey key = linkModel.getModelKey();

						LinkModificationSystemModelChangeSet changes = ModificationBatch.this.changeInstances.get(key);
						LinkModificationSystemModelChangeInstanceSet instanceSet = changes.makeChangeSet(linkModel);
						instanceMap.put(key, instanceSet);

						// Apply Change To Model
						LinkModificationResultSet resultSet = instanceSet.applyChanges();

						// If a Change was made,
						// then add it to the models to be saved.
						if (resultSet.isModelModified()) {
							T model = pair.getModel();
							modifiedModels.add(model);
						} else {
							unmodifiedModels.add(key);
						}

						// Update Set
						totalResultSet.addResultSet(resultSet);
					}

					// Save all models
					LinkModificationSystemEntryImpl.this.updater.update(modifiedModels);

					// Return results set.
					return new ModificationBatchResult(totalResultSet, instanceMap, modifiedModels, unmodifiedModels);
				}

			});

			return this.batchResult;
		}

		public void undoChanges() {

			List<T> modifiedModels = this.batchResult.getModifiedModels();
			List<ModelKey> modifiedModelKeys = ModelKey.readModelKeys(modifiedModels);
			final ReadRequest request = new KeyReadRequest(modifiedModelKeys, false);

			ObjectifyService.ofy().transactNew(new VoidWork() {

				@Override
				public void vrun() {
					Map<ModelKey, LinkModificationSystemModelChangeInstanceSet> map = ModificationBatch.this.batchResult
					        .getInstanceMap();

					ReadResponse<? extends MutableLinkModelAccessorPair<T>> response = LinkModificationSystemEntryImpl.this.accessor
					        .readMutableLinkModels(request);

					List<T> modifiedModels = new ArrayList<T>();

					for (MutableLinkModelAccessorPair<T> pair : response.getModels()) {
						MutableLinkModel linkModel = pair.getMutableLinkModel();
						ModelKey modelKey = linkModel.getModelKey();

						LinkModificationSystemModelChangeInstanceSet instanceSet = map.get(modelKey);
						boolean modified = instanceSet.undoChanges(linkModel);

						if (modified) {
							modifiedModels.add(pair.getModel());
						}
					}

					// Save all models
					LinkModificationSystemEntryImpl.this.updater.update(modifiedModels);

					// TODO: Consider logging models that failed being loaded.
				}

			});
		}

		protected void assertUnavailableModelsAreOptional(Collection<ModelKey> missingModels)
		        throws UnavailableModelException {
			for (ModelKey key : missingModels) {
				LinkModificationSystemModelChangeSet changeSet = this.changeInstances.get(key);

				if (changeSet.isOptional() == false) {
					throw new UnavailableModelException(key);
				}
			}

		}

	}

	/**
	 * Contains results from {@link ModificationBatch#performChanges()}.
	 * 
	 * @author dereekb
	 *
	 */
	protected class ModificationBatchResult {

		private final LinkModificationResultSet resultSet;
		private final Map<ModelKey, LinkModificationSystemModelChangeInstanceSet> instanceMap;
		private final List<T> modifiedModels;
		private final Set<ModelKey> unmodifiedModels;

		public ModificationBatchResult(LinkModificationResultSet resultSet,
		        Map<ModelKey, LinkModificationSystemModelChangeInstanceSet> instanceMap,
		        List<T> modifiedModels,
		        Set<ModelKey> unmodifiedModels) {
			super();
			this.resultSet = resultSet;
			this.instanceMap = instanceMap;
			this.modifiedModels = modifiedModels;
			this.unmodifiedModels = unmodifiedModels;
		}

		public LinkModificationResultSet getResultSet() {
			return this.resultSet;
		}

		public Map<ModelKey, LinkModificationSystemModelChangeInstanceSet> getInstanceMap() {
			return this.instanceMap;
		}

		public List<T> getModifiedModels() {
			return this.modifiedModels;
		}

		public Set<ModelKey> getUnmodifiedModels() {
			return this.unmodifiedModels;
		}

	}

}
