package com.dereekb.gae.server.mail.service.impl.provider.mailgun;

import com.dereekb.gae.server.mail.service.MailService;
import com.dereekb.gae.server.mail.service.impl.provider.MailServiceProvider;

/**
 * {@link MailService} for Mailgun.
 * 
 * @author dereekb
 *
 */
public interface MailgunMailService
        extends MailServiceProvider<MailgunMailServiceRequest, MailgunMailServiceResponse> {

}
