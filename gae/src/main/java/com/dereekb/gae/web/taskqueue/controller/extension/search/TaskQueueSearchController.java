package com.dereekb.gae.web.taskqueue.controller.extension.search;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.service.TypedDocumentIndexService;
import com.dereekb.gae.model.extension.search.document.index.service.exception.UnregisteredSearchTypeException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;

/**
 * Task Queue controller for manipulating search indexes.
 *
 * @author dereekb
 *
 */
@Controller
public final class TaskQueueSearchController {

	private TypeModelKeyConverter keyTypeConverter;
	private TypedDocumentIndexService service;

	public TaskQueueSearchController() {}

	public TaskQueueSearchController(TypeModelKeyConverter keyTypeConverter, TypedDocumentIndexService service) {
		this.keyTypeConverter = keyTypeConverter;
		this.service = service;
	}

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) {
		this.keyTypeConverter = keyTypeConverter;
	}

	public TypedDocumentIndexService getService() {
		return this.service;
	}

	public void setService(TypedDocumentIndexService service) {
		this.service = service;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/search/index", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void searchIndex(@PathVariable("type") String modelType,
	                        @RequestParam List<String> keys) {
		this.changeIndex(modelType, keys, IndexAction.INDEX);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/search/unindex", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void searchUnindex(@PathVariable("type") String modelType,
	                          @RequestParam List<String> keys) {
		this.changeIndex(modelType, keys, IndexAction.UNINDEX);
	}

	private void changeIndex(String modelType,
	                         List<String> identifiers,
	                         IndexAction action) throws UnregisteredSearchTypeException, AtomicOperationException {
		List<ModelKey> keys = this.keyTypeConverter.convertKeys(modelType, identifiers);
		this.service.changeIndexWithKeys(modelType, keys, action);
	}

	@Override
	public String toString() {
		return "TaskQueueSearchController [keyTypeConverter=" + this.keyTypeConverter + ", indexService=" + this.service
		        + "]";
	}

}
