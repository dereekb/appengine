package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.CrudsService;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnsupportedServiceRequestException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.functions.utility.SearchStringToQueryConverter;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConverter;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnsupportedRequestException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.SearchResponse;
import com.thevisitcompany.gae.utilities.misc.range.ComparableRange;
import com.thevisitcompany.gae.utilities.misc.range.IntegerRange;

/**
 * Cruds API Controller
 *
 * @author dereekb
 *
 * @param <T>
 *            Model Type
 * @param <K>
 *            Model's Identifier Type
 * @param <Q>
 *            Query Type
 * @param <A>
 *            Archive Type
 */
public abstract class CrudsApiController<T extends KeyedModel<K>, K, A, Q> extends CrudApiController<T, K, A> {

	private CrudsService<T, K, Q> crudsService;
	private SearchStringToQueryConverter<Q> queryStringConverter;
	private ComparableRange<Integer> queryRangeLimit = new IntegerRange(20, 1, 50);

	public CrudsApiController() {};

	/**
	 * Public search of target object type.
	 *
	 * @param value
	 * @param getModels Returns models if true.
	 * @return
	 */
	@ResponseBody
	@PreAuthorize("hasPermission(this, 'query')")
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json")
	public final SearchResponse<A, K> stringQuery(@RequestParam(required = true) String type,
	                                              @RequestParam(required = false, defaultValue = "false") Boolean getModels,
	                                              @RequestParam(required = false) Integer limit) {

		SearchResponse<A, K> response = new SearchResponse<A, K>();

		if (this.queryStringConverter == null) {
			throw new UnsupportedRequestException("Querying is not supported by this type.");
		}

		limit = this.queryRangeLimit.getLimitedValue(limit);
		Q query = this.queryStringConverter.makeQuery(type, limit);

		try {
			if (getModels) {
				ModelConverter<T, A> converter = this.getConverter();

				List<T> retrievedModels = this.crudsService.queryModels(query);
				List<A> retrievedModelsData = converter.convertToDataModels(retrievedModels);
				response.setModels(retrievedModelsData);
			} else {
				List<K> retrievedModelIdentifiers = this.crudsService.queryKeys(query);
				response.setIdentifiers(retrievedModelIdentifiers);
			}

		} catch (UnsupportedServiceRequestException e) {
			throw new UnsupportedRequestException("Searching this type is unsupported.");
		}

		return response;
	}

	public SearchStringToQueryConverter<Q> getQueryStringConverter() {
		return this.queryStringConverter;
	}

	public void setQueryStringConverter(SearchStringToQueryConverter<Q> queryStringConverter) {
		this.queryStringConverter = queryStringConverter;
	}

	public CrudsService<T, K, Q> getCrudsService() {
		return this.crudsService;
	}

	public void setCrudsService(CrudsService<T, K, Q> crudsService) {
		super.setCrudService(crudsService);
		this.crudsService = crudsService;
	}

	public ComparableRange<Integer> getQueryRangeLimit() {
		return this.queryRangeLimit;
	}

	public void setQueryRangeLimit(ComparableRange<Integer> queryRangeLimit) {
		this.queryRangeLimit = queryRangeLimit;
	}

}
