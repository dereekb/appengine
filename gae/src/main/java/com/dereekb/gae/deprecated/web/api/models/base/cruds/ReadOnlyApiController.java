package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnsupportedServiceRequestException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.functions.utility.SearchStringToQueryConverter;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.service.QueryService;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.service.ReadService;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConverter;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnavailableModelsRequestException;
import com.thevisitcompany.gae.deprecated.web.exceptions.UnsupportedRequestException;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.ReadResponse;
import com.thevisitcompany.gae.deprecated.web.response.models.cruds.SearchResponse;
import com.thevisitcompany.gae.utilities.misc.range.ComparableRange;
import com.thevisitcompany.gae.utilities.misc.range.IntegerRange;

/**
 * Basic, read-only API controller for a {@link KeyedModel} that returns a
 * data-transfer object (DTO).
 *
 * @author dereekb
 *
 * @param <T>
 *            Keyed Model Type
 * @param <K>
 *            Keyed Model Identifier Type
 * @param <A>
 *            DTO Type
 */
public abstract class ReadOnlyApiController<T extends KeyedModel<K>, K, A, Q> {

	protected ModelConverter<T, A> converter;
	private ReadService<T, K> readService;

	private QueryService<T, K, Q> queryService;
	private SearchStringToQueryConverter<Q> queryStringConverter;
	private ComparableRange<Integer> queryRangeLimit = new IntegerRange(10, 1, 20);
	private boolean readAtomicity = false;

	public ReadOnlyApiController() {};

	@ResponseBody
	@RequestMapping(value = "/read", method = RequestMethod.GET, produces = "application/json")
	public final ReadResponse<A, K> read(@RequestParam(required = true) List<K> identifiers,
	                                     @RequestParam(required = false) String type)
	        throws UnavailableModelsRequestException {
		ReadResponse<A, K> response = new ReadResponse<A, K>();
		List<T> retrievedModels = null;

		try {
			retrievedModels = this.readService.read(identifiers);
			List<A> results = this.converter.convertToDataModels(retrievedModels, type);
			response.setModels(results);
		} catch (UnavailableObjectsException e) {
			if (this.readAtomicity) {
				throw new UnavailableModelsRequestException(e, response);
			} else {
				response.setSuccess(true);
			}
		}

		return response;
	}

	@ResponseBody
	@PreAuthorize("hasPermission(this, 'query')")
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json")
	public final SearchResponse<A, K> query(@RequestParam(required = true) String type,
	                                        @RequestParam(required = false, defaultValue = "false") Boolean getModels,
	                                        @RequestParam(required = false) Integer limit)
	        throws UnsupportedServiceRequestException {

		SearchResponse<A, K> response = new SearchResponse<A, K>();

		if (this.queryStringConverter == null) {
			throw new UnsupportedRequestException("Querying is not supported by this type.");
		}

		limit = this.queryRangeLimit.getLimitedValue(limit);
		Q query = this.queryStringConverter.makeQuery(type, limit);

		try {
			if (getModels) {
				List<T> retrievedModels = this.queryService.queryModels(query);
				List<A> retrievedModelsData = this.converter.convertToDataModels(retrievedModels);
				response.setModels(retrievedModelsData);
			} else {
				List<K> retrievedModelIdentifiers = this.queryService.queryKeys(query);
				response.setIdentifiers(retrievedModelIdentifiers);
			}

		} catch (UnsupportedServiceRequestException e) {
			throw new UnsupportedRequestException("Querying is not supported by this type.");
		}

		return response;
	}

	public ModelConverter<T, A> getConverter() {
		return this.converter;
	}

	public void setConverter(ModelConverter<T, A> converter) {
		this.converter = converter;
	}

	public ReadService<T, K> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T, K> readService) {
		this.readService = readService;
	}

	public QueryService<T, K, Q> getQueryService() {
		return this.queryService;
	}

	public void setQueryService(QueryService<T, K, Q> queryService) {
		this.queryService = queryService;
	}

	public SearchStringToQueryConverter<Q> getQueryStringConverter() {
		return this.queryStringConverter;
	}

	public void setQueryStringConverter(SearchStringToQueryConverter<Q> queryStringConverter) {
		this.queryStringConverter = queryStringConverter;
	}

	public ComparableRange<Integer> getQueryRangeLimit() {
		return this.queryRangeLimit;
	}

	public void setQueryRangeLimit(ComparableRange<Integer> queryRangeLimit) {
		this.queryRangeLimit = queryRangeLimit;
	}

	public boolean isReadAtomicity() {
		return this.readAtomicity;
	}

	public void setReadAtomicity(boolean readAtomicity) {
		this.readAtomicity = readAtomicity;
	}

}
