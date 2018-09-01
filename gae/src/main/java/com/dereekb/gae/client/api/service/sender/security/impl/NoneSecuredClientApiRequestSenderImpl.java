package com.dereekb.gae.client.api.service.sender.security.impl;

import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.sender.ClientApiRequestSender;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;

/**
 * {@link SecuredClientApiRequestSender} implementation that always has a
 * security level of "None".
 *
 * @author dereekb
 *
 */
public class NoneSecuredClientApiRequestSenderImpl extends AbstractSecuredClientApiRequestSenderImpl {

	public NoneSecuredClientApiRequestSenderImpl(ClientApiRequestSender sender) {
		super(sender, ClientRequestSecurityImpl.none());
	}

	public static NoneSecuredClientApiRequestSenderImpl make(ClientApiRequestSender sender) {
		return new NoneSecuredClientApiRequestSenderImpl(sender);
	}

	@Override
	public void setDefaultSecurity(ClientRequestSecurity defaultSecurity) {
		super.setDefaultSecurity(ClientRequestSecurityImpl.none());
	}

	// MARK: AbstractSecuredClientApiRequestSenderImpl
	@Override
	protected ClientRequest buildSecuredRequest(ClientRequest request,
	                                            ClientRequestSecurity security) {
		return request;
	}

	@Override
	public String toString() {
		return "NoneSecuredClientApiRequestSenderImpl []";
	}

}
