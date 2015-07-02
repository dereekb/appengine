package com.thevisitcompany.gae.deprecated.model.storage.support.search.query;


@Deprecated
public abstract class StorageModelDefaultStringToQueryConverter<T> extends DefaultStringToQueryConverter<T> {

	private StorageModelQueryRequestBuilder<T> builder;

	public StorageModelDefaultStringToQueryConverter() {
		super();
		super.setAllowRecent(true);
	}

	public StorageModelDefaultStringToQueryConverter(StorageModelQueryRequestBuilder<T> builder) {
		this();
		this.builder = builder;
	}

	public StorageModelQueryRequestBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(StorageModelQueryRequestBuilder<T> builder) {
		super.setBuilder(builder);
		this.builder = builder;
	}

}