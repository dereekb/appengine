package com.dereekb.gae.model.extension.links.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.deprecated.components.Link;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.LinkInfoImpl;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.link.DescribedModelLinkImpl;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.link.DescribedModelLinkInfo;
import com.dereekb.gae.model.extension.links.descriptor.UniqueDescribedModel;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

@Deprecated
public abstract class AbstractDescriptiveModelLinkSystemEntry<T extends UniqueDescribedModel> extends AbstractModelLinkSystemEntry<T> {

	protected List<DescribedModelLinkInfo> descriptiveLinkInfo;

	public AbstractDescriptiveModelLinkSystemEntry(String modelType,
	        ReadService<T> readService,
	        DeleteService<T> deleteService,
	        Updater<T> updater) {
		super(modelType, readService, deleteService, updater);
		this.descriptiveLinkInfo = new ArrayList<DescribedModelLinkInfo>();
	}

	public AbstractDescriptiveModelLinkSystemEntry(String modelType,
	        ReadService<T> readService,
	        DeleteService<T> deleteService,
	        Updater<T> updater,
	        List<DescribedModelLinkInfo> info) {
		super(modelType, readService, deleteService, updater);
		this.descriptiveLinkInfo = info;
	}

	public List<DescribedModelLinkInfo> getDescriptiveLinkInfo() {
		return this.descriptiveLinkInfo;
	}

	public void setDescriptiveLinkInfo(List<DescribedModelLinkInfo> descriptiveLinkInfo) {
		this.descriptiveLinkInfo = descriptiveLinkInfo;
	}

	@Override
	public final List<Link> getLinks(T model) {
		List<Link> links = new ArrayList<Link>();
		links.addAll(this.makeDefinedLinksForModel(model));
		links.addAll(this.makeDescriptiveLinks(model));
		return links;
	}

	protected List<Link> makeDescriptiveLinks(final T model) {
		List<Link> links = new ArrayList<Link>();
		ModelKey key = model.getModelKey();

		for (DescribedModelLinkInfo info : this.descriptiveLinkInfo) {
			LinkInfoImpl linkInfo = info.toLinkInfo(key);
			DescribedModelLinkImpl link = new DescribedModelLinkImpl(linkInfo, model);
			links.add(link);
		}

		return links;
	}

	/**
	 * Generates "defined" links for the given model.
	 *
	 * @param model
	 * @return {@link List} of all the links. Each link should have a unique
	 *         name. Should not include the descriptive info link. Should not
	 *         return null.
	 */
	public abstract List<Link> makeDefinedLinksForModel(final T model);

}
