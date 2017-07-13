package com.dereekb.gae.test.model.extension.link.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.exception.LinkSaveConditionException;
import com.dereekb.gae.model.extension.links.components.impl.LinkInfoImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.LinkCollectionImpl;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelSetChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringModelKeyConverterImpl;


@Deprecated
public class SomeLinkModelDelegate extends AbstractTestLinkSystemDelegate<SomeLinkModel> {

	public SomeLinkModelDelegate() {
		super();
		// Register the reverse links
		this.reverseLinkNames.put(SomeLinkModel.DIFFERENT_MODEL_LINKS_NAME, DifferentLinkModel.SOME_MODEL_LINKS_NAME);
	}

	@Override
	public String getLinkModelType() {
		return "SomeLinkModel";
	}

	@Override
	public SomeLinkModel makeModel(ModelKey key) {
		return new SomeLinkModel(key);
	}

	@Override
	public Map<String, Link> buildLinks(final SomeLinkModel model) {
		Map<String, Link> links = new HashMap<String, Link>();

		String differentLinkModelLinkName = SomeLinkModel.DIFFERENT_MODEL_LINKS_NAME;
		String differentLinkTargetType = DifferentLinkModel.MODEL_TYPE;
		ModelKeyType differentLinkKeyType = ModelKeyType.NAME;
		Set<String> differentLinkKeys = model.getDifferentModelLinks();
		StringModelKeyConverter differentLinkTypeConverter = StringModelKeyConverterImpl.CONVERTER;

		LinkInfoImpl differentLinkInfo = new LinkInfoImpl(differentLinkModelLinkName, model.getModelKey(),
		        differentLinkTargetType, differentLinkKeyType);

		LinkCollectionImpl<String> differentLink = new LinkCollectionImpl<String>(differentLinkInfo, differentLinkKeys,
		        differentLinkTypeConverter);

		links.put(differentLinkModelLinkName, differentLink);

		return links;
	}

	@Override
	public void validateModels(List<SomeLinkModel> models,
	                           LinkModelSetChange changes) throws LinkSaveConditionException {
	}

	@Override
	public void reviewModels(List<SomeLinkModel> models,
	                         LinkModelSetChange changes) {
	}

}
