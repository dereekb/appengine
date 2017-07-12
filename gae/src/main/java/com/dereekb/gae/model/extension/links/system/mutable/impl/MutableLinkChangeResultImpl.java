package com.dereekb.gae.model.extension.links.system.mutable.impl;

import java.util.Collections;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResultDynamicInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * {@link MutableLinkChangeResult} implementation for non-dynamic changes.
 * <p>
 * Lazy-computes the modified and redundant changes.
 * 
 * @author dereekb
 *
 */
public class MutableLinkChangeResultImpl implements MutableLinkChangeResult {

	private final MutableLinkChange change;
	
	private final Set<ModelKey> beforeChangeSet;
	private final Set<ModelKey> afterChangeSet;
	
	private final Instance instance;
	
	public MutableLinkChangeResultImpl(MutableLinkChange change,
	        Set<ModelKey> beforeChangeSet,
	        Set<ModelKey> afterChangeSet) {
		super();
		this.change = change;
		this.beforeChangeSet = beforeChangeSet;
		this.afterChangeSet = afterChangeSet;
		
		this.instance = this.makeInstance();
	}

	private Instance makeInstance() {
		Instance instance = null;
		
		switch (this.change.getLinkChangeType()) {
			case ADD:
				instance = new AddInstance();
				break;
			case REMOVE:
				instance = new RemoveInstance();
				break;
			case SET:
				instance = new SetInstance();
				break;
			case CLEAR:
				instance = new ClearInstance();
				break;
			default:
				throw new IllegalArgumentException();
		}
		
		return instance;
	}

	// MARK: Change
	@Override
	public Set<ModelKey> getModified() {
		return this.instance.getModified();
	}

	@Override
	public Set<ModelKey> getRedundant() {
		return this.instance.getRedundant();
	}

	@Override
	public MutableLinkChangeResultDynamicInfo getDynamicChangeInfo() {
		return this.instance.getDynamicChangeInfo();
	}
	
	// MARK: MutableLinkChangeResultDynamicInfo

	// MARK: Internal
	private abstract class Instance implements MutableLinkChangeResult {
		
		private Set<ModelKey> modified;
		private Set<ModelKey> redundant;

		@Override
		public Set<ModelKey> getModified() {
			if (this.modified == null) {
				this.modified = this.computeModified();
			}

			return this.modified;
		}
		
		protected abstract Set<ModelKey> computeModified();

		@Override
		public Set<ModelKey> getRedundant() {
			if (this.redundant == null) {
				this.redundant = this.computeRedundant();
			}
			
			return this.redundant;
		}

		protected abstract Set<ModelKey> computeRedundant();

		@Override
		public MutableLinkChangeResultDynamicInfo getDynamicChangeInfo() {
			throw new UnsupportedOperationException("Not yet implemented.");
		}
		
	}

	private class AddInstance extends Instance {

		@Override
		protected Set<ModelKey> computeModified() {
			// Keys that were not in the original set.
			Set<ModelKey> changeKeys = MutableLinkChangeResultImpl.this.change.getKeys();
			return SetUtility.makeSetDifference(MutableLinkChangeResultImpl.this.beforeChangeSet, changeKeys).getCompliment();
		}

		@Override
		protected Set<ModelKey> computeRedundant() {
			// Keys that were in both sets initially.
			Set<ModelKey> changeKeys = MutableLinkChangeResultImpl.this.change.getKeys();
			return SetUtility.makeSetDifference(MutableLinkChangeResultImpl.this.beforeChangeSet, changeKeys).getIntersection();
		}

	}

	private class RemoveInstance extends Instance {

		@Override
		protected Set<ModelKey> computeModified() {
			// Keys that were in both sets initially.
			Set<ModelKey> changeKeys = MutableLinkChangeResultImpl.this.change.getKeys();
			return SetUtility.makeSetDifference(MutableLinkChangeResultImpl.this.beforeChangeSet, changeKeys).getIntersection();
		}

		@Override
		protected Set<ModelKey> computeRedundant() {
			// Keys that were requested to be removed, but not in the original set.
			Set<ModelKey> changeKeys = MutableLinkChangeResultImpl.this.change.getKeys();
			return SetUtility.makeSetDifference(MutableLinkChangeResultImpl.this.beforeChangeSet, changeKeys).getCompliment();
		}

	}

	private class SetInstance extends Instance {

		@Override
		protected Set<ModelKey> computeModified() {
			return MutableLinkChangeResultImpl.this.beforeChangeSet;
			
		}

		@Override
		protected Set<ModelKey> computeRedundant() {
			Set<ModelKey> changeKeys = MutableLinkChangeResultImpl.this.change.getKeys();
			return SetUtility.makeSetDifference(MutableLinkChangeResultImpl.this.beforeChangeSet, changeKeys).getIntersection();
		}

	}

	private class ClearInstance extends Instance {

		@Override
		protected Set<ModelKey> computeModified() {
			return MutableLinkChangeResultImpl.this.beforeChangeSet;
		}

		@Override
		protected Set<ModelKey> computeRedundant() {
			return Collections.emptySet();
		}

	}
	
	
}
