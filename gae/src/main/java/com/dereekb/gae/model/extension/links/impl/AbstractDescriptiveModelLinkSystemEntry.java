package com.dereekb.gae.model.extension.links.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.UniqueDescriptivelyLinkedModel;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.impl.LinkInfoImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.DescriptiveModelLinkImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.DescriptiveModelLinkInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;

public abstract class AbstractDescriptiveModelLinkSystemEntry<T extends UniqueDescriptivelyLinkedModel> extends AbstractModelLinkSystemEntry<T> {

	protected List<DescriptiveModelLinkInfo> descriptiveLinkInfo;

	public AbstractDescriptiveModelLinkSystemEntry(String modelType, ReadService<T> service, ConfiguredSetter<T> setter) {
		super(modelType, service, setter);
		this.descriptiveLinkInfo = new ArrayList<DescriptiveModelLinkInfo>();
	}

	public AbstractDescriptiveModelLinkSystemEntry(String modelType,
	        ReadService<T> service,
	        ConfiguredSetter<T> setter,
	        List<DescriptiveModelLinkInfo> info) {
		super(modelType, service, setter);
		this.descriptiveLinkInfo = info;
	}

	public List<DescriptiveModelLinkInfo> getDescriptiveLinkInfo() {
		return this.descriptiveLinkInfo;
	}

	public void setDescriptiveLinkInfo(List<DescriptiveModelLinkInfo> descriptiveLinkInfo) {
		this.descriptiveLinkInfo = descriptiveLinkInfo;
	}

	@Override
	public final List<Link> makeLinksForModel(T model) {
		List<Link> links = new ArrayList<Link>();
		links.addAll(this.makeDefinedLinksForModel(model));
		links.addAll(this.makeDescriptiveLinks(model));
		return links;
	}

	protected List<Link> makeDescriptiveLinks(final T model) {
		List<Link> links = new ArrayList<Link>();
		ModelKey key = model.getModelKey();

		for (DescriptiveModelLinkInfo info : this.descriptiveLinkInfo) {
			LinkInfoImpl linkInfo = info.toLinkInfo(key);
			DescriptiveModelLinkImpl link = new DescriptiveModelLinkImpl(linkInfo, model);
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
	public abstract List<Link> makeDefinedLinksForModel(T model);

}
