package com.dereekb.gae.server.search.query.builder.fields;

import com.dereekb.gae.server.search.query.expression.SearchField;
import com.google.appengine.api.search.Field.FieldType;

/**
 * Google App Engine Search Service query field.
 *
 * @author dereekb
 * @deprecated Use {@link SearchField} instead.
 */
@Deprecated
public interface SearchQueryField {

	/**
	 * @return {@code true} if not.
	 */
	public boolean isNot();

	/**
	 * Returns the field type.
	 *
	 * @return {@link FieldType}. Never {@code null}.
	 */
	public FieldType getType();

	/**
	 * Whether or not this field is considered a complex field.
	 *
	 * @return {@code true} if complex.
	 */
	public boolean isComplex();

}
