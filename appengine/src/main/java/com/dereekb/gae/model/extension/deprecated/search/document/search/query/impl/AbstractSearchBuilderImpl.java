package com.dereekb.gae.model.extension.search.document.search.query.impl;

import java.util.Map;

import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.impl.field.ExpressionStart;
import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.impl.field.SimpleExpression;
import com.dereekb.gae.utilities.collections.map.StringMapReader;
import com.dereekb.gae.utilities.factory.Factory;

/**
 *
 * @author dereekb
 *
 * @param <S>
 */
public abstract class AbstractSearchBuilderImpl<S extends AbstractSearchImpl>
        implements Factory<S> {

	public static final String DEFAULT_TEXT_QUERY_FIELD = "query";

	private String prefix;

	private String textQueryField = DEFAULT_TEXT_QUERY_FIELD;
	private boolean parseQueryExpression = true;

	public AbstractSearchBuilderImpl(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFormat() {
		return this.prefix + "%s";
	}

	public String getTextQueryField() {
		return this.textQueryField;
	}

	public void setTextQueryField(String textQueryField) {
		this.textQueryField = textQueryField;
	}

	public boolean isParseQueryExpression() {
		return this.parseQueryExpression;
	}

	public void setParseQueryExpression(boolean parseQueryExpression) {
		this.parseQueryExpression = parseQueryExpression;
	}

	// MARK: Search
	public S make(Map<String, String> parameters) {
		return this.makeNewSearch(parameters);
	}

	protected abstract S makeNewSearch(Map<String, String> parameters);

	public final void applyParameters(S search,
	                            Map<String, String> parameters) {
		StringMapReader reader = new StringMapReader(parameters, this.getFormat());
		this.applyParameters(search, reader);
	};

	public final void applyParameters(S search,
	                                  StringMapReader reader) {
		this.applyParametersToSearch(search, reader);

		if (this.parseQueryExpression) {
			search.setQuery(reader.get(this.textQueryField));
		}
	}

	protected abstract void applyParametersToSearch(S search,
	                                                StringMapReader reader);

	// MARK: Expression Builder
	public final ExpressionBuilder make(S search) {
		return this.make(search, this.getFormat());
	}

	public final ExpressionBuilder make(S search,
	                                    String format) {
		ExpressionBuilder builder = new ExpressionStart();

		builder = this.buildExpression(search, format, builder);

		String query = search.getQuery();
		if (query != null) {
			builder = builder.and(new SimpleExpression(query));
		}

		return builder;
	}

	protected abstract ExpressionBuilder buildExpression(S search,
	                                                     String format,
	                                                     ExpressionBuilder builder);

}
