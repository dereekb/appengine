package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPair;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeBuilder;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeSet;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.impl.LinkModificationResultImpl;
import com.dereekb.gae.model.extension.links.system.modification.exception.LinkModelMismatchException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLink;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;

/**
 * {@link LinkModificationSystemModelChangeBuilder} implementation that uses the
 * linkModificationPair link's data to perform changes on arbitrary models.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemModelChangeBuilderImpl extends AbstractDirectionalConverter<LinkModificationPair, LinkModificationSystemModelChange>
        implements LinkModificationSystemModelChangeBuilder {

	public static final LinkModificationSystemModelChangeBuilderImpl SINGLETON = new LinkModificationSystemModelChangeBuilderImpl();

	// MARK: LinkModificationSystemModelChangeBuilder
	@Override
	public LinkModificationSystemModelChangeSet makeChangeSet(List<LinkModificationPair> modifications) {
		List<LinkModificationSystemModelChange> changes = this.convert(modifications);
		return new LinkModificationSystemModelChangeSetImpl(changes);
	}

	@Override
	public LinkModificationSystemModelChange convertSingle(LinkModificationPair linkModificationPair)
	        throws ConversionFailureException {
		LinkModification linkModification = linkModificationPair.getLinkModification();
		MutableLinkChange linkChange = linkModification.getChange();
		MutableLinkChangeType linkChangeType = linkChange.getLinkChangeType();

		switch (linkChangeType) {
			case ADD:
				return new AddRelationChange(linkModificationPair);
			case CLEAR:
				return new ClearRelationChange(linkModificationPair);
			case REMOVE:
				return new RemoveRelationChange(linkModificationPair);
			case SET:
				return new SetRelationChange(linkModificationPair);
			default:
				throw new ConversionFailureException();
		}
	}

	// MARK: Internal
	protected class AddRelationChange extends AbstractLinkModificationSystemModelChange {

		public AddRelationChange(LinkModificationPair linkModificationPair) {
			super(linkModificationPair);
		}

	}

	protected class ClearRelationChange extends AbstractLinkModificationSystemModelChange {

		public ClearRelationChange(LinkModificationPair linkModificationPair) {
			super(linkModificationPair);
		}

	}

	protected class RemoveRelationChange extends AbstractLinkModificationSystemModelChange {

		public RemoveRelationChange(LinkModificationPair linkModificationPair) {
			super(linkModificationPair);
		}

	}

	protected class SetRelationChange extends AbstractLinkModificationSystemModelChange {

		public SetRelationChange(LinkModificationPair linkModificationPair) {
			super(linkModificationPair);
		}

	}

	protected abstract class AbstractLinkModificationSystemModelChange
	        implements LinkModificationSystemModelChange {

		protected final LinkModificationPair linkModificationPair;

		public AbstractLinkModificationSystemModelChange(LinkModificationPair linkModificationPair) {
			super();
			this.linkModificationPair = linkModificationPair;
		}

		protected String getLinkName() {
			LinkModification linkModification = this.getLinkModification();
			return linkModification.getLink().getLinkName();
		}

		// MARK: LinkModificationSystemModelChange
		@Override
		public boolean isOptional() {
			LinkModification linkModification = this.getLinkModification();
			return linkModification.isOptional();
		}

		@Override
		public LinkModificationPair getPair() {
			return this.linkModificationPair;
		}

		@Override
		public LinkModificationSystemModelChangeInstance makeChangeInstance(MutableLinkModel linkModel) throws LinkModelMismatchException {
			return new Instance(linkModel);
		}
		
		// MARK: Internal
		public LinkModification getLinkModification() {
			return this.linkModificationPair.getLinkModification();
		}
		
		protected void setLinkModificationResult(LinkModificationResult linkModificationResult) {
			this.linkModificationPair.setLinkModificationResult(linkModificationResult);
		}

		protected void setLinkModificationUndoResult(LinkModificationResult linkModificationResult) {
			this.linkModificationPair.setUndoResult(linkModificationResult);
		}
		
		protected LinkModificationResult getLinkModificationResult() {
			return this.linkModificationPair.getLinkModificationResult();
		}
		
		protected MutableLinkChangeResult getMutableLinkChangeResult() {
			LinkModificationResult result = this.getLinkModificationResult();
			MutableLinkChangeResult changeResult = null;
			
			if (result != null) {
				changeResult = result.getLinkChangeResult();
			}
			
			return changeResult;
		}
		
		// MARK: Instance
		/**
		 * Idempotently created instance.
		 * 
		 * @author dereekb
		 */
		protected class Instance
		        implements LinkModificationSystemModelChangeInstance {

			private final MutableLinkModel linkModel;

			private LinkModificationResult result;
			private LinkModificationResult undoResult;

			public Instance(MutableLinkModel linkModel) {
				this.linkModel = linkModel;
			}

			protected MutableLink loadMutableLink(MutableLinkModel linkModel) {
				String linkName = AbstractLinkModificationSystemModelChange.this.getLinkName();
				return linkModel.getLink(linkName);
			}
			
			protected MutableLinkChangeResult getMutableLinkChangeResult() {
				return AbstractLinkModificationSystemModelChange.this.getMutableLinkChangeResult();
			}

			// MARK: LinkModificationSystemModelChangeInstance
			@Override
			public LinkModificationResult applyChange() {
				MutableLink link = this.loadMutableLink(this.linkModel);
				
				// Check the instance, not the pair for the previous result.
				if (this.result == null) {
					LinkModification modification = AbstractLinkModificationSystemModelChange.this.getLinkModification();
					MutableLinkChange change = modification.getChange();
					
					// Modify Keys
					MutableLinkChangeResult changeResult = link.modifyKeys(change);
					
					// Update the pair.
					LinkModificationResult linkModificationResult = this.makeLinkModificationResult(link, changeResult);
					
					this.result = linkModificationResult;
					AbstractLinkModificationSystemModelChange.this.setLinkModificationResult(linkModificationResult);
				}
				
				return this.result;
			}

			@Override
			public boolean undoChange(MutableLinkModel linkModel) {
				
				// Check the pair, not the instance for the previous result.
				MutableLinkChangeResult changeResult = this.getMutableLinkChangeResult();
				MutableLinkChangeResult undoChangeResult = null;
				
				if (changeResult == null) {
					return false;	// Nothing changed/to change.
				} else if (this.undoResult == null) {
					
					MutableLink link = this.loadMutableLink(linkModel);
					undoChangeResult = this.undoChange(link, changeResult);
					
					LinkModificationResult linkModificationUndoResult = this.makeLinkModificationResult(link, undoChangeResult);
					this.undoResult = linkModificationUndoResult;
					
					AbstractLinkModificationSystemModelChange.this.setLinkModificationUndoResult(linkModificationUndoResult);		
				} else {
					undoChangeResult = this.undoResult.getLinkChangeResult();
				}
				
				return undoChangeResult.getModified().isEmpty() == false;
			}

			// MARK: Internal
			protected LinkModificationResult makeLinkModificationResult(MutableLink link,
			                                                            MutableLinkChangeResult result) {
				LinkModification linkModification = AbstractLinkModificationSystemModelChange.this.getLinkModification();
				return new LinkModificationResultImpl(true, linkModification, result);
			}

			protected MutableLinkChangeResult undoChange(MutableLink link,
			                                             MutableLinkChangeResult result) {
				LinkModification linkModification = AbstractLinkModificationSystemModelChange.this.getLinkModification();
				MutableLinkChange change = linkModification.getChange();
				MutableLinkChange undoChange = MutableLinkChangeImpl.makeUndo(link, change, result);
				return link.modifyKeys(undoChange);
			}

		}


	}

}
