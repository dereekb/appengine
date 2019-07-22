package com.dereekb.gae.server.mail.service.impl;

import com.dereekb.gae.server.mail.service.MailServiceRequestBody;
import com.dereekb.gae.server.mail.service.MailServiceRequestBodyType;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;

/**
 * {@link MailServiceRequestBody}.
 *
 * @author dereekb
 *
 */
public class MailServiceRequestBodyImpl
        implements MailServiceRequestBody {

	private String subject;
	private String bodyContent;
	private MailServiceRequestBodyType bodyType;
	private Parameters parameters;

	public MailServiceRequestBodyImpl(String subject, String bodyContent) {
		this(subject, bodyContent, MailServiceRequestBodyType.PLAIN_TEXT);
	}

	public MailServiceRequestBodyImpl(String subject, String bodyContent, MailServiceRequestBodyType bodyType) {
		super();
		this.setSubject(subject);
		this.setBodyType(bodyType);
		this.setBodyContent(bodyContent);
	}

	// MARK: MailServiceRequestBody
	@Override
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		if (StringUtility.isEmptyString(subject)) {
			throw new IllegalArgumentException("subject cannot be null or empty.");
		}

		this.subject = subject;
	}

	@Override
	public String getBodyContent() {
		return this.bodyContent;
	}

	public void setBodyContent(String bodyContent) {
		if (StringUtility.isEmptyString(bodyContent)) {
			throw new IllegalArgumentException("bodyContent cannot be null or empty.");
		}

		this.bodyContent = bodyContent;
	}

	@Override
	public MailServiceRequestBodyType getBodyType() {
		return this.bodyType;
	}

	public void setBodyType(MailServiceRequestBodyType bodyType) {
		if (bodyType == null) {
			throw new IllegalArgumentException("bodyType cannot be null.");
		}

		this.bodyType = bodyType;
	}

	@Override
	public Parameters getParameters() {
		if (this.parameters != null) {
			return this.parameters;
		} else {
			return new ParametersImpl();
		}
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "MailServiceRequestBodyImpl [subject=" + this.subject + ", bodyContent=" + this.bodyContent
		        + ", bodyType=" + this.bodyType + ", parameters=" + this.parameters + "]";
	}

}
