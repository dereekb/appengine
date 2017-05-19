package com.dereekb.gae.client.api.model.extension.link.exception;

import java.util.List;

// MARK: Interfaces
public interface ClientLinkSystemChangeErrorSet {

	public String getType();

	public List<ClientLinkSystemChangeError> getErrors();

}