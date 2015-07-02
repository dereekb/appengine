package com.thevisitcompany.gae.deprecated.web.api.models.support.upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConverter;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.response.upload.UploadedModelResponse;
import com.thevisitcompany.gae.server.datastore.models.DatabaseModel;
import com.thevisitcompany.gae.server.storage.upload.FileUploadHandler;
import com.thevisitcompany.gae.server.storage.upload.FileUploadUrlFactory;

public class ModelUploadApiComponent<T extends KeyedModel<K>, A, K> extends UploadApiComponent<T> {

	private ModelConverter<T, A> converter;
	private Boolean returnModels;

	public ModelUploadApiComponent() {};

	public ModelUploadApiComponent(FileUploadUrlFactory uploadUrlFactory) {
		super(uploadUrlFactory);
		this.returnModels = false;
	}

	public ModelUploadApiComponent(FileUploadHandler<T> uploadHandler, FileUploadUrlFactory uploadUrlFactory) {
		super(uploadHandler, uploadUrlFactory);
		this.returnModels = false;
	}

	public ModelUploadApiComponent(FileUploadHandler<T> uploadHandler,
	        FileUploadUrlFactory uploadUrlFactory,
	        ModelConverter<T, A> converter) {
		super(uploadHandler, uploadUrlFactory);
		this.converter = converter;
	}

	public ModelUploadApiComponent(FileUploadHandler<T> uploadHandler,
	        FileUploadUrlFactory uploadUrlFactory,
	        ModelConverter<T, A> converter,
	        Boolean returnModels) {
		super(uploadHandler, uploadUrlFactory);
		this.converter = converter;
		this.returnModels = returnModels;
	}

	public final UploadedModelResponse<A, K> uploadModels(HttpServletRequest request) {
		UploadedModelResponse<A, K> response = new UploadedModelResponse<A, K>();
		List<T> uploadedModels = this.uploadHandler.upload(request);

		if (this.returnModels) {
			List<A> retrievedModelsData = this.converter.convertToDataModels(uploadedModels);
			response.setModels(retrievedModelsData);
		} else {
			List<K> retrievedModelIdentifiers = DatabaseModel.readModelIdentifiers(uploadedModels);
			response.setIdentifiers(retrievedModelIdentifiers);
		}

		return response;
	}

	public ModelConverter<T, A> getConverter() {
		return this.converter;
	}

	public void setConverter(ModelConverter<T, A> converter) {
		this.converter = converter;
	}

	public Boolean getReturnModels() {
		return this.returnModels;
	}

	public void setReturnModels(Boolean returnModels) {
		this.returnModels = (this.converter != null && returnModels);
	}

}
