package com.dereekb.gae.model.extension.links.system.mutable.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.system.components.LimitedLinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LimitedLinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.LinkType;
import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.modification.utility.LinkModificationSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.BidirectionalLinkNameMap;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessorFactory;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderAccessorDelegate;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * Abstract {@link MutableLinkSystemBuilderEntry} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractMutableLinkSystemBuilderEntry<T>
        implements LinkModificationSystemBuilderEntry<T>, MutableLinkSystemBuilderEntry, MutableLinkSystemBuilderAccessorDelegate<T> {

	private ReadService<T> readService;
	private LimitedLinkModelInfoImpl linkModelInfo = new LimitedLinkModelInfoImpl();
	private BidirectionalLinkNameMap map = EmptyBidirectionalLinkNameMapImpl.SINGLETON;

	private Updater<T> updater;
	private TaskRequestSender<T> reviewTaskSender;

	public AbstractMutableLinkSystemBuilderEntry(ReadService<T> readService) {
		this.setReadService(readService);
	}

	public AbstractMutableLinkSystemBuilderEntry(ReadService<T> readService,
	        Updater<T> updater,
	        TaskRequestSender<T> reviewTaskSender) {
		super();
		this.setReadService(readService);
		this.setUpdater(updater);
		this.setReviewTaskSender(reviewTaskSender);
	}

	// MARK: LinkModificationSystemBuilderEntry
	@Override
	public Updater<T> getUpdater() {
		return this.updater;
	}

	public void setUpdater(Updater<T> updater) {
		if (updater == null) {
			throw new IllegalArgumentException("updater cannot be null.");
		}
	
		this.updater = updater;
	}

	@Override
	public TaskRequestSender<T> getReviewTaskSender() {
		return this.reviewTaskSender;
	}
	
	public void setReviewTaskSender(TaskRequestSender<T> reviewTaskSender) {
		if (reviewTaskSender == null) {
			throw new IllegalArgumentException("reviewTaskSender cannot be null.");
		}
	
		this.reviewTaskSender = reviewTaskSender;
	}

	// MARK: Accessors
	@Override
	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		if (readService == null) {
			throw new IllegalArgumentException("readService cannot be null.");
		}

		this.readService = readService;
	}

	public void setBidirectionalLinkNames(Map<String, String> linkNames) {
		if (linkNames == null) {
			this.map = EmptyBidirectionalLinkNameMapImpl.SINGLETON;
		} else {
			this.map = new BidirectionalLinkNameMapImpl(linkNames);
		}
	}
	
	public void setBidirectionaLinkMap(BidirectionalLinkNameMap map) {
		if (map == null) {
			throw new IllegalArgumentException("map cannot be null.");
		}
		
		this.map = map;
	}

	// MARK: MutableLinkSystemBuilderEntry
	@Override
	public LimitedLinkModelInfo loadLinkModelInfo() {
		return this.linkModelInfo;
	}

	public abstract ModelKeyType getModelKeyType();
	
	@Override
	public BidirectionalLinkNameMap getBidirectionalMap() {
		return this.map;
	}

	@Override
	public MutableLinkAccessor makeLinkAccessor(String linkName,
	                                            T model)
	        throws UnavailableLinkException {
		return this.linkModelInfo.makeLinkAccessor(linkName, model);
	}

	// MARK: Internal
	protected abstract List<MutableLinkData<T>> makeLinkData();

	private class LimitedLinkModelInfoImpl
	        implements MutableLinkAccessorFactory<T>, LimitedLinkModelInfo {

		private CaseInsensitiveMap<LimitedLinkInfoImpl> linksMap = null;

		// MARK: LimitedLinkModelInfo
		@Override
		public ModelKeyType getModelKeyType() {
			return AbstractMutableLinkSystemBuilderEntry.this.getModelKeyType();
		}
		
		@Override
		public String getLinkModelType() {
			return AbstractMutableLinkSystemBuilderEntry.this.getLinkModelType();
		}
		
		@Override
		public Set<String> getLinkNames() {
			return this.getLinksMap().keySet();
		}

		@Override
		public LimitedLinkInfoImpl getLinkInfo(String linkName) throws UnavailableLinkException {
			LimitedLinkInfoImpl linkInfo = this.getLinksMap().get(linkName);

			if (linkInfo == null) {
				throw UnavailableLinkException.withLink(linkName);
			}

			return linkInfo;
		}

		// MARK: MutableLinkAccessorFactory
		@Override
		public MutableLinkAccessor makeLinkAccessor(String linkName,
		                                            T model)
		        throws UnavailableLinkException {
			return this.getLinkInfo(linkName).makeLinkAccessor(model);
		}

		// MARK: Links
		protected CaseInsensitiveMap<LimitedLinkInfoImpl> getLinksMap() {
			if (this.linksMap == null) {
				this.linksMap = this.makeLinksMap();
			}

			return this.linksMap;
		}

		protected CaseInsensitiveMap<LimitedLinkInfoImpl> makeLinksMap() {
			CaseInsensitiveMap<LimitedLinkInfoImpl> linksMap = new CaseInsensitiveMap<LimitedLinkInfoImpl>();

			List<MutableLinkData<T>> linkDatas = AbstractMutableLinkSystemBuilderEntry.this.makeLinkData();

			for (MutableLinkData<T> linkData : linkDatas) {
				LimitedLinkInfoImpl linkInfo = new LimitedLinkInfoImpl(linkData);
				linksMap.put(linkData.getLinkName(), linkInfo);
			}

			return linksMap;
		}

		private class LimitedLinkInfoImpl
		        implements LimitedLinkInfo {

			private MutableLinkData<T> data;

			public LimitedLinkInfoImpl(MutableLinkData<T> data) {
				this.data = data;
			}

			// MARK: MutableLinkData
			@Override
			public String getLinkName() {
				return this.data.getLinkName();
			}

			@Override
			public LinkType getLinkType() {
				return this.data.getLinkType();
			}

			@Override
			public String getRelationLinkType() throws DynamicLinkInfoException {
				return this.data.getRelationLinkType();
			}

			@Override
			public LinkSize getLinkSize() {
				return this.data.getLinkSize();
			}

			public MutableLinkAccessor makeLinkAccessor(T model) {
				return this.data.makeLinkAccessor(model);
			}

			// MARK: LimitedLinkInfo
			@Override
			public LimitedLinkModelInfo getLinkModelInfo() {
				return LimitedLinkModelInfoImpl.this;
			}

		}

	}

}
