package com.dereekb.gae.web.taskqueue.server.task.impl.lifecycle.apps;

import java.util.List;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorUtility;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.app.model.app.generator.AppGenerator;
import com.dereekb.gae.server.datastore.Storer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.mail.service.MailRecipient;
import com.dereekb.gae.server.mail.service.MailService;
import com.dereekb.gae.server.mail.service.MailServiceRequest;
import com.dereekb.gae.server.mail.service.exception.MailSendFailureException;
import com.dereekb.gae.server.mail.service.impl.MailRecipientImpl;
import com.dereekb.gae.server.mail.service.impl.MailServiceRequestBodyImpl;
import com.dereekb.gae.server.mail.service.impl.MailServiceRequestImpl;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.taskqueue.server.task.TaskQueueTaskControllerEntry;
import com.dereekb.gae.web.taskqueue.server.task.TaskQueueTaskControllerRequest;
import com.dereekb.gae.web.taskqueue.server.task.impl.lifecycle.apps.impl.AllocateAppsRequestImpl;
import com.dereekb.gae.web.taskqueue.server.task.impl.lifecycle.apps.impl.AllocateAppsResponseImpl;
import com.google.apphosting.api.ApiProxy.Environment;

/**
 * Task used for pre-allocating {@link App} configurations for your app to use.
 *
 * @author dereekb
 *
 */
public class AllocateAppsTask
        implements Task<AllocateAppsTaskPair>, TaskQueueTaskControllerEntry {

	private static final Integer DEFAULT_APP_COUNT = 20;

	private AllocateAppsRequest request = new AllocateAppsRequestImpl(DEFAULT_APP_COUNT);

	private MailService mailService;
	private Storer<App> appStorer;

	private Generator<App> appGenerator = new AppGenerator(AppLoginSecurityLevel.APP);

	public AllocateAppsTask(MailService mailService, Storer<App> appStorer) {
		this.setMailService(mailService);
		this.setAppStorer(appStorer);
	}

	public AllocateAppsRequest getRequest() {
		return this.request;
	}

	public void setRequest(AllocateAppsRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("request cannot be null.");
		}

		this.request = request;
	}

	public MailService getMailService() {
		return this.mailService;
	}

	public void setMailService(MailService mailService) {
		if (mailService == null) {
			throw new IllegalArgumentException("mailService cannot be null.");
		}

		this.mailService = mailService;
	}

	public Storer<App> getAppStorer() {
		return this.appStorer;
	}

	public void setAppStorer(Storer<App> appStorer) {
		if (appStorer == null) {
			throw new IllegalArgumentException("appStorer cannot be null.");
		}

		this.appStorer = appStorer;
	}

	public Generator<App> getAppGenerator() {
		return this.appGenerator;
	}

	public void setAppGenerator(Generator<App> appGenerator) {
		if (appGenerator == null) {
			throw new IllegalArgumentException("appGenerator cannot be null.");
		}

		this.appGenerator = appGenerator;
	}

	// MARK: TaskQueueTaskControllerEntry
	@Override
	public void doTask(AllocateAppsTaskPair input) throws FailedTaskException {
		input.setResult(this.makeApps(input.getKey()));
	}

	@Override
	public void performTask(TaskQueueTaskControllerRequest request) throws RuntimeException {
		this.makeApps(this.request);
	}

	protected AllocateAppsResponse makeApps(AllocateAppsRequest request) {
		List<App> apps = GeneratorUtility.generate(request.getAppCount(), this.appGenerator);
		this.appStorer.store(apps);

		MailServiceRequest mailRequest = this.makeMailRequest(apps);

		try {
			this.mailService.sendMail(mailRequest);
		} catch (MailSendFailureException e) {
			e.printStackTrace();
		}

		return new AllocateAppsResponseImpl(apps);
	}

	private MailServiceRequest makeMailRequest(List<App> apps) {
		List<ModelKey> keys = ModelKey.readModelKeys(apps);

		MailServiceRequestImpl request = new MailServiceRequestImpl();

		Environment enviroment = GoogleAppEngineUtility.getApiEnvironment();

		String appId = enviroment.getAppId();
		String versionId = enviroment.getVersionId();
		String email = enviroment.getEmail();

		String subject = "Created apps.";
		String bodyContent = "Created apps for " + appId + " - " + versionId + "\n\n";

		for (ModelKey key : keys) {
			bodyContent += "" + key + "\n";
		}

		MailServiceRequestBodyImpl body = new MailServiceRequestBodyImpl(subject, bodyContent);
		request.setBody(body);

		MailRecipient recipient = new MailRecipientImpl(email);
		request.setRecipient(recipient);

		return request;
	}

	@Override
	public String toString() {
		return "AllocateAppsTask [request=" + this.request + ", mailService=" + this.mailService + ", appStorer="
		        + this.appStorer + ", appGenerator=" + this.appGenerator + "]";
	}

}
