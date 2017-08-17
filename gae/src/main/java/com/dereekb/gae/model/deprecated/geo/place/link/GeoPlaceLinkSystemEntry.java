package com.dereekb.gae.model.geo.place.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.deprecated.geo.place.GeoPlace;
import com.dereekb.gae.model.extension.links.deprecated.components.Link;
import com.dereekb.gae.model.extension.links.deprecated.components.LinkTarget;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.LinkInfoImpl;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.LinkTargetImpl;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.link.DescribedModelLinkInfo;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.link.LinkImpl;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.link.SingleLink;
import com.dereekb.gae.model.extension.links.deprecated.components.impl.link.SingleLinkDelegate;
import com.dereekb.gae.model.extension.links.deprecated.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.deprecated.impl.AbstractDescriptiveModelLinkSystemEntry;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;

/**
 * {@link LinkSystemEntry} implementation for {@link GeoPlace}.
 *
 * @author dereekb
 *
 */
@Deprecated
public class GeoPlaceLinkSystemEntry extends AbstractDescriptiveModelLinkSystemEntry<GeoPlace> {

	public static final String GEO_PLACE_LINK_TYPE = "GeoPlace";

	private static final ExtendedObjectifyModelKeyUtil<GeoPlace> geoPlaceUtil = ExtendedObjectifyModelKeyUtil
	        .make(GeoPlace.class, ModelKeyType.NUMBER);

	private String parentLinkName = "parent";
	private LinkTarget parentTarget = new LinkTargetImpl(GEO_PLACE_LINK_TYPE, ModelKeyType.NUMBER);

	public GeoPlaceLinkSystemEntry(CrudService<GeoPlace> crudService,
	        Updater<GeoPlace> updater,
	        List<DescribedModelLinkInfo> info) {
		super(GEO_PLACE_LINK_TYPE, crudService, crudService, updater, info);
	}

	public GeoPlaceLinkSystemEntry(ReadService<GeoPlace> readService,
	        DeleteService<GeoPlace> deleteService,
	        Updater<GeoPlace> updater) {
		super(GEO_PLACE_LINK_TYPE, readService, deleteService, updater);
	}

	public GeoPlaceLinkSystemEntry(ReadService<GeoPlace> readService,
	        DeleteService<GeoPlace> deleteService,
	        Updater<GeoPlace> updater,
	        List<DescribedModelLinkInfo> info) {
		super(GEO_PLACE_LINK_TYPE, readService, deleteService, updater, info);
	}

	public String getParentLinkName() {
		return this.parentLinkName;
	}

	public void setParentLinkName(String parentLinkName) {
		this.parentLinkName = parentLinkName;
	}

	public LinkTarget getParentTarget() {
		return this.parentTarget;
	}

	public void setParentTarget(LinkTarget parentTarget) {
		this.parentTarget = parentTarget;
	}

	public static ExtendedObjectifyModelKeyUtil<GeoPlace> getGeoplaceutil() {
		return geoPlaceUtil;
	}

	@Override
	public List<Link> makeDefinedLinksForModel(final GeoPlace model) {
		List<Link> links = new ArrayList<Link>();

		// Parent Link
		LinkInfoImpl parentLinkInfo = new LinkInfoImpl(this.parentLinkName, model.getModelKey(), this.parentTarget);
		LinkImpl parentLink = new LinkImpl(parentLinkInfo, new SingleLink(new SingleLinkDelegate() {

			@Override
			public ModelKey getKey() {
				Key<GeoPlace> key = model.getParent();
				return geoPlaceUtil.toModelKey(key);
			}

			@Override
			public void setKey(ModelKey modelKey) {
				Key<GeoPlace> key = geoPlaceUtil.fromModelKey(modelKey);
				model.setParent(key);
			}
		}

		));

		links.add(parentLink);
		return links;
	}

	@Override
	public String toString() {
		return "GeoPlaceLinkSystemEntry [descriptiveLinkInfo=" + this.descriptiveLinkInfo + ", modelType="
		        + this.modelType + ", indexService=" + this.readService + ", updater=" + this.updater + ", reviewer="
		        + this.reviewer + ", validator=" + this.validator + ", reverseLinkNames=" + this.getReverseLinkNames()
		        + "]";
	}

}
