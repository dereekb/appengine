package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Arrays;
import java.util.List;

import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameter;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedValueExpressionOperatorPair;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;
import com.google.common.base.Joiner;

/**
 * Used for encoding and decoding a string for a
 * {@link AbstractQueryFieldParameter}.
 *
 * @author dereekb
 *
 */
public class QueryFieldParameterDencoder {

	public static final String NULL_OP_VALUE_PLACEHOLDER = "null";

	public static final String DEFAULT_SPLITTER = ",";
	public static final String SECOND_FILTER_SPLITTER = "~~";
	public static final QueryFieldParameterDencoder SINGLETON = new QueryFieldParameterDencoder();

	private String splitter;

	public QueryFieldParameterDencoder() {
		this.setSplitter(DEFAULT_SPLITTER);
	}

	public QueryFieldParameterDencoder(String splitter) throws IllegalArgumentException {
		this.setSplitter(splitter);
	}

	public String getSplitter() {
		return this.splitter;
	}

	public void setSplitter(String splitter) throws IllegalArgumentException {
		if (splitter == null) {
			throw new IllegalArgumentException("Splitter cannot be null.");
		}

		this.splitter = splitter;
	}

	public EncodedQueryParameter decodeString(String parameterString) throws IllegalArgumentException {
		String[] expressionSplit = parameterString.split(SECOND_FILTER_SPLITTER);
		EncodedQueryParameterImpl parameter = this.decodeParameterString(expressionSplit[0]);
		
		if (expressionSplit.length > 2) {
			throw new IllegalArgumentException("Invalid query parameter. Too many splits detected.");
		} else if (expressionSplit.length == 2) {
			EncodedQueryParameterImpl secondParameter = this.decodeParameterString(expressionSplit[1]);
			parameter.setSecondFilter(secondParameter);
		}
		
		return parameter;
	}
	
	protected EncodedQueryParameterImpl decodeParameterString(String parameterString) {
		String[] split = parameterString.split(this.splitter);
		EncodedQueryParameterImpl parameter;

		if (split.length == 1) {
			parameter = new EncodedQueryParameterImpl(parameterString);
		} else {
			Decoder decoder = new Decoder(split);
			parameter = decoder.decode();
		}

		return parameter;
	}

	public String encodeString(EncodedQueryParameter parameter) {
		String[] components = new String[] { null, null };

		components[0] = this.encodeParameterString(parameter);

		if (parameter.getOperator() != null) {
			EncodedValueExpressionOperatorPair pair = parameter.getSecondFilter();
			
			if (pair != null) {
				components[1] = this.encodeValueExpressionParameterString(pair);
			}
		}

		return Joiner.on(SECOND_FILTER_SPLITTER).skipNulls().join(components);
	}

	public String encodeParameterString(EncodedQueryParameter parameter) {
		QueryResultsOrdering ordering = parameter.getOrdering();

		String[] components = new String[] { null, null };

		String primary = this.encodeValueExpressionParameterString(parameter);
		
		if (primary != null) {
			components[0] = primary;
		}
		
		if (ordering != null) {
			components[1] = ordering.getCode();
		}

		return Joiner.on(QueryFieldParameterDencoder.this.splitter).skipNulls().join(components);
	}

	public String encodeValueExpressionParameterString(EncodedValueExpressionOperatorPair parameter) {
		String value = parameter.getValue();
		ExpressionOperator operator = parameter.getOperator();

		String[] components = new String[] { null, null };

		if (operator != null) {
			components[0] = operator.getValue();
			components[1] = this.valueForExpressionOperator(value, operator);
			return Joiner.on(QueryFieldParameterDencoder.this.splitter).skipNulls().join(components);
		} else {
			return null;
		}
	}

	protected String valueForExpressionOperator(String value,
	                                            ExpressionOperator operator) {
		if (operator == ExpressionOperator.IS_NULL) {
			return NULL_OP_VALUE_PLACEHOLDER;
		} else {
			return value;
		}
	}
	
	private class Decoder {

		private final String[] split;

