package com.dereekb.gae.web.api.model.extension.link;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.links.service.LinkChange;
import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.model.extension.links.service.impl.LinkChangeImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.ModelKeyTypeConverter;
import com.dereekb.gae.web.api.model.extension.link.LinkExtensionController.ApiLinkChange;

public class ApiLinkChangeConverter {

	private ModelKeyTypeConverter keyTypeConverter;

	public ApiLinkChangeConverter(ModelKeyTypeConverter keyTypeConverter) {
		this.keyTypeConverter = keyTypeConverter;
	}

	public List<LinkChange> convert(String primaryType,
	                                Collection<ApiLinkChange> input) throws ConversionFailureException {
		List<LinkChange> changes = new ArrayList<LinkChange>();

		SingleDirectionalConverter<String, ModelKey> converter;

		try {
			converter = this.keyTypeConverter.getSingleConverterForModelType(primaryType);
		} catch (IllegalArgumentException e) {
			throw new ConversionFailureException(e.getMessage());
		}

		for (ApiLinkChange inputChange : input) {
			LinkChangeImpl change = new LinkChangeImpl();

			String actionString = inputChange.getAction();
			LinkChangeAction action = LinkChangeAction.withString(actionString);
			change.setAction(action);

			change.setPrimaryType(primaryType);

			String primaryKeyValue = inputChange.getPrimaryKey();
			ModelKey primaryKey = converter.convertSingle(primaryKeyValue);
			change.setPrimaryKey(primaryKey);

			String linkName = inputChange.getLinkName();
			change.setLinkName(linkName);

			List<String> targetKeyValues = inputChange.getTargetKeys();

			// TODO: Replace with a SAFE, and efficient converter.
			List<ModelKey> targetKeys = ModelKey.convert(targetKeyValues);
			change.setTargetKeys(targetKeys);
		}

		return changes;
	}

}
