package com.dereekb.gae.client.api.model.extension.link.exception;

import com.dereekb.gae.model.extension.links.components.exception.LinkExceptionReason;
import com.dereekb.gae.model.extension.links.service.LinkSystemChange;

public interface ClientLinkSystemChangeError {

	public LinkSystemChange getChange();

	public LinkExceptionReason getReason();

}