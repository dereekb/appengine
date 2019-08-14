package com.dereekb.gae.server.search.query.utility;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;

/**
 * Provides utility functions for working with {@link SearchExpressionBuilder} values.
 *
 * @author dereekb
 *
 */
public class SearchExpressionBuilderUtility {

	/**
	 * Does an AND merge between the inputs, unless either is {@code null} and attempts to return the non-null value.
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static SearchExpressionBuilder and(SearchExpressionBuilder a, SearchExpressionBuilder b) {
		if (a != null && b != null) {
			return a.and(b);
		} else if (a != null) {
			return a;
		} else {
			return b;
		}
	}

	/**
	 * Does an OR merge between the inputs, unless either is {@code null} and attempts to return the non-null value.
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static SearchExpressionBuilder or(SearchExpressionBuilder a, SearchExpressionBuilder b) {
		if (a != null && b != null) {
			return a.or(b);
		} else if (a != null) {
			return a;
		} else {
			return b;
		}
	}

}
