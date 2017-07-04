package com.dereekb.gae.model.extension.links.system.mutable.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.links.system.components.LimitedLinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LimitedLinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkModel;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkSize;
import com.dereekb.gae.model.extension.links.system.components.Relation;
import com.dereekb.gae.model.extension.links.system.components.exceptions.NoRelationException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.exception.UnavailableLinkModelAccessorException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLink;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModelAccessorPair;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModelBuilder;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemEntry;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;
import com.dereekb.gae.model.extension.links.system.readonly.LinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.readonly.LinkSystem;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * {@link LinkSystem} implementation.
 * 
 * @author dereekb
 *
 */
public class MutableLinkSystemImpl
        implements LinkSystem {

	private CaseInsensitiveMap<MutableLinkSystemEntry> entriesMap;
	private CaseInsensitiveMap<LinkModelInfo> linkModelInfoMap;

	// MARK: LinkSystem
	@Override
	public Set<String> getAvailableSetTypes() {
		return this.entriesMap.keySet();
	}

	@Override
	public LinkModelInfo loadLinkModelInfo(String type) throws UnavailableLinkModelException {
		LinkModelInfo modelInfo = this.linkModelInfoMap.get(type);

		if (modelInfo == null) {
			modelInfo = this.makeLinkModelInfo(type);
			this.linkModelInfoMap.put(type, modelInfo);
		}

		return modelInfo;
	}

	private LinkModelInfo makeLinkModelInfo(String type) throws UnavailableLinkModelException {
		MutableLinkSystemEntry entry = this.getEntryForType(type);
		return new LinkModelInfoImpl(entry);
	}

	@Override
	public LinkModelAccessor loadAccessor(String type) throws UnavailableLinkModelAccessorException {
		return null;
	}

	// MARK: Mutable Accessors
	/**
	 * Used for generating a new {@link MutableLinkModelAccessor}.
	 * 
	 * @return {@link MutableLinkModelAccessor}. Never {@code null}.
	 */
	public <T extends UniqueModel> MutableLinkModelAccessor<T> makeAccessor(String linkType,
	                                                                        ReadService<T> readService)
	        throws UnavailableLinkModelException {
		return new MutableLinkModelAccessorImpl<T>(linkType, readService);
	}

	/**
	 * {@link MutableLinkModelAccessor} implementation.
	 * 
	 * @author dereekb
	 *
	 * @param <T>
	 *            model type
	 */
	private class MutableLinkModelAccessorImpl<T extends UniqueModel>
	        implements LinkModelAccessor, MutableLinkModelAccessor<T>, MutableLinkModelBuilder<T> {

		private final String linkType;
		private final MutableLinkSystemEntry linkSystemEntry;
		private ReadService<T> readService;

		public MutableLinkModelAccessorImpl(String linkType, ReadService<T> readService)
		        throws UnavailableLinkModelException, IllegalArgumentException {
			this.linkType = linkType;
			this.linkSystemEntry = MutableLinkSystemImpl.this.getEntryForType(linkType);
			this.linkSystemEntry.getLinkModelType();
			this.setReadService(readService);
		}

		public void setReadService(ReadService<T> readService) throws IllegalArgumentException {
			if (readService == null) {
				throw new IllegalArgumentException("readService cannot be null.");
			}

			this.readService = readService;
		}

		// MARK: LinkModelAccessor
		@Override
		public String getLinkModelType() {
			return this.linkType;
		}

		@Override
		public ReadResponse<? extends LinkModel> readLinkModels(ReadRequest request) throws AtomicOperationException {
			ReadResponse<T> response = this.readService.read(request);
			return new LinkModelReadResponse(response);
		}

		// MARK: LinkModelReadResponse
		private class LinkModelReadResponse extends AbstractReadResponse<LinkModel> {

			private Collection<LinkModel> linkModels;

			public LinkModelReadResponse(ReadResponse<T> response) {
				super(response);
			}

			// MARK: ReadResponse
			@Override
			public Collection<LinkModel> getModels() {
				if (this.linkModels == null) {
					this.linkModels = this.makeModels();
				}

				return this.linkModels;
			}

			// MARK: Internal
			private Collection<LinkModel> makeModels() {
				Collection<T> models = this.response.getModels();
				List<LinkModel> linkModels = new ArrayList<LinkModel>();

				for (T model : models) {
					LinkModel linkModel = MutableLinkModelAccessorImpl.this.makeMutableLinkModel(model);
					linkModels.add(linkModel);
				}

				return linkModels;
			}

		}

		protected abstract class AbstractReadResponse<R>
		        implements ReadResponse<R> {

			protected final ReadResponse<T> response;

			public AbstractReadResponse(ReadResponse<T> response) {
				this.response = response;
			}

			// MARK: ReadResponse
			@Override
			public Collection<ModelKey> getFiltered() {
				return this.response.getFiltered();
			}

			@Override
			public Collection<ModelKey> getUnavailable() {
				return this.response.getUnavailable();
			}

			@Override
			public Collection<ModelKey> getFailed() {
				return this.response.getFailed();
			}

		}

		// MARK: MutableLinkModelBuilder
		@Override
		public MutableLinkModel makeMutableLinkModel(T model) {
			return new MutableLinkModelImpl(model);
		}

		/**
		 * {@link MutableLinkModel} implementation.
		 * 
		 * @author dereekb
		 *
		 */
		private class MutableLinkModelImpl
		        implements MutableLinkModel {

			private final T model;

			public MutableLinkModelImpl(T model) {
				this.model = model;
			}

			// MARK: MutableLinkModel
			@Override
			public LinkModelInfo getLinkModelInfo() {
				return MutableLinkSystemImpl.this.loadLinkModelInfo(MutableLinkModelAccessorImpl.this.linkType);
			}

			@Override
			public ModelKey getModelKey() {
				return this.model.getModelKey();
			}

			@Override
			public ModelKey keyValue() {
				return this.model.getModelKey();
			}

			@Override
			public String getLinkModelType() {
				return MutableLinkModelAccessorImpl.this.linkType;
			}

			@Override
			public MutableLink getLink(String linkName) throws UnavailableLinkException {
				LinkInfo info = this.getLinkModelInfo().getLinkInfo(linkName);
				MutableLinkAccessor accessor = MutableLinkModelAccessorImpl.this.linkSystemEntry
				        .makeLinkAccessor(linkName);
				return new MutableLinkImpl(info, accessor);
			}

			/**
			 * {@link MutableLink} implementation.
			 * 
			 * @author dereekb
			 *
			 */
			private class MutableLinkImpl
			        implements MutableLink {

				private LinkInfo info;
				private MutableLinkAccessor accessor;

				public MutableLinkImpl(LinkInfo info, MutableLinkAccessor accessor) {
					super();
					this.info = info;
					this.accessor = accessor;
				}

				// MARK: MutableLink
				@Override
				public LinkInfo getLinkInfo() {
					return this.info;
				}

				@Override
				public LinkModel getLinkModel() {
					return MutableLinkModelImpl.this;
				}

				@Override
				public Set<ModelKey> getLinkedModelKeys() {
					return this.accessor.getLinkedModelKeys();
				}

				@Override
				public MutableLinkChangeResult modifyKeys(MutableLinkChange change) throws MutableLinkChangeException {
					return this.accessor.modifyKeys(change);
				}

			}

		}

		// MARK: MutableLinkModelAccessor
		@Override
		public ReadResponse<? extends MutableLinkModelAccessorPair<T>> readMutableLinkModels(ReadRequest request)
		        throws AtomicOperationException {

			ReadResponse<T> response = this.readService.read(request);
			return new PairReadResponse(response);
		}

		private class PairReadResponse extends AbstractReadResponse<MutableLinkModelAccessorPairImpl> {

			private Collection<MutableLinkModelAccessorPairImpl> pairs;

			public PairReadResponse(ReadResponse<T> response) {
				super(response);
			}

			// MARK: ReadResponse
			@Override
			public Collection<MutableLinkModelAccessorPairImpl> getModels() {
				if (this.pairs == null) {
					this.pairs = this.makePairs();
				}

				return this.pairs;
			}

			private Collection<MutableLinkModelAccessorPairImpl> makePairs() {
				List<MutableLinkModelAccessorPairImpl> pairs = new ArrayList<MutableLinkModelAccessorPairImpl>();

				for (T model : this.response.getModels()) {
					MutableLinkModelAccessorPairImpl pair = new MutableLinkModelAccessorPairImpl(model);
					pairs.add(pair);
				}

				return pairs;
			}

		}

		/**
		 * {@link MutableLinkModelAccessorPair} implementation.
		 * 
		 * @author dereekb
		 *
		 */
		private class MutableLinkModelAccessorPairImpl
		        implements MutableLinkModelAccessorPair<T> {

			private final T model;
			private MutableLinkModel mutableLinkModel;

			public MutableLinkModelAccessorPairImpl(T model) {
				this.model = model;
			}

			@Override
			public ModelKey keyValue() {
				return this.model.getModelKey();
			}

			@Override
			public T getModel() {
				return this.model;
			}

			@Override
			public MutableLinkModel getMutableLinkModel() {
				if (this.mutableLinkModel == null) {
					this.mutableLinkModel = MutableLinkModelAccessorImpl.this.makeMutableLinkModel(this.model);
				}

				return this.mutableLinkModel;
			}

		}

	}

	// MARK: Internal
	public MutableLinkSystemEntry getEntryForType(String type) throws UnavailableLinkModelException {
		MutableLinkSystemEntry entry = this.entriesMap.get(type);

		if (entry == null) {
			throw UnavailableLinkModelException.makeForType(type);
		}

		return entry;
	}

	/**
	 * {@link LinkModelInfo} implementation.
	 * 
	 * @author dereekb
	 *
	 */
	private class LinkModelInfoImpl
	        implements LinkModelInfo {

		private final MutableLinkSystemEntry linkSystemEntry;
		private final LimitedLinkModelInfo limitedLinkModel;

		private CaseInsensitiveMap<LinkInfoImpl> linksMap = new CaseInsensitiveMap<LinkInfoImpl>();

		public LinkModelInfoImpl(MutableLinkSystemEntry linkSystemEntry) {
			this.linkSystemEntry = linkSystemEntry;
			this.limitedLinkModel = linkSystemEntry.loadLinkModelInfo();
		}

		// MARK: LinkModelInfo
		@Override
		public Set<String> getLinkNames() {
			return this.limitedLinkModel.getLinkNames();
		}

		@Override
		public String getLinkModelType() {
			return this.limitedLinkModel.getLinkModelType();
		}

		@Override
		public LinkInfo getLinkInfo(String linkName) throws UnavailableLinkException {
			LinkInfoImpl linkInfo = this.linksMap.get(linkName);

			if (linkInfo == null) {
				LimitedLinkInfo limitedLinkInfo = this.limitedLinkModel.getLinkInfo(linkName);
				linkInfo = new LinkInfoImpl(limitedLinkInfo);
				this.linksMap.put(linkName, linkInfo);
			}

			return linkInfo;
		}

		private class LinkInfoImpl
		        implements LinkInfo {

			private final LimitedLinkInfo limitedLinkInfo;
			private RelationSource relationSource;

			public LinkInfoImpl(LimitedLinkInfo limitedLinkInfo) {
				this.limitedLinkInfo = limitedLinkInfo;
			}

			@Override
			public String getLinkName() {
				return this.limitedLinkInfo.getLinkName();
			}

			@Override
			public String getLinkType() {
				return this.limitedLinkInfo.getLinkType();
			}

			@Override
			public LinkSize getLinkSize() {
				return this.limitedLinkInfo.getLinkSize();
			}

			@Override
			public String getLinkModelType() {
				return LinkModelInfoImpl.this.getLinkModelType();
			}

			@Override
			public LinkModelInfo getLinkModelInfo() {
				return LinkModelInfoImpl.this;
			}

			@Override
			public Relation getRelationInfo() throws NoRelationException {
				if (this.relationSource == null) {

				}

				return this.relationSource.getRelationInfo();
			}

			protected RelationSource buildRelationSource() {

			}

		}

		private class RelationSourceImpl
		        implements RelationSource {

			@Override
			public Relation getRelationInfo() throws NoRelationException {
				// TODO Auto-generated method stub
				return null;
			}

		}

	}

	private static interface RelationSource {

		public Relation getRelationInfo() throws NoRelationException;

	}

	private static class NonexistantRelationSource
	        implements RelationSource {

		@Override
		public Relation getRelationInfo() throws NoRelationException {
			throw new NoRelationException();
		}

	}
}
