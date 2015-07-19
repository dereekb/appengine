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
import com.dereekb.gae.server.datastore.models.keys.conversion.LongModelKeyConverter;


public class DifferentLinkModelDelegate extends AbstractTestLinkSystemDelegate<DifferentLinkModel> {

	public DifferentLinkModelDelegate() {
		super();

		// Register the reverse links
		this.reverseLinkNames.put(DifferentLinkModel.SOME_MODEL_LINKS_NAME, SomeLinkModel.DIFFERENT_MODEL_LINKS_NAME);
	}

	@Override
	public String getLinkModelType() {
		return "DifferentLinkModel";
	}

	@Override
	public DifferentLinkModel makeModel(ModelKey key) {
		return new DifferentLinkModel(key);
	}

	@Override
	public Map<String, Link> buildLinks(final DifferentLinkModel model) {

		Map<String, Link> links = new HashMap<String, Link>();

		// The name of the field in this type.
		String someLinkModelLinkName = DifferentLinkModel.SOME_MODEL_LINKS_NAME;

		// The target type used.
		String someLinkTargetType = SomeLinkModel.MODEL_TYPE;

		// The "SomeLinks" elements correspond to the "DifferentLinks" in the
		// remote SomeLinkModel defined up above in the constructor.

		ModelKeyType someLinkKeyType = ModelKeyType.NUMBER;
		Set<Long> someLinkKeys = model.getSomeModelLinks();
		LongModelKeyConverter someLinkTypeConverter = new LongModelKeyConverter();

		LinkInfoImpl someLinkInfo = new LinkInfoImpl(someLinkModelLinkName, model.getModelKey(), someLinkTargetType,
		        someLinkKeyType);

		LinkCollectionImpl<Long> someLink = new LinkCollectionImpl<Long>(someLinkInfo, someLinkKeys,
		        someLinkTypeConverter);

		links.put(someLinkModelLinkName, someLink);

		return links;
	}

	@Override
	public void validateModels(List<DifferentLinkModel> models,
	                           LinkModelSetChange changes) throws LinkSaveConditionException {}

	@Override
	public void reviewModels(List<DifferentLinkModel> models,
	                         LinkModelSetChange changes) {}

}
