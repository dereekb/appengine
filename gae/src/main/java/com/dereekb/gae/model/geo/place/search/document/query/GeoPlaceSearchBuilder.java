package com.dereekb.gae.model.geo.place.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.model.PointRadiusSearch;
import com.dereekb.gae.model.geo.place.search.document.index.GeoPlaceDocumentBuilderStep;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilderSource;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.BooleanField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionStart;
import com.dereekb.gae.utilities.collections.map.MapReader;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Builder for {@link GeoPlaceSearch} elements.
 *
 * @author dereekb
 *
 */
public class GeoPlaceSearchBuilder
        implements Factory<GeoPlaceSearch> {

	public static final String DEFAULT_ID_FIELD = ModelDocumentBuilderUtility.ID_FIELD;
	public static final String DEFAULT_DATE_FIELD = ModelDocumentBuilderUtility.DATE_FIELD;
	public static final String DEFAULT_POINT_FIELD = ModelDocumentBuilderUtility.POINT_FIELD;
	public static final String DEFAULT_REGION_FIELD = GeoPlaceDocumentBuilderStep.REGION_FIELD;

	private String prefix = GeoPlaceDocumentBuilderStep.DERIVATIVE_PREFIX;

	private String idField = DEFAULT_ID_FIELD;
	private String dateField = DEFAULT_DATE_FIELD;
	private String pointField = DEFAULT_POINT_FIELD;
	private String isRegionField = DEFAULT_REGION_FIELD;

	public GeoPlaceSearchBuilder() {}

	public GeoPlaceSearchBuilder(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getIdField() {
		return this.idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getDateField() {
		return this.dateField;
	}

	public void setDateField(String dateField) {
		this.dateField = dateField;
	}

	public String getPointField() {
		return this.pointField;
	}

	public void setPointField(String pointField) {
		this.pointField = pointField;
	}

	public String getIsRegionField() {
		return this.isRegionField;
	}

	public void setIsRegionField(String isRegionField) {
		this.isRegionField = isRegionField;
	}

	public GeoPlaceSearch make(Map<String, String> parameters) {
		GeoPlaceSearch search = new GeoPlaceSearch(parameters);

		if (search.hasValues() == false) {
			search = null;
		}

		return search;
	}

	public void applyParameters(GeoPlaceSearch search,
	                            Map<String, String> parameters) {
		MapReader<String> reader = new MapReader<String>(parameters, this.getFormat());

		if (reader.containsKey(this.idField)) {
			Long id = new Long(reader.get(this.idField));
			search.setId(id);
		}

		if (reader.containsKey(this.isRegionField)) {
			Boolean region = new Boolean(reader.get(this.isRegionField));
			search.setRegion(region);
		}

		search.setPoint(PointRadiusSearch.fromString(reader.get(this.pointField)));
		search.setDate(DateSearch.fromString(reader.get(this.dateField)));
	}

	public String getFormat() {
		return this.prefix + "%s";
	}

	public ExpressionBuilder make(GeoPlaceSearch search) {
		return this.make(search, this.getFormat());
	}

	public ExpressionBuilder make(GeoPlaceSearch search,
	                              String format) {
		ExpressionBuilder builder = new ExpressionStart();

		Long id = search.getId();
		if (id != null) {
			String idName = String.format(format, this.idField);
			builder = builder.and(new AtomField(idName, id));
		}

		Boolean region = search.getRegion();
		if (region != null) {
			String regionName = String.format(format, this.isRegionField);
			builder = builder.and(new BooleanField(regionName, region));
		}

		PointRadiusSearch point = search.getPoint();
		if (point != null) {
			String pointName = String.format(format, this.pointField);
			builder = builder.and(point.make(pointName));
		}

		DateSearch date = search.getDate();
		if (date != null) {
			String dateName = String.format(format, this.dateField);
			builder = builder.and(date.make(dateName));
		}

		return builder;
	}

	@Override
	public GeoPlaceSearch make() throws FactoryMakeFailureException {
		return new GeoPlaceSearch();
	}

	public class GeoPlaceSearch
	        implements ExpressionBuilderSource {

		private Boolean region;

		private Long id;
		private DateSearch date;
		private PointRadiusSearch point;

		private GeoPlaceSearch() {}

		public GeoPlaceSearch(Map<String, String> parameters) {
			this.applyParameters(parameters);
		}

		public void applyParameters(Map<String, String> parameters) {
			GeoPlaceSearchBuilder.this.applyParameters(this, parameters);
		}

		public Boolean getRegion() {
			return this.region;
		}

		public void setRegion(Boolean region) {
			this.region = region;
		}

		public Long getId() {
			return this.id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public DateSearch getDate() {
			return this.date;
		}

		public void setDate(DateSearch date) {
			this.date = date;
		}

		public PointRadiusSearch getPoint() {
			return this.point;
		}

		public void setPoint(PointRadiusSearch point) {
			this.point = point;
		}

		public boolean hasValues() {
			return (this.date != null || this.region != null || this.point != null || this.id != null);
		}

		@Override
		public ExpressionBuilder makeExpression() {
			return GeoPlaceSearchBuilder.this.make(this);
		}

		@Override
		public String toString() {
			return "GeoPlaceSearch [region=" + this.region + ", id=" + this.id + ", date=" + this.date + ", point="
			        + this.point + "]";
		}

	}

}
