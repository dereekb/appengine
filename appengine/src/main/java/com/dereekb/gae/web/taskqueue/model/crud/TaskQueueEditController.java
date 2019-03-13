package com.dereekb.gae.web.taskqueue.model.crud;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.map.impl.CaseInsensitiveEntryContainerImpl;
import com.dereekb.gae.web.taskqueue.model.crud.exception.UnregisteredEditTypeException;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;

/**
 * Task Queue controller used for CRUD related changes.
 *
 * @author dereekb
 *
 * @deprecated Send taskqueue requests to {@link TaskQueueIterateController} instead.
 */
@Deprecated
@RestController
@RequestMapping("/taskqueue")
public final class TaskQueueEditController extends CaseInsensitiveEntryContainerImpl<TaskQueueEditControllerEntry> {

	private static final Logger LOGGER = Logger.getLogger(TaskQueueIterateController.class.getName());

	public static final String CREATE_PATH = "create";
	public static final String UPDATE_PATH = "update";
	public static final String DELETE_PATH = "delete";

	private TypeModelKeyConverter keyTypeConverter;

	public TaskQueueEditController() {}

	@Deprecated
	public TaskQueueEditController(TypeModelKeyConverter keyTypeConverter,
	        Map<String, TaskQueueEditControllerEntry> entries) {
		super(entries);
		this.setKeyTypeConverter(keyTypeConverter);
	}

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) {
		if (keyTypeConverter == null) {
			throw new IllegalArgumentException("keyTypeConverter cannot be null.");
		}

		this.keyTypeConverter = keyTypeConverter;
	}

	// CRUD
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/" + CREATE_PATH, method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void reviewCreate(@PathVariable("type") String modelType,
	                         @RequestParam("keys") List<String> identifiers) {
		try {
			TaskQueueEditControllerEntry entry = this.getEntryForType(modelType);
			List<ModelKey> keys = this.keyTypeConverter.convertKeys(modelType, identifiers);
			entry.reviewCreate(keys);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "Create review task failed.", e);
			throw e;
		}
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/" + UPDATE_PATH, method = RequestMethod.PUT, consumes = "application/octet-stream")
	public void reviewUpdate(@PathVariable("type") String modelType,
	                         @RequestParam("keys") List<String> identifiers) {
		try {
			TaskQueueEditControllerEntry entry = this.getEntryForType(modelType);
			List<ModelKey> keys = this.keyTypeConverter.convertKeys(modelType, identifiers);
			entry.reviewUpdate(keys);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "Update review task failed.", e);
			throw e;
		}
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/{type}/"
	        + DELETE_PATH, method = RequestMethod.DELETE, consumes = "application/octet-stream")
	public void processDelete(@PathVariable("type") String modelType,
	                          @RequestParam("keys") List<String> identifiers) {
		try {
			TaskQueueEditControllerEntry entry = this.getEntryForType(modelType);
			List<ModelKey> keys = this.keyTypeConverter.convertKeys(modelType, identifiers);
			entry.processDelete(keys);
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "Delete review task failed.", e);
			throw e;
		}
	}

	// MARK: Entries
	@Override
	protected void throwEntryDoesntExistException(String type) throws RuntimeException {
		throw new UnregisteredEditTypeException(type);
	}

	@Override
	public String toString() {
		return "TaskQueueEditController [keyTypeConverter=" + this.keyTypeConverter + "]";
	}

}
