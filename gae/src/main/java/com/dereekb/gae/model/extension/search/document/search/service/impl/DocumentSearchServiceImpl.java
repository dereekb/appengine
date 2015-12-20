package com.dereekb.gae.model.extension.search.document.search.service.impl;

import com.dereekb.gae.model.extension.search.document.search.service.DocumentSearchRequest;
import com.dereekb.gae.model.extension.search.document.search.service.DocumentSearchRequestOptions;
import com.dereekb.gae.model.extension.search.document.search.service.DocumentSearchService;
import com.dereekb.gae.server.search.service.SearchDocumentQueryService;
import com.dereekb.gae.server.search.service.request.impl.DocumentQueryRequestImpl;
import com.dereekb.gae.server.search.service.response.SearchDocumentQueryResponse;
import com.dereekb.gae.utilities.task.Task;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;

/**
 * {@link DocumentSearchService} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentSearchServiceImpl
        implements DocumentSearchService {

	private SearchDocumentQueryService service;
	private Task<QueryOptions.Builder> queryBuilderTask;

	public DocumentSearchServiceImpl(SearchDocumentQueryService service) {
		this.service = service;
	}

	public DocumentSearchServiceImpl(SearchDocumentQueryService service, Task<QueryOptions.Builder> queryBuilderTask) {
		this.service = service;
		this.queryBuilderTask = queryBuilderTask;
	}

	public SearchDocumentQueryService getService() {
		return this.service;
	}

	public void setService(SearchDocumentQueryService service) {
		this.service = service;
	}

	public Task<QueryOptions.Builder> getQueryBuilderTask() {
		return this.queryBuilderTask;
	}

	public void setQueryBuilderTask(Task<QueryOptions.Builder> queryBuilderTask) {
		this.queryBuilderTask = queryBuilderTask;
	}

	// MARK: DocumentSearchService
	@Override
	public SearchDocumentQueryResponse search(DocumentSearchRequest request) {
		DocumentQueryRequestImpl queryRequest = new DocumentQueryRequestImpl();

		Query.Builder queryBuilder = Query.newBuilder();
		QueryOptions.Builder optionsBuilder = this.buildOptions(request.getOptions());

		queryBuilder.setOptions(optionsBuilder);

		queryRequest.setIndexName(request.getIndex());
		queryRequest.setDocumentQuery(queryBuilder);

		SearchDocumentQueryResponse response = this.service.queryDocuments(queryRequest);
		return response;
	}

	public QueryOptions.Builder buildOptions(DocumentSearchRequestOptions options) {
		QueryOptions.Builder builder = QueryOptions.newBuilder();

		if (options != null) {
			//Cursor
			String cursorString = options.getCursor();
			if (cursorString != null) {
				builder.setCursor(Cursor.newBuilder().build(cursorString));
			}

			//Limit
			Integer limit = options.getLimit();
			if (limit != null) {
				builder.setLimit(limit);
			}

			//Offset
			Integer offset = options.getOffset();
			if (offset != null) {
				builder.setOffset(offset);
			}
		}

		if (this.queryBuilderTask != null) {
			this.queryBuilderTask.doTask(builder);
		}

		return builder;
	}

	@Override
	public String toString() {
		return "DocumentSearchServiceImpl [service=" + this.service + ", queryBuilderTask=" + this.queryBuilderTask
		        + "]";
	}

}
