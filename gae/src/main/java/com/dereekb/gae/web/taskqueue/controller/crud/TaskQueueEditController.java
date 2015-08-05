package com.dereekb.gae.web.taskqueue.controller.crud;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.ModelKeyTypeConverterImpl;
import com.dereekb.gae.web.taskqueue.controller.crud.exception.UnregisteredEditTypeException;

/**
 * Task Queue controller used for CRUD related changes.
 *
 * @author dereekb
 *
 */
@Controller
public final class TaskQueueEditController {

	private ModelKeyTypeConverterImpl keyTypeConverter;
	private Map<String, TaskQueueEditControllerEntry> entries;

	public TaskQueueEditController() {}

	public TaskQueueEditController(ModelKeyTypeConverterImpl keyTypeConverter,
	        Map<String, TaskQueueEditControllerEntry> entries) {
		this.keyTypeConverter = keyTypeConverter;
		this.entries = entries;
	}

	public ModelKeyTypeConverterImpl getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(ModelKeyTypeConverterImpl keyTypeConverter) {
		this.keyTypeConverter = keyTypeConverter;
	}

	public Map<String, TaskQueueEditControllerEntry> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, TaskQueueEditControllerEntry> entries) {
		this.entries = entries;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/create", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void reviewCreate(@PathVariable("type") String modelType,
	                         @RequestParam List<String> identifiers) {
		TaskQueueEditControllerEntry entry = this.getEntryForType(modelType);
		List<ModelKey> keys = this.keyTypeConverter.convertKeys(modelType, identifiers);
		entry.reviewCreate(keys);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/update", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void reviewUpdate(@PathVariable("type") String modelType,
	                         @RequestParam List<String> identifiers) {
		TaskQueueEditControllerEntry entry = this.getEntryForType(modelType);
		List<ModelKey> keys = this.keyTypeConverter.convertKeys(modelType, identifiers);
		entry.reviewUpdate(keys);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/delete", method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void reviewDelete(@PathVariable("type") String modelType,
	                         @RequestParam List<String> identifiers) {
		TaskQueueEditControllerEntry entry = this.getEntryForType(modelType);
		List<ModelKey> keys = this.keyTypeConverter.convertKeys(modelType, identifiers);
		entry.reviewDelete(keys);
	}

	private TaskQueueEditControllerEntry getEntryForType(String modelType) throws UnregisteredEditTypeException {
		TaskQueueEditControllerEntry entry = this.entries.get(modelType);

		if (entry == null) {
			throw new UnregisteredEditTypeException();
		}

		return entry;
	}

}
