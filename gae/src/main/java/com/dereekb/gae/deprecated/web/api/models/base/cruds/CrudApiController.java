package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.CrudService;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnsupportedServiceRequestException;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConverter;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.api.ApiRequest;
import com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create.ApiCreateDirector;
import com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create.CreateRequest;
import com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.delete.ApiDeleteDirector;
import com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.delete.DeleteRequest;
import com.thevisitcompany.gae.deprecated.web.exceptions.ApiRuntimeException;
import com.thevisitcompany.gae.deprecated.web.exceptions.ForbiddenModelRequestException;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnavailableModelsRequestException;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnsupportedRequestException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.CreateResponse;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.DeleteResponse;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.ReadResponse;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.UpdateResponse;
import com.thevisitcompany.gae.model.extension.generation.TypedGenerator;
import com.thevisitcompany.gae.utilities.misc.range.IntegerRange;

/**
 * Spring Annotated API Controller that offers several functions: Sample,
 * Create, Read, Update, and Delete.
 *
 * @author dereekb
 */
@Deprecated
public abstract class CrudApiController<T extends KeyedModel<K>, K, A> {

	private IntegerRange samplesRange = new IntegerRange(1, 1, 20);
	private String defaultSampleType;

	protected ModelConverter<T, A> converter;
	private CrudService<T, K> crudService;

	private ApiCreateDirector<A, K> createDirector;
	private ApiDeleteDirector<K> deleteDirector;
	private TypedGenerator<T> generator;

	public CrudApiController() {};

	@ResponseBody
	@RequestMapping(value = "/sample", method = RequestMethod.GET, produces = "application/json")
	public ReadResponse<A, K> sample(@RequestParam(required = false) Integer count,
	                                 @RequestParam(required = false) String type) {
		ReadResponse<A, K> response = new ReadResponse<A, K>();
		count = this.samplesRange.getLimitedValue(count);

		if (type == null && this.defaultSampleType != null) {
			type = this.defaultSampleType;
		}

		List<T> generatedModels = this.generator.generate(count, type);
		List<A> archives = this.converter.convertToDataModels(generatedModels, type);
		response.setModels(archives);

		return response;
	}

	@ResponseBody
	@PreAuthorize("hasPermission(this, 'create')")
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public final CreateResponse<A, K> create(@Valid @RequestBody CreateRequest<A, K> request) {

		if (this.createDirector == null) {
			throw new UnsupportedRequestException("Cannot create this type.");
		}

		CreateResponse<A, K> response = this.createDirector.create(request);
		return response;
	}

	@ResponseBody
	@PreAuthorize("hasPermission(this, 'read')")
	@RequestMapping(value = "/read", method = RequestMethod.GET, produces = "application/json")
	public final ReadResponse<A, K> read(@RequestParam(required = true) List<K> identifiers,
	                                     @RequestParam(required = false) String type) {
		ReadResponse<A, K> response = new ReadResponse<A, K>();
		List<T> retrievedModels = null;

		try {
			retrievedModels = this.crudService.read(identifiers);
			List<A> results = this.converter.convertToDataModels(retrievedModels, type);
			response.setModels(results);
		} catch (UnavailableObjectsException e) {
			throw new UnavailableModelsRequestException(e, response);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	@ResponseBody
	@PreAuthorize("hasPermission(this, 'update')")
	@RequestMapping(value = { "/update", "/edit" }, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public final UpdateResponse<A, K> update(@RequestBody @Valid ApiRequest<A>> request,
	                                         @RequestParam(required = false, defaultValue = "false") Boolean getModels) {
		UpdateResponse<A, K> response = new UpdateResponse<A, K>();
		List<A> data = request.getData();

		try {
			List<T> models = this.converter.convertToObjects(data);
			List<K> updatedIds = this.crudService.update(models);

			if (getModels) {
				List<T> updatedModels = this.crudService.read(updatedIds);
				List<A> results = this.converter.convertToDataModels(updatedModels);
				response.setModels(results);
			} else {
				response.setIdentifiers(updatedIds);
			}

			response.setSuccess(true);
		} catch (ForbiddenObjectChangesException e) {
			throw new ForbiddenModelRequestException(response);
		} catch (UnsupportedServiceRequestException e) {
			throw new UnsupportedRequestException("Editing this type is unsupported.");
		} catch (UnavailableObjectsException e) {
			throw new UnavailableModelsRequestException(e, response);
		} catch (RuntimeException e) {
			throw new ApiRuntimeException(e);
		}

		return response;
	}

	@ResponseBody
	@PreAuthorize("hasPermission(this, 'delete')")
	@RequestMapping(value = { "/delete", "/destroy" }, method = RequestMethod.DELETE, produces = "application/json")
	public final DeleteResponse<K> delete(@RequestBody @Valid DeleteRequest<K> request) {

		if (this.deleteDirector == null) {
			throw new UnsupportedRequestException("Cannot delete this type.");
		}

		DeleteResponse<K> response = this.deleteDirector.delete(request);
		return response;
	}

	public String getDefaultSampleType() {
		return this.defaultSampleType;
	}

	public void setDefaultSampleType(String defaultSampleType) {
		this.defaultSampleType = defaultSampleType;
	}

	public ModelConverter<T, A> getConverter() {
		return this.converter;
	}

	public void setConverter(ModelConverter<T, A> converter) {
		this.converter = converter;
	}

	public TypedGenerator<T> getGenerator() {
		return this.generator;
	}

	public void setGenerator(TypedGenerator<T> generator) {
		this.generator = generator;
	}

	public CrudService<T, K> getCrudService() {
		return this.crudService;
	}

	public void setCrudService(CrudService<T, K> crudService) {
		this.crudService = crudService;
	}

	public ApiCreateDirector<A, K> getCreateDirector() {
		return this.createDirector;
	}

	public void setCreateDirector(ApiCreateDirector<A, K> createDirector) {
		this.createDirector = createDirector;
	}

	public ApiDeleteDirector<K> getDeleteDirector() {
		return this.deleteDirector;
	}

	public void setDeleteDirector(ApiDeleteDirector<K> deleteDirector) {
		this.deleteDirector = deleteDirector;
	}

	public IntegerRange getSamplesRange() {
		return this.samplesRange;
	}

	public void setSamplesRange(IntegerRange samplesRange) {
		this.samplesRange = samplesRange;
	}

}
