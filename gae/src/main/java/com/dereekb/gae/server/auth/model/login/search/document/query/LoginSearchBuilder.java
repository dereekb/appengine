package com.dereekb.gae.server.auth.model.login.search.document.query;

import java.util.Map;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.step.model.util.ModelDocumentBuilderUtility;
import com.dereekb.gae.model.extension.search.document.search.model.DateSearch;
import com.dereekb.gae.model.extension.search.document.search.query.impl.AbstractSearchBuilderImpl;
import com.dereekb.gae.model.extension.search.document.search.query.impl.AbstractSearchImpl;
import com.dereekb.gae.server.auth.model.login.search.document.index.LoginDocumentBuilderStep;
import com.dereekb.gae.server.auth.model.login.search.document.query.LoginSearchBuilder.LoginSearch;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.utilities.collections.map.StringMapReader;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Builder for {@link LoginSearch} elements.
 *
 * @author dereekb
 *
 */
public class LoginSearchBuilder extends AbstractSearchBuilderImpl<LoginSearch> {

	public static final String DEFAULT_ID_FIELD = ModelDocumentBuilderUtility.ID_FIELD;
	public static final String DEFAULT_DATE_FIELD = ModelDocumentBuilderUtility.DATE_FIELD;

	private String idField = DEFAULT_ID_FIELD;
	private String dateField = DEFAULT_DATE_FIELD;

	public LoginSearchBuilder() {
		super(LoginDocumentBuilderStep.DERIVATIVE_PREFIX);
	}

	public LoginSearchBuilder(String prefix) {
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

	// MARK: Search
	@Override
	public LoginSearch make() throws FactoryMakeFailureException {
		return new LoginSearch();
	}

	@Override
	public LoginSearch makeNewSearch(Map<String, String> parameters) {
		return new LoginSearch(parameters);
	}

	@Override
	protected void applyParametersToSearch(LoginSearch search,
	                                       StringMapReader reader) {

		search.setId(reader.getLong(this.idField));

		search.setDate(DateSearch.fromString(reader.get(this.dateField)));
	}

	// MAKE: Make
	@Override
	public ExpressionBuilder buildExpression(LoginSearch search,
	                                         String format,
	                                         ExpressionBuilder builder) {

		Long id = search.getId();
		if (id != null) {
			String idName = String.format(format, this.idField);
			builder = builder.and(new AtomField(idName, id));
		}

		DateSearch date = search.getDate();
		if (date != null) {
			String dateName = String.format(format, this.dateField);
			builder = builder.and(date.make(dateName));
		}

		return builder;
	}

	public class LoginSearch extends AbstractSearchImpl {

		private Boolean region;

		private Long id;
		private DateSearch date;

		// TODO

		private LoginSearch() {}

		public LoginSearch(Map<String, String> parameters) {
			this.applyParameters(parameters);
		}

		@Override
        public void applyParameters(Map<String, String> parameters) {
			LoginSearchBuilder.this.applyParameters(this, parameters);
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

		@Override
		public ExpressionBuilder makeExpression() {
			return LoginSearchBuilder.this.make(this);
		}


	}

}
