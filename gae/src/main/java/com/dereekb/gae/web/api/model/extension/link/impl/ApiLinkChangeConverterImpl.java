package com.dereekb.gae.web.api.model.extension.link.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;
import com.dereekb.gae.model.extension.links.service.impl.LinkSystemChangeImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.ModelKeyTypeConverterImpl;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChangeConverter;

/**
 * {@link ApiLinkChangeConverter} implementation.
 *
 * @author dereekb
 *
 */
public class ApiLinkChangeConverterImpl
        implements ApiLinkChangeConverter {

	private ModelKeyTypeConverterImpl keyTypeConverter;

	public ApiLinkChangeConverterImpl(ModelKeyTypeConverterImpl keyTypeConverter) {
		this.keyTypeConverter = keyTypeConverter;
	}

	@Override
    public List<LinkSystemChange> convert(String primaryType,
	                                      Collection<ApiLinkChange> input) throws ConversionFailureException {
		List<LinkSystemChange> changes = new ArrayList<LinkSystemChange>();

		SingleDirectionalConverter<String, ModelKey> converter;

		try {
			converter = this.keyTypeConverter.getSingleConverterForModelType(primaryType);
		} catch (IllegalArgumentException e) {
			throw new ConversionFailureException(e.getMessage());
		}

		for (ApiLinkChange inputChange : input) {
			LinkSystemChangeImpl change = new LinkSystemChangeImpl();

			String actionString = inputChange.getAction();
			LinkChangeAction action = LinkChangeAction.withString(actionString);
			change.setAction(action);

			change.setPrimaryType(primaryType);

			String primaryKeyValue = inputChange.getPrimaryKey();
			ModelKey primaryKey = converter.convertSingle(primaryKeyValue);
			change.setPrimaryKey(primaryKey);

			String linkName = inputChange.getLinkName();
			change.setLinkName(linkName);

			Set<String> targetStringKeys = inputChange.getTargetKeys();
			change.setTargetStringKeys(targetStringKeys);
		}

		return changes;
	}

	@Override
	public String toString() {
		return "ApiLinkChangeConverterImpl [keyTypeConverter=" + this.keyTypeConverter + "]";
	}

}
