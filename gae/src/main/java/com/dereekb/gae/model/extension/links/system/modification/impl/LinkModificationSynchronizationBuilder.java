package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.Relation;
import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.impl.LinkModificationImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Static utility used for building {@link LinkModification} for synchronization.
 * @author dereekb
 *
 */
public class LinkModificationSynchronizationBuilder {

	public static List<LinkModification> makeSynchronizationLinkModifications(LinkModificationResult result) {
		Instance instance = new Instance(result);
		return instance.compute();
	}
	
	private static class Instance {
		
		private final LinkModificationResult result;
		
		public Instance(LinkModificationResult result) {
			super();
			this.result = result;
		}

		public List<LinkModification> compute() {
			LinkModification linkModification = this.result.getLinkModification();
			LinkInfo linkInfo = linkModification.getLink();
			Relation relation;

			try {
				relation = linkInfo.getRelationInfo();
			} catch (NoRelationException e) {
				return Collections.emptyList();
			}

			// TODO: Eventually update to use the dynamic change information, if ever added.
			
			RelationSubInstance subInstance = this.makeSubInstanceForRelation(relation);
			return subInstance.compute();
		}
		
		private RelationSubInstance makeSubInstanceForRelation(Relation relation) {
			LinkInfo relationLinkInfo = relation.getRelationLink();
			LinkSize relationLinkSize = relationLinkInfo.getLinkSize();
			
			switch (relationLinkSize) {
				case MANY:
					return new LinkSizeManySubInstance(relation);
				case ONE:
					return new LinkSizeOneSubInstance(relation);
				default:
					throw new UnsupportedOperationException();
			}
			
		}

		protected abstract class RelationSubInstance {
			
			protected final Relation relation;

			public RelationSubInstance(Relation relation) {
				this.relation = relation;
			}

			public abstract List<LinkModification> compute();

			// MARK: Internal
			protected List<LinkModification> makeSynchronizationLinkModificationForKeys(MutableLinkChange syncChange,
			                                                                                 LinkInfo relationLinkInfo,
			                                                                                 Set<ModelKey> modelKeys) {
				return LinkModificationImpl.makeSynchronizationLinkModificationForKeys(syncChange, relationLinkInfo, modelKeys);
			}
		}

		protected class LinkSizeManySubInstance extends RelationSubInstance {

			public LinkSizeManySubInstance(Relation relation) {
				super(relation);
			}

			@Override
			public List<LinkModification> compute() {
				LinkModification linkModification = Instance.this.result.getLinkModification();
				
				ModelKey originalKey = linkModification.getKey();
				MutableLinkChangeType originalChangeType = linkModification.getChange().getLinkChangeType();

				MutableLinkChangeResult linkChangeResult = Instance.this.result.getLinkChangeResult();
	
				Set<ModelKey> modified = linkChangeResult.getModified();
				LinkInfo relationLinkInfo = this.relation.getRelationLink();

				switch (originalChangeType) {
					case CLEAR:
						// Treat as a removal for sync changes.
						originalChangeType = MutableLinkChangeType.REMOVE;
					case ADD:
					case REMOVE:
						MutableLinkChangeType syncChangeType = originalChangeType;
						MutableLinkChange syncChange = MutableLinkChangeImpl.make(syncChangeType, originalKey);
						
						return this.makeSynchronizationLinkModificationForKeys(syncChange, relationLinkInfo, modified);
					case SET:
						// Add and Remove items to properly synchronize.
						Set<ModelKey> changeKeys = linkModification.getChange().getKeys();

						Set<ModelKey> allAdded = new HashSet<ModelKey>(changeKeys);
						Set<ModelKey> alreadyLinked = linkChangeResult.getRedundant();
						
						// Don't update links to already linked.
						allAdded.removeAll(alreadyLinked);
						
						Set<ModelKey> allRemoved = new HashSet<ModelKey>(linkChangeResult.getOriginalSet());
						allRemoved.removeAll(changeKeys);

						MutableLinkChange addChange = MutableLinkChangeImpl.make(MutableLinkChangeType.ADD, originalKey);
						List<LinkModification> addSync = this.makeSynchronizationLinkModificationForKeys(addChange,
						        relationLinkInfo, allAdded);

						MutableLinkChange removeChange = MutableLinkChangeImpl.make(MutableLinkChangeType.REMOVE,
						        originalKey);
						List<LinkModification> removeSync = this.makeSynchronizationLinkModificationForKeys(removeChange,
						        relationLinkInfo, allRemoved);

						List<LinkModification> syncChanges = new ArrayList<LinkModification>(addSync);
						syncChanges.addAll(removeSync);

						return syncChanges;
					default:
						throw new UnsupportedOperationException();
				}
			}
		}

