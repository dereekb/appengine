package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationChangeType;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPair;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPairState;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntry;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntryInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntryInstanceConfig;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeBuilder;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstanceSet;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeSet;
import com.dereekb.gae.model.extension.links.system.modification.utility.LinkModificationPairUtility;
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
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

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
	
	public LinkModificationSystemEntryImpl(MutableLinkModelAccessor<T> accessor,
	        Updater<T> updater,
	        TaskRequestSender<T> reviewTaskSender) {
		super();
		this.setAccessor(accessor);
		this.setUpdater(updater);
		this.setReviewTaskSender(reviewTaskSender);
	}

	public MutableLinkModelAccessor<T> getAccessor() {
		return this.accessor;
	}
	
	public void setAccessor(MutableLinkModelAccessor<T> accessor) {
		if (accessor == null) {
			throw new IllegalArgumentException("accessor cannot be null.");
		}
	
		this.accessor = accessor;
	}
	
	public Updater<T> getUpdater() {
		return this.updater;
	}
	
	public void setUpdater(Updater<T> updater) {
		if (updater == null) {
			throw new IllegalArgumentException("updater cannot be null.");
		}
	
		this.updater = updater;
	}

	public TaskRequestSender<T> getReviewTaskSender() {
		return this.reviewTaskSender;
	}
	
	public void setReviewTaskSender(TaskRequestSender<T> reviewTaskSender) {
		if (reviewTaskSender == null) {
			throw new IllegalArgumentException("reviewTaskSender cannot be null.");
		}
	
		this.reviewTaskSender = reviewTaskSender;
	}

	// MARK: LinkModificationSystemEntry
	@Override
	public FilterResults<ModelKey> filterModelsExist(Set<ModelKey> keys) {
		
		ReadResponse<ModelKey> response = this.accessor.readExistingModels(keys);
		
		FilterResults<ModelKey> filterResults = new FilterResults<ModelKey>();
		filterResults.addAll(FilterResult.PASS, response.getModels());
		
		Collection<ModelKey> failed = response.getFailed();
		filterResults.addAll(FilterResult.FAIL, failed);
		
		return filterResults;
	}

	@Override
	public LinkModificationSystemEntryInstance makeInstance(LinkModificationSystemEntryInstanceConfig config) {
		return new LinkModificationSystemEntryInstanceImpl(config);
	}

	// MARK: Internal
	protected class LinkModificationSystemEntryInstanceImpl
	        implements LinkModificationSystemEntryInstance {
		
		private LinkModificationSystemEntryInstanceConfig config;
		
		public LinkModificationSystemEntryInstanceImpl(LinkModificationSystemEntryInstanceConfig config) {
			super();
			this.config = config;
		}

		public LinkModificationSystemEntryInstanceConfig getConfig() {
			return this.config;
		}

		public void setConfig(LinkModificationSystemEntryInstanceConfig config) {
			if (config == null) {
				throw new IllegalArgumentException("config cannot be null.");
			}
		
			this.config = config;
		}

		// MARK: LinkModificationSystemEntryInstance
		@Override
		public void applyChanges() {
			DoChangesModificationBatchSet doChanges = new DoChangesModificationBatchSet();
			doChanges.doAction();
		}

		@Override
		public void commitChanges() {
			CommitChangesModificationBatchSet commitChanges = new CommitChangesModificationBatchSet();
			commitChanges.doAction();
		}

		@Override
		public void undoChanges() {
			UndoChangesModificationBatchSet undoChanges = new UndoChangesModificationBatchSet();
			undoChanges.doAction();
		}

		@Override
		public void runChanges(LinkModificationChangeType changeType) {
			switch (changeType) {
				case COMMIT:
					this.commitChanges();
					break;
				case DO:
					this.applyChanges();
					break;
				case UNDO:
					this.undoChanges();
					break;
				default:
					throw new UnsupportedOperationException();
			}
		}

		// MARK: Batch Sets
		protected abstract class AbstractModicationRunner {

			protected final List<LinkModificationPair> filterApplicablePairs() {
				List<LinkModificationPair> pairs = LinkModificationSystemEntryInstanceImpl.this.config.getModificationPairs();
				List<LinkModificationPair> filteredPairs = new ArrayList<LinkModificationPair>();
				
				for (LinkModificationPair pair : pairs) {
					if (this.isApplicableForModification(pair)) {
						filteredPairs.add(pair);
					}
				}
				
				return filteredPairs;
			}

			protected abstract boolean isApplicableForModification(LinkModificationPair pair);
			
		}
		
		protected abstract class AbstractModificationBatchSet extends AbstractModicationRunner {
			
			private final List<ModificationBatch> batches;
			
			public AbstractModificationBatchSet() {
				this.batches = this.makeBatches();
			}

			private final List<ModificationBatch> makeBatches() {
				List<LinkModificationPair> applicablePairs = this.filterApplicablePairs();

				HashMapWithList<ModelKey, LinkModificationPair> keyedMap = LinkModificationPairUtility.buildKeyedChangesMap(applicablePairs);
				
				List<List<ModelKey>> keyBatches = LinkModificationSystemEntryImpl.this.PARTITIONER
				        .makePartitions(keyedMap.keySet());

				List<ModificationBatch> batches = new ArrayList<ModificationBatch>(keyBatches.size());

				for (List<ModelKey> keyBatch : keyBatches) {
					ModificationBatch batch = new ModificationBatch();

					// Get all modifications for a specific model key.
					for (ModelKey key : keyBatch) {
						List<LinkModificationPair> modifications = keyedMap.get(key);
						batch.addModificationSet(key, modifications);
					}

					batches.add(batch);
				}
				
				return batches;
			}
			
			public final void doAction() {
				this.doActionOnModel(this.batches);
			}
			
			protected abstract void doActionOnModel(List<ModificationBatch> batches);

			protected void assertUnavailableModelsAreOptional(Collection<ModelKey> missingModels, Map<ModelKey, LinkModificationSystemModelChangeSet> changeInstances)
			        throws AtomicOperationException {
				Set<ModelKey> nonOptionalKeys = new HashSet<ModelKey>();
				
				for (ModelKey key : missingModels) {
					LinkModificationSystemModelChangeSet changeSet = changeInstances.get(key);

					if (changeSet.isOptional() == false) {
						 nonOptionalKeys.add(key);
					}
				}
				
				if (nonOptionalKeys.isEmpty() == false) {
					throw new AtomicOperationException(nonOptionalKeys, AtomicOperationExceptionReason.UNAVAILABLE);
				}
			}
			
		}
		
		protected abstract class AbstractChangesDoChangesModificationBatchSet extends AbstractModificationBatchSet {
			
			@Override
			protected final void doActionOnModel(List<ModificationBatch> batches) {
				for (ModificationBatch batch : batches) {
					this.doAction(batch);
				}
			}
			
			private void doAction(ModificationBatch batch) {
				final Map<ModelKey, LinkModificationSystemModelChangeSet> changeInstances = batch.getChangeInstances();

				List<ModelKey> modifiedModelKeys = ModelKey.readModelKeys(changeInstances.keySet());
				final ReadRequest request = new KeyReadRequest(modifiedModelKeys, false);

				ObjectifyService.ofy().transactNew(new VoidWork() {

					@Override
					public void vrun() {
						
						ReadResponse<? extends MutableLinkModelAccessorPair<T>> response = LinkModificationSystemEntryImpl.this.accessor
						        .readMutableLinkModels(request);

						Collection<ModelKey> failed = response.getFailed();

						// Assert unavailable models are optional, otherwise thrown an exception.
						if (failed.isEmpty() == false) {
							AbstractChangesDoChangesModificationBatchSet.this.assertUnavailableModelsAreOptional(failed, changeInstances);
						}

						List<T> modifiedModels = new ArrayList<T>();

						for (MutableLinkModelAccessorPair<T> pair : response.getModels()) {
							MutableLinkModel linkModel = pair.getMutableLinkModel();
							ModelKey modelKey = linkModel.getModelKey();

							LinkModificationSystemModelChangeSet changeSet = changeInstances.get(modelKey);
							LinkModificationSystemModelChangeInstanceSet instanceSet = changeSet.makeInstanceWithModel(linkModel);
							
							boolean modified = AbstractChangesDoChangesModificationBatchSet.this.doActionOnModel(instanceSet);

							if (modified) {
								modifiedModels.add(pair.getModel());
							}
						}

						// Save all models
						LinkModificationSystemEntryImpl.this.updater.update(modifiedModels);
					}

				});
			}

			protected abstract boolean doActionOnModel(LinkModificationSystemModelChangeInstanceSet instanceSet);
			
		}

		protected class DoChangesModificationBatchSet extends AbstractChangesDoChangesModificationBatchSet {

			@Override
			protected boolean isApplicableForModification(LinkModificationPair pair) {
				boolean isApplicable = false;
				
				switch (pair.getState()) {
					case INIT:
					case UNDONE:
						isApplicable = true;
						break;
					case SUCCESS:
					default:
						isApplicable = false;
						break;
				}
				
				return isApplicable;
			}

			@Override
			protected boolean doActionOnModel(LinkModificationSystemModelChangeInstanceSet instanceSet) {
				return instanceSet.applyChanges();
			}
			
		}

		protected class UndoChangesModificationBatchSet extends AbstractChangesDoChangesModificationBatchSet {

			@Override
			protected boolean isApplicableForModification(LinkModificationPair pair) {
				boolean isApplicable = false;
				
				switch (pair.getState()) {
					case INIT:
					case UNDONE:
						isApplicable = false;
						break;
					case SUCCESS:
					default:
						isApplicable = true;
						break;
				}
				
				return isApplicable;
			}

			@Override
			protected boolean doActionOnModel(LinkModificationSystemModelChangeInstanceSet instanceSet) {
				return instanceSet.undoChanges();
			}
			
		}

		protected class CommitChangesModificationBatchSet extends AbstractModicationRunner {

			@Override
			protected boolean isApplicableForModification(LinkModificationPair pair) {
				return pair.getState() == LinkModificationPairState.SUCCESS;
			}

			public void doAction() {
				List<LinkModificationPair> pairs = this.filterApplicablePairs();
				Set<ModelKey> keys = ModelKey.makeModelKeySet(pairs);

				ReadRequest request = new KeyReadRequest(keys, false);
				ReadResponse<? extends MutableLinkModelAccessorPair<T>> readResponse = LinkModificationSystemEntryImpl.this.accessor.readMutableLinkModels(request);
				
				List<T> models = new ArrayList<T>();
				
				for (MutableLinkModelAccessorPair<T> readPair : readResponse.getModels()) {
					T model = readPair.getModel();
					models.add(model);
				}
				
				LinkModificationSystemEntryImpl.this.reviewTaskSender.sendTasks(models);
			}
			
		}

	}

	private class ModificationBatch {

		private Map<ModelKey, LinkModificationSystemModelChangeSet> changeInstances = new HashMap<ModelKey, LinkModificationSystemModelChangeSet>();

		public ModificationBatch() {
			super();
		}

		public Map<ModelKey, LinkModificationSystemModelChangeSet> getChangeInstances() {
			return this.changeInstances;
		}
		
		// MARK: Internal
		public void addModificationSet(ModelKey key,
		                               List<LinkModificationPair> modifications) {
			LinkModificationSystemModelChangeSet changeSet = LinkModificationSystemEntryImpl.this.changeBuilder
			        .makeChangeSet(modifications);
			this.changeInstances.put(key, changeSet);
		}

	}

}
