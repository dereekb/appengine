package com.thevisitcompany.gae.deprecated.model.storage.support.search.document;

import java.util.Date;

import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.thevisitcompany.gae.deprecated.model.storage.StorageModel;
import com.thevisitcompany.gae.deprecated.model.storage.StorageModelItemInfo;

@Deprecated
public abstract class StorageModelSearchDocument<T extends StorageModel<T>> extends ModelDocument<T, Long> {

	public StorageModelSearchDocument(T model) {
		super(model);
	}

	@Override
	protected void addModelVariables(Builder document) {
		StorageModelItemInfo info = this.model.getItemInfo();

		if (info != null) {
			String group = info.getGroup();
			Field.Builder groupField = Field.newBuilder().setName("group").setText(group);
			document.addField(groupField);

			String category = info.getCategory();
			Field.Builder categoryField = Field.newBuilder().setName("category").setText(category);
			document.addField(categoryField);
		}

		Date creationDate = this.model.getCreationDate();
		Field.Builder dateField = Field.newBuilder().setName("created").setDate(creationDate);
		document.addField(dateField);
	}

}