		/**
		 * {@link RelationSubInstance} Implementation for creating synchronization for links with a size of {@link LinkSize#ONE}.
		 * <p>
		 * Links of this size only use SET and CLEAR. 
		 * 
		 * @author dereekb
		 *
		 */
		protected class LinkSizeOneSubInstance extends RelationSubInstance {

			public LinkSizeOneSubInstance(Relation relation) {
				super(relation);
			}

			@Override
			public List<LinkModification> compute() {
				LinkModification linkModification = Instance.this.result.getLinkModification();
				
				ModelKey originalKey = linkModification.getKey();
				MutableLinkChangeType originalChangeType = linkModification.getChange().getLinkChangeType();

				MutableLinkChangeResult linkChangeResult = Instance.this.result.getLinkChangeResult();
	
				Set<ModelKey> modified = linkChangeResult.getModified();
				LinkInfo relationLinkInfo = this.relation.getRelationLink();

				switch (originalChangeType) {
					case ADD:
						// Set Added Links
						Set<ModelKey> added = linkChangeResult.getModified();
						MutableLinkChange addedSetChange = MutableLinkChangeImpl.make(MutableLinkChangeType.SET, originalKey);
						return this.makeSynchronizationLinkModificationForKeys(addedSetChange, relationLinkInfo, added);
					case REMOVE:
						// Clear Removed Links
						Set<ModelKey> removed = linkChangeResult.getModified();
						MutableLinkChange removedClearChange = MutableLinkChangeImpl.make(MutableLinkChangeType.CLEAR, originalKey);
						return this.makeSynchronizationLinkModificationForKeys(removedClearChange, relationLinkInfo, removed);
					case CLEAR:
						// Clear the linked reversals.
						MutableLinkChange syncChange = MutableLinkChangeImpl.make(originalChangeType, originalKey);
						return this.makeSynchronizationLinkModificationForKeys(syncChange, relationLinkInfo, modified);
					case SET:
						// Set new, and clear old.
						Set<ModelKey> changeKeys = linkModification.getChange().getKeys();

						Set<ModelKey> allAdded = new HashSet<ModelKey>(changeKeys);
						Set<ModelKey> alreadyLinked = linkChangeResult.getRedundant();
						
						// Don't update links to already linked.
						allAdded.removeAll(alreadyLinked);

						Set<ModelKey> allRemoved = new HashSet<ModelKey>(linkChangeResult.getOriginalSet());
						allRemoved.removeAll(changeKeys);

						// Set New Links
						MutableLinkChange setChange = MutableLinkChangeImpl.make(MutableLinkChangeType.SET, originalKey);
						List<LinkModification> setSync = this.makeSynchronizationLinkModificationForKeys(setChange,
						        relationLinkInfo, allAdded);

						// Clear Old Links
						MutableLinkChange clearChange = MutableLinkChangeImpl.make(MutableLinkChangeType.CLEAR,
						        originalKey);
						List<LinkModification> clearSync = this.makeSynchronizationLinkModificationForKeys(clearChange,
						        relationLinkInfo, modified);

						List<LinkModification> syncChanges = new ArrayList<LinkModification>(setSync);
						syncChanges.addAll(clearSync);

						return syncChanges;
					default:
						throw new UnsupportedOperationException();
				}
			}
		}
		
	}

}
