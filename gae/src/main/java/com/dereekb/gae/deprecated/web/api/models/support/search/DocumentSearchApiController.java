package com.thevisitcompany.gae.deprecated.web.api.models.support.search;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.DocumentSearchRequest;
import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.DocumentSearchStringConverter;
import com.thevisitcompany.gae.deprecated.model.extension.search.document.search.service.DocumentSearchService;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnsupportedServiceRequestException;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedSearchableModel;
import com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.ReadOnlyApiController;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnsupportedRequestException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.SearchResponse;
import com.thevisitcompany.gae.utilities.misc.range.ComparableRange;
import com.thevisitcompany.gae.utilities.misc.range.IntegerRange;

public abstract class DocumentSearchApiController<T extends KeyedSearchableModel<K>, K, A, Q> extends ReadOnlyApiController<T, K, A, Q> {

	private ComparableRange<Integer> searchRangeLimit = new IntegerRange(20, 1, 40);

	private DocumentSearchService<T, K> searchService;
	private DocumentSearchStringConverter searchStringConverter;

	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public final SearchResponse<A, K> search(@RequestParam String query,
	                                         @RequestParam(required = false, defaultValue = "false") Boolean getModels,
	                                         @RequestParam(required = false) Integer limit) {

		SearchResponse<A, K> response = new SearchResponse<A, K>();
		DocumentSearchRequest searchRequest = this.searchStringConverter.makeQuery(query);

		limit = this.searchRangeLimit.getLimitedValue(limit);
		searchRequest.setLimit(limit);

		try {
			if (getModels) {
				List<T> retrievedModels = this.searchService.searchModels(searchRequest);
				List<A> retrievedModelsData = this.converter.convertToDataModels(retrievedModels);
				response.setModels(retrievedModelsData);
			} else {
				List<K> retrievedModelIdentifiers = this.searchService.search(searchRequest);
				response.setIdentifiers(retrievedModelIdentifiers);
			}

		} catch (UnsupportedServiceRequestException e) {
			throw new UnsupportedRequestException("Searching this type is unsupported.");
		}

		return response;
	}

	public DocumentSearchService<T, K> getSearchService() {
		return this.searchService;
	}

	public void setSearchService(DocumentSearchService<T, K> searchService) {
		this.searchService = searchService;
	}

	public DocumentSearchStringConverter getSearchStringConverter() {
		return this.searchStringConverter;
	}

	public void setSearchStringConverter(DocumentSearchStringConverter searchStringConverter) {
		this.searchStringConverter = searchStringConverter;
	}

	public ComparableRange<Integer> getSearchRangeLimit() {
		return this.searchRangeLimit;
	}

	public void setSearchRangeLimit(ComparableRange<Integer> searchRangeLimit) {
		this.searchRangeLimit = searchRangeLimit;
	}

}
