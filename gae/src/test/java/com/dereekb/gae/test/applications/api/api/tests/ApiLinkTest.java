package com.dereekb.gae.test.applications.api.api.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;
import com.dereekb.gae.web.api.model.extension.link.LinkExtensionApiController;
import com.dereekb.gae.web.api.model.extension.link.impl.ApiLinkChangeRequest;
import com.dereekb.gae.web.api.shared.request.ApiRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

public abstract class ApiLinkTest<T extends UniqueModel> extends ApiApplicationTestContext {

	private String modelType;

	@Autowired
	@Qualifier("linkExtensionApiController")
	protected LinkExtensionApiController controller;

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	protected void performRequest(ApiRequest<ApiLinkChange> request) {
		ApiLinkChangeRequest changeRequest = new ApiLinkChangeRequest(request.getData());
		this.performRequest(changeRequest);
	}

	protected void performRequest(ApiLinkChangeRequest request) {
		try {
			ApiResponse response = this.controller.link(this.modelType, request);
			Assert.assertTrue(response.getResponseSuccess());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	protected ApiRequest<ApiLinkChange> buildRequest(LinkChangeAction action,
	                                                       String linkName,
	                                                     String targetType,
	                                                     T primaryModel,
	                                                     UniqueModel targetModel) {
		return this.buildRequest(action, linkName, targetType, primaryModel, SingleItem.withValue(targetModel));
	}

	protected ApiRequest<ApiLinkChange> buildRequest(LinkChangeAction action,
	                                                       String linkName,
	                                                       String targetType,
	                                                       T primaryModel,
	                                                       Collection<? extends UniqueModel> targetModels) {

		List<ApiLinkChange> changes = new ArrayList<ApiLinkChange>();
		ApiLinkChange targetChange = new ApiLinkChange();
		targetChange.setAction(action.getAction());
		targetChange.setLinkName(linkName);
		targetChange.setTargetKeys(ModelKey.readStringKeys(targetModels));
		targetChange.setPrimaryKey(primaryModel.getModelKey().keyAsString());

		changes.add(targetChange);

		ApiRequest<ApiLinkChange> request = new ApiRequest<>();
		request.setData(changes);
		return request;
	}

}
