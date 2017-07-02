package com.dereekb.gae.model.extension.links.system.modification.impl;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeBuilder;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.components.impl.LinkModificationResultImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLink;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;

/**
 * {@link LinkModificationSystemModelChangeBuilder} implementation that uses the
 * linkModification link's data to perform changes on arbitrary models.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemModelChangeBuilderImpl extends AbstractDirectionalConverter<LinkModification, LinkModificationSystemModelChange>
        implements LinkModificationSystemModelChangeBuilder {

	public static final LinkModificationSystemModelChangeBuilderImpl SINGLETON = new LinkModificationSystemModelChangeBuilderImpl();

	// MARK: LinkModificationSystemModelChangeBuilder
	@Override
	public LinkModificationSystemModelChange convertSingle(LinkModification linkModification)
	        throws ConversionFailureException {
		MutableLinkChange linkChange = linkModification.getChange();
		MutableLinkChangeType linkChangeType = linkChange.getLinkChangeType();

		switch (linkChangeType) {
			case ADD:
				return new AddRelationChange(linkModification);
			case CLEAR:
				return new ClearRelationChange(linkModification);
			case REMOVE:
				return new RemoveRelationChange(linkModification);
			case SET:
				return new SetRelationChange(linkModification);
			default:
				throw new ConversionFailureException();
		}
	}

	// MARK: Internal
	protected class AddRelationChange extends AbstractLinkModificationSystemModelChange {

		public AddRelationChange(LinkModification linkModification) {
			super(linkModification);
		}

	}

	protected class ClearRelationChange extends AbstractLinkModificationSystemModelChange {

		public ClearRelationChange(LinkModification linkModification) {
			super(linkModification);
		}

	}

	protected class RemoveRelationChange extends AbstractLinkModificationSystemModelChange {

		public RemoveRelationChange(LinkModification linkModification) {
			super(linkModification);
		}

	}

	protected class SetRelationChange extends AbstractLinkModificationSystemModelChange {

		public SetRelationChange(LinkModification linkModification) {
			super(linkModification);
		}

	}

	protected abstract class AbstractLinkModificationSystemModelChange
	        implements LinkModificationSystemModelChange {

		protected final LinkModification linkModification;

		public AbstractLinkModificationSystemModelChange(LinkModification linkModification) {
			super();
			this.linkModification = linkModification;
		}

		protected String getLinkName() {
			return this.linkModification.getLink().getLinkName();
		}

		// MARK: LinkModificationSystemModelChange
		@Override
		public LinkModificationSystemModelChangeInstance makeChange(MutableLinkModel linkModel) {
			return new Instance(linkModel);
		}

		// MARK: Instance
		protected class Instance
		        implements LinkModificationSystemModelChangeInstance {

			private final MutableLinkModel linkModel;

			private MutableLinkChangeResult result;

			public Instance(MutableLinkModel linkModel) {
				this.linkModel = linkModel;
			}

			protected MutableLink loadMutableLink() {
				MutableLink link = this.linkModel.getLink(AbstractLinkModificationSystemModelChange.this.getLinkName());
				return link;
			}

			// MARK: LinkModificationSystemModelChangeInstance
			@Override
			public LinkModificationResult applyChange() {
				MutableLink link = this.loadMutableLink();

				if (this.result == null) {
					MutableLinkChange change = AbstractLinkModificationSystemModelChange.this.linkModification
					        .getChange();
					this.result = link.modifyKeys(change);
				}

				return this.makeLinkModificationResult(link, this.result);
			}

			@Override
			public void undoChange() {
				// If no result, there is nothing to undo.
				if (this.result != null) {
					MutableLink link = this.loadMutableLink();
					this.undoChange(link, this.result);
					this.result = null;
				}
			}

			// MARK: LinkModificationSystemModelChangeInstance
			protected LinkModificationResult makeLinkModificationResult(MutableLink link,
			                                                            MutableLinkChangeResult result) {
				return new LinkModificationResultImpl(true,
				        AbstractLinkModificationSystemModelChange.this.linkModification, result);
			}

			protected void undoChange(MutableLink link,
			                          MutableLinkChangeResult result) {
				MutableLinkChange undoChange = MutableLinkChangeImpl
				        .makeUndo(AbstractLinkModificationSystemModelChange.this.linkModification.getChange(), result);
				MutableLink mutableLink = this.loadMutableLink();
				mutableLink.modifyKeys(undoChange);
			}

		}

	}

}
