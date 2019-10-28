package com.dereekb.gae.server.notification.model.token.dto;

import java.util.List;

import com.dereekb.gae.model.extension.links.descriptor.impl.dto.DescribedDatabaseModelData;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO of the {@link NotificationSettings} class.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationSettingsData extends DescribedDatabaseModelData {

	private static final long serialVersionUID = 1L;

	private List<NotificationTokenData> tokens;

	public NotificationSettingsData() {}

	public List<NotificationTokenData> getTokens() {
		return this.tokens;
	}

	public void setTokens(List<NotificationTokenData> tokens) {
		this.tokens = tokens;
	}

}
