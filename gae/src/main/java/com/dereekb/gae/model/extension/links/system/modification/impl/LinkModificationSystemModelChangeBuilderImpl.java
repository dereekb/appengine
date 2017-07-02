package com.dereekb.gae.model.extension.links.system.modification.impl;

import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChange;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeBuilder;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemModelChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLink;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;

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
				return CLEAR_SINGLETON;
			case REMOVE:
				return REMOVE_SINGLETON;
			case SET:
				return SET_SINGLETON;
			default:
				throw new RuntimeException();
		}
	}

	// MARK: Internal
	protected class AddRelationChange extends AbstractLinkModificationSystemModelChange {

		public AddRelationChange(LinkModification linkModification) {
			super(linkModification);
		}

		@Override
		public LinkModificationSystemModelChangeInstance makeChange(MutableLinkModel linkModel) {
			return new Instance(linkModel);
		}

		protected class Instance extends AbstractInstance {

			public Instance(MutableLinkModel linkModel) {
				super(linkModel);
			}

			// MARK: LinkModificationSystemModelChangeInstance
			@Override
			protected LinkModificationResult makeLinkModificationResult(MutableLinkChangeResult result) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected void undoChange(MutableLinkChangeResult result) {
				// TODO Auto-generated method stub

			}

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

		// MARK: Instance
		protected abstract class AbstractInstance
		        implements LinkModificationSystemModelChangeInstance {

			private final MutableLinkModel linkModel;

			private MutableLinkChangeResult result;

			public AbstractInstance(MutableLinkModel linkModel) {
				this.linkModel = linkModel;
			}

			protected MutableLink loadMutableLink() {
				MutableLink link = this.linkModel.getLink(AbstractLinkModificationSystemModelChange.this.getLinkName());
				return link;
			}

			// MARK: LinkModificationSystemModelChangeInstance
			@Override
			public LinkModificationResult applyChange() {
				if (this.result == null) {
					MutableLink link = this.loadMutableLink();
					MutableLinkChange change = AbstractLinkModificationSystemModelChange.this.linkModification
					        .getChange();
					this.result = link.modifyKeys(change);
					return this.makeLinkModificationResult(this.result);
				} else {
					// TODO: Replace with specific exception.
					throw new RuntimeException();
				}
			}

			@Override
			public void undoChange() {
				if (this.result != null) {

					// If no result, there is nothing to undo.
					this.undoChange(this.result);
				}
			}

			protected abstract LinkModificationResult makeLinkModificationResult(MutableLinkChangeResult result);

			protected abstract void undoChange(MutableLinkChangeResult result);

		}

	}

}
