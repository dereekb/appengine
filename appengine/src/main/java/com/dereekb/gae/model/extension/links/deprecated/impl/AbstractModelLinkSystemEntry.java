package com.dereekb.gae.model.extension.links.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.links.deprecated.components.Link;
import com.dereekb.gae.model.extension.links.deprecated.components.LinkInfo;
import com.dereekb.gae.model.extension.links.deprecated.components.accessor.LinkAccessor;
import com.dereekb.gae.model.extension.links.deprecated.components.exception.LinkSaveConditionException;
import com.dereekb.gae.model.extension.links.deprecated.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.deprecated.components.model.change.LinkModelSetChange;
import com.dereekb.gae.model.extension.links.deprecated.components.model.impl.LinkModelImplDelegate;
import com.dereekb.gae.model.extension.links.deprecated.components.model.impl.LinkModelSetImpl;
import com.dereekb.gae.model.extension.links.deprecated.components.model.impl.LinkModelSetImplDelegate;
import com.dereekb.gae.model.extension.links.deprecated.components.system.LinkSystem;
import com.dereekb.gae.model.extension.links.deprecated.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.deprecated.components.system.impl.bidirectional.BidirectionalLinkSystemEntry;
import com.dereekb.gae.model.extension.links.deprecated.deleter.LinkDeleterChangeType;
import com.dereekb.gae.model.extension.links.deprecated.deleter.LinkDeleterServiceEntry;
import com.dereekb.gae.model.extension.links.system.mutable.exception.NoReverseLinksException;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * Abstract implementation of different {@link LinkSystem} related elements.
 * <p>
 * The reverse link names map is case-insensitive.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public abstract class AbstractModelLinkSystemEntry<T extends UniqueModel>
        implements LinkAccessor<T>, LinkDeleterServiceEntry, LinkSystemEntry, LinkModelImplDelegate<T>,
        LinkModelSetImplDelegate<T>, BidirectionalLinkSystemEntry {

	/**
	 * Delegate interface for {@link AbstractModelLinkSystemEntry} for
	 * validating models.
	 *
	 * @author dereekb
	 *
	 * @param <T>
	 */
	public static interface Validator<T> {

		/**
		 * @see LinkModelSetImplDelegate#validateModels(List,
		 *      LinkModelSetChange)
		 */
		public void validateModels(List<T> models,
		                           LinkModelSetChange changes)
		        throws LinkSaveConditionException;

	}

	/**
	 * Delegate interface for {@link AbstractModelLinkSystemEntry} for reviewing
	 * model changes.
	 *
	 * @author dereekb
	 *
	 * @param <T>
	 */
	public static interface Reviewer<T> {

		/**
		 * @see LinkModelSetImplDelegate#reviewModels(List, LinkModelSetChange)
		 */
		public void reviewModels(List<T> models,
		                         LinkModelSetChange changes);
	}

	protected String modelType;

	protected ReadService<T> readService;
	protected Updater<T> updater;

	protected Reviewer<T> reviewer;
	protected Validator<T> validator;

	protected DeleteService<T> deleteService;

	@Deprecated
	protected Map<String, LinkDeleterChangeType> deleteChangesMap;

	/**
	 * Names for the reverse element.
	 * <p>
	 * Keyed by this element's link names to the opposite link name.
	 * <p>
	 * For example, if this has a link named "parent", the value "child" will be
	 * keyed to "parent".
	 */
	private Map<String, String> reverseLinkNames;

	public AbstractModelLinkSystemEntry(String modelType,
	        ReadService<T> readService,
	        DeleteService<T> deleteService,
	        Updater<T> updater) {
		this(modelType, readService, deleteService, updater, null);
	}

	public AbstractModelLinkSystemEntry(String modelType,
	        ReadService<T> readService,
	        DeleteService<T> deleteService,
	        Updater<T> updater,
	        Map<String, LinkDeleterChangeType> deleteChangesMap) {
		this.setModelType(modelType);
		this.setReadService(readService);
		this.setUpdater(updater);
		this.setDeleteService(deleteService);
		this.setDeleteChangesMap(deleteChangesMap);
		this.setReverseLinkNames(null);
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public ReadService<T> getService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> service) {
		this.readService = service;
	}

	public Updater<T> getUpdater() {
		return this.updater;
	}

	public void setUpdater(Updater<T> updater) {
		this.updater = updater;
	}

	public final Map<String, String> getReverseLinkNames() {
		return this.reverseLinkNames;
	}

	public final void setReverseLinkNames(Map<String, String> reverseLinkNames) {
		this.reverseLinkNames = new CaseInsensitiveMap<String>(reverseLinkNames);
	}

	@Override
	public ReadResponse<T> readModels(Collection<ModelKey> keys) {
		ReadRequest request = new KeyReadRequest(keys);
		request.getOptions().setAtomic(false);
		return this.readService.read(request);
	}

	@Override
	public void saveModels(List<T> models) {
		this.updater.update(models);
	}

	@Override
	public String getLinkModelType() {
		return this.modelType;
	}

	public DeleteService<T> getDeleteService() {
		return this.deleteService;
	}

	public void setDeleteService(DeleteService<T> deleteService) {
		this.deleteService = deleteService;
	}

	@Deprecated
	@Override
	public Map<String, LinkDeleterChangeType> getDeleteChangesMap() {
		return this.deleteChangesMap;
	}

	@Deprecated
	public void setDeleteChangesMap(Map<String, LinkDeleterChangeType> deleteChangesMap) {
		if (deleteChangesMap == null) {
			deleteChangesMap = new HashMap<String, LinkDeleterChangeType>();
		}

		this.deleteChangesMap = deleteChangesMap;
	}

	// LinkDeleterServiceEntry
	@Override
	public void deleteModels(Collection<ModelKey> keys) {
		DeleteRequestImpl request = new DeleteRequestImpl(keys);
		this.deleteService.delete(request);
	}

	// LinkModelImplDelegate
	@Override
	public Map<String, Link> buildLinks(T model) {
		Map<String, Link> map = new HashMap<String, Link>();
		List<Link> links = this.getLinks(model);

		for (Link link : links) {
			String key = link.getLinkName();
			map.put(key, link);
		}

		return map;
	}

	// AbstractLinkDelegateImpl
	@Override
	public abstract List<Link> getLinks(T model);

	@Override
	public LinkModelSet makeSet() {
		return new LinkModelSetImpl<T>(this, this);
	}

	@Override
	public void validateModels(List<T> models,
	                           LinkModelSetChange changes)
	        throws LinkSaveConditionException {
		if (this.validator != null) {
			this.validator.validateModels(models, changes);
		}
	}

	@Override
	public void reviewModels(List<T> models,
	                         LinkModelSetChange changes) {
		if (this.reviewer != null) {
			this.reviewer.reviewModels(models, changes);
		}
	}

	// BidirectionalLinkSystemEntry
	@Override
	public boolean isBidirectionallyLinked(LinkInfo info) {
		String linkName = info.getLinkName();
		return this.reverseLinkNames.containsKey(linkName);
	}

	@Override
	public String getReverseLinkName(LinkInfo info) throws NoReverseLinksException {
		String linkName = info.getLinkName();
		String reverseLinkName = this.reverseLinkNames.get(linkName);

		if (reverseLinkName == null) {
			throw new NoReverseLinksException(reverseLinkName);
		}

		return reverseLinkName;
	}

}
