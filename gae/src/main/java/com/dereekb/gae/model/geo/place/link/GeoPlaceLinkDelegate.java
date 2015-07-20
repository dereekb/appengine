package com.dereekb.gae.model.geo.place.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.impl.LinkInfoImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.DescribedModelLinkInfo;
import com.dereekb.gae.model.extension.links.components.impl.link.LinkImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.SingleLink;
import com.dereekb.gae.model.extension.links.components.impl.link.SingleLinkDelegate;
import com.dereekb.gae.model.extension.links.components.system.impl.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.impl.AbstractDescriptiveModelLinkSystemEntry;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.googlecode.objectify.Key;

/**
 * {@link LinkSystemEntry} implementation for {@link GeoPlace}.
 *
 * @author dereekb
 *
 */
public class GeoPlaceLinkDelegate extends AbstractDescriptiveModelLinkSystemEntry<GeoPlace> {

	private static final String GEO_PLACE_LINK_TYPE = "GeoPlace";
	private static final ExtendedObjectifyModelKeyUtil<GeoPlace> util = ExtendedObjectifyModelKeyUtil.make(
	        GeoPlace.class, ModelKeyType.NUMBER);

	public GeoPlaceLinkDelegate(ReadService<GeoPlace> service, ConfiguredSetter<GeoPlace> setter) {
		super(GEO_PLACE_LINK_TYPE, service, setter);
	}

	public GeoPlaceLinkDelegate(ReadService<GeoPlace> service,
	        ConfiguredSetter<GeoPlace> setter,
	        List<DescribedModelLinkInfo> info) {
	    super(GEO_PLACE_LINK_TYPE, service, setter, info);
    }

	@Override
	public List<Link> makeDefinedLinksForModel(final GeoPlace model) {
		List<Link> links = new ArrayList<Link>();

		// Parent Link
		LinkInfoImpl parentLinkInfo = new LinkInfoImpl("parent", model.getModelKey(), "GeoPlace", ModelKeyType.NUMBER);
		LinkImpl parentLink = new LinkImpl(parentLinkInfo, new SingleLink(new SingleLinkDelegate() {

			@Override
            public ModelKey getKey() {
				Key<GeoPlace> key = model.getParent();
				return util.toModelKey(key);
            }

			@Override
			public void setKey(ModelKey modelKey) {
				Key<GeoPlace> key = util.fromModelKey(modelKey);
				model.setParent(key);
            }}

		));

		links.add(parentLink);
		return links;
	}

}
