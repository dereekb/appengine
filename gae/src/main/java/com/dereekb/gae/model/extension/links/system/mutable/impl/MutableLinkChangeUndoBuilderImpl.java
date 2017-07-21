package com.dereekb.gae.model.extension.links.system.mutable.impl;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.Link;
import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeUndoBuilder;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * {@link MutableLinkChangeUndoBuilder} implementation.
 * 
 * @author dereekb
 *
 */
public class MutableLinkChangeUndoBuilderImpl implements MutableLinkChangeUndoBuilder {
	
	public static final MutableLinkChangeUndoBuilder SINGLETON = new MutableLinkChangeUndoBuilderImpl();

	protected MutableLinkChangeUndoBuilderImpl() {
		super();
	}

	// MARK: MutableLinkChangeUndoBuilder
	@Override
	public MutableLinkChange makeUndo(Link currentLink,
	                                  MutableLinkChange previousChange,
	                                  MutableLinkChangeResult result) {
		
		 AbstractUndoInstance instance = null;
		 
		 LinkInfo linkInfo = currentLink.getLinkInfo();
		 
		 switch (linkInfo.getLinkSize()) {
			case MANY:
				instance = new ManySizeUndoInstance(currentLink, previousChange, result);
				break;
			case ONE:
				instance = new OneSizeUndoInstance(currentLink, previousChange, result);
				break;
			default:
				throw new RuntimeException();
		 }
		 
		return instance.makeUndoChange();
	}
	
	private abstract class AbstractUndoInstance {
		
		protected final Link currentLink;
		protected final MutableLinkChange previousChange;
		protected final MutableLinkChangeResult result;
		
		public AbstractUndoInstance(Link currentLink,
		        MutableLinkChange previousChange,
		        MutableLinkChangeResult result) {
			super();
			this.currentLink = currentLink;
			this.previousChange = previousChange;
			this.result = result;
		}
		
		public abstract MutableLinkChange makeUndoChange();
		
		// MARK: Internal
		protected boolean setHasNotChangedSinceModification() {
			Set<ModelKey> currentKeys = this.currentLink.getLinkedModelKeys();
			Set<ModelKey> changeKeys = this.result.getResultSet();
			
			return SetUtility.isEquivalent(currentKeys, changeKeys);
		}
		
	}
	
	protected class OneSizeUndoInstance extends AbstractUndoInstance {

		public OneSizeUndoInstance(Link currentLink, MutableLinkChange previousChange, MutableLinkChangeResult result) {
			super(currentLink, previousChange, result);
		}

		@Override
		public MutableLinkChange makeUndoChange() {
			MutableLinkChangeType type = this.previousChange.getLinkChangeType();
			
			switch (type) {
				case CLEAR:
					// If the link has already been modified elsewhere, don't change it back.
					if (this.currentLink.getLinkedModelKeys().isEmpty() == false) {
						return MutableLinkChangeImpl.none();
					} else {
						return MutableLinkChangeImpl.setOrClear(this.result.getOriginalSet());
					}
				case SET:
					if (this.setHasNotChangedSinceModification()) {
						return MutableLinkChangeImpl.setOrClear(this.result.getOriginalSet());
					} else {
						return MutableLinkChangeImpl.none();
					}
				case ADD:
				case REMOVE:
				default:
					throw new UnsupportedOperationException();
			}
		}
		
	}
	
	protected class ManySizeUndoInstance extends AbstractUndoInstance {

		public ManySizeUndoInstance(Link currentLink,
		        MutableLinkChange previousChange,
		        MutableLinkChangeResult result) {
			super(currentLink, previousChange, result);
		}

		@Override
		public MutableLinkChange makeUndoChange() {
			MutableLinkChangeType type = this.previousChange.getLinkChangeType();

			switch (type) {
				case ADD:
					// Remove only those that were added.
					return MutableLinkChangeImpl.remove(this.result.getModified());
				case CLEAR:
				case REMOVE:
					// Add those that were removed.
					return MutableLinkChangeImpl.add(this.result.getModified());
				case SET:
					// Add everything back from the original set.
					return MutableLinkChangeImpl.add(this.result.getOriginalSet());
				default:
					throw new UnsupportedOperationException();
			}
		}
		
	}

	@Override
	public String toString() {
		return "MutableLinkChangeUndoBuilderImpl []";
	}

}