		private String value;
		private QueryResultsOrdering ordering;
		private ExpressionOperator operator;

		private Decoder(String[] split) {
			this.split = split;
		}

		public EncodedQueryParameterImpl decode() {
			int valueStart = this.decodeOperator();
			int valueEnd = this.split.length;

			// Don't try to decode the ordering if there are only two elements
			// and the operator has been decoded.
			if (this.operator == null || this.split.length > 2) {
				valueEnd = this.decodeOrdering();
			}

			this.buildValue(valueStart, valueEnd);
			return new EncodedQueryParameterImpl(this.value, this.operator, this.ordering);
		}

		private void buildValue(int start,
		                        int end) {
			List<String> components = Arrays.asList(this.split);
			components = components.subList(start, end);
			this.value = Joiner.on(QueryFieldParameterDencoder.this.splitter).join(components);
		}

		private int decodeOperator() {
			int valueStart;

			try {
				this.operator = ExpressionOperator.fromString(this.split[0]);
				valueStart = 1;
			} catch (IllegalArgumentException e) {
				valueStart = 0; // Was not a condition.
			}

			return valueStart;
		}

		private int decodeOrdering() {
			int valueEnd;

			try {
				this.ordering = QueryResultsOrdering.fromString(this.split[this.split.length - 1]);
				valueEnd = this.split.length - 1;
			} catch (IllegalArgumentException e) {
				valueEnd = this.split.length;
			}

			return valueEnd;
		}

	}

	public static final class EncodedQueryParameterImpl extends EncodedValueExpressionOperatorPairImpl
	        implements EncodedQueryParameter {

		private QueryResultsOrdering ordering;

		private EncodedValueExpressionOperatorPair secondFilter;

		public EncodedQueryParameterImpl(String value) {
			this(value, ExpressionOperator.EQUAL);
		}

		public EncodedQueryParameterImpl(String value, ExpressionOperator operator) {
			this(value, operator, null);
		}

		public EncodedQueryParameterImpl(String value, ExpressionOperator operator, QueryResultsOrdering ordering) {
			this(value, operator, ordering, null, null);
		}

		public EncodedQueryParameterImpl(String value,
		        ExpressionOperator operator,
		        QueryResultsOrdering ordering,
		        String secondValue,
		        ExpressionOperator secondOperator) {
			this(value, operator, ordering,
			        EncodedValueExpressionOperatorPairImpl.tryMake(secondValue, secondOperator));
		}

		public EncodedQueryParameterImpl(String value,
		        ExpressionOperator operator,
		        QueryResultsOrdering ordering,
		        EncodedValueExpressionOperatorPair secondFilter) {
			super(value, operator);
			this.setOrdering(ordering);
			this.setSecondFilter(secondFilter);
		}

		@Override
		public QueryResultsOrdering getOrdering() {
			return this.ordering;
		}

		public void setOrdering(QueryResultsOrdering ordering) {
			this.ordering = ordering;
		}

		@Override
		public EncodedValueExpressionOperatorPair getSecondFilter() {
			return this.secondFilter;
		}

		public void setSecondFilter(EncodedValueExpressionOperatorPair secondFilter) {
			this.secondFilter = EncodedValueExpressionOperatorPairImpl.tryCopy(secondFilter);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.ordering == null) ? 0 : this.ordering.hashCode());
			result = prime * result + ((this.secondFilter == null) ? 0 : this.secondFilter.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (EncodedQueryParameter.class.isAssignableFrom(obj.getClass()) == false) {
				return false;
			}
			
			EncodedQueryParameter other = (EncodedQueryParameter) obj;
			if (this.ordering != other.getOrdering()) {
				return false;
			}
			if (this.secondFilter == null) {
				if (other.getSecondFilter() != null) {
					return false;
				}
			} else if (!this.secondFilter.equals(other.getSecondFilter())) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return "EncodedQueryParameterImpl [ordering=" + this.ordering + ", secondFilter=" + this.secondFilter
			        + ", value=" + this.value + ", operator=" + this.operator + "]";
		}

	}

}
