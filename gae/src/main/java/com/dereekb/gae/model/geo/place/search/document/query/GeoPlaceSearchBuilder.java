package com.dereekb.gae.model.geo.place.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.model.PointRadiusSearch;
import com.dereekb.gae.model.extension.search.document.search.query.impl.AbstractSearchBuilderImpl;
import com.dereekb.gae.model.extension.search.document.search.query.impl.AbstractSearchImpl;
import com.dereekb.gae.model.geo.place.search.document.index.GeoPlaceDocumentBuilderStep;
import com.dereekb.gae.model.geo.place.search.document.query.GeoPlaceSearchBuilder.GeoPlaceSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.BooleanField;
import com.dereekb.gae.utilities.collections.map.StringMapReader;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Builder for {@link GeoPlaceSearch} elements.
 *
 * @author dereekb
 *
 */
public class GeoPlaceSearchBuilder extends AbstractSearchBuilderImpl<GeoPlaceSearch> {

	public static final String DEFAULT_ID_FIELD = ModelDocumentBuilderUtility.ID_FIELD;
	public static final String DEFAULT_DATE_FIELD = ModelDocumentBuilderUtility.DATE_FIELD;
	public static final String DEFAULT_POINT_FIELD = ModelDocumentBuilderUtility.POINT_FIELD;
	public static final String DEFAULT_REGION_FIELD = GeoPlaceDocumentBuilderStep.REGION_FIELD;

	private String idField = DEFAULT_ID_FIELD;
	private String dateField = DEFAULT_DATE_FIELD;
	private String pointField = DEFAULT_POINT_FIELD;
	private String isRegionField = DEFAULT_REGION_FIELD;

	public GeoPlaceSearchBuilder() {
		super(GeoPlaceDocumentBuilderStep.DERIVATIVE_PREFIX);
	}

	public GeoPlaceSearchBuilder(String prefix) {
		super(prefix);
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

	// MARK: Search
	@Override
	public GeoPlaceSearch make() throws FactoryMakeFailureException {
		return new GeoPlaceSearch();
	}

	@Override
	public GeoPlaceSearch makeNewSearch(Map<String, String> parameters) {
		return new GeoPlaceSearch(parameters);
	}

	@Override
	protected void applyParametersToSearch(GeoPlaceSearch search,
	                                       StringMapReader reader) {

		search.setId(reader.getLong(this.idField));

		search.setRegion(reader.getBoolean(this.isRegionField));

		search.setPoint(PointRadiusSearch.fromString(reader.get(this.pointField)));
		search.setDate(DateSearch.fromString(reader.get(this.dateField)));
	}

	// MAKE: Make
	@Override
	public ExpressionBuilder buildExpression(GeoPlaceSearch search,
	                                         String format,
	                                         ExpressionBuilder builder) {

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

	public class GeoPlaceSearch extends AbstractSearchImpl {

		private Boolean region;

		private Long id;
		private DateSearch date;
		private PointRadiusSearch point;

		private GeoPlaceSearch() {}

		public GeoPlaceSearch(Map<String, String> parameters) {
			this.applyParameters(parameters);
		}

		@Override
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
