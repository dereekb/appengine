package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Arrays;
import java.util.List;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.QueryParameter;
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

	public static final String DEFAULT_SPLITTER = ",";
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

	public QueryParameter decodeString(String parameterString) throws IllegalArgumentException {
		String[] split = parameterString.split(this.splitter);
		QueryParameter parameter;

		if (split.length == 1) {
			parameter = new ParameterImpl(parameterString);
		} else {
			Decoder decoder = new Decoder(split);
			parameter = decoder.decode();
		}

		return parameter;
	}

	public String encodeString(QueryParameter parameter) {
		String value = parameter.getValue();
		QueryResultsOrdering ordering = parameter.getOrdering();
		ExpressionOperator operator = parameter.getOperator();

		String[] components = new String[] { null, value, null };

		if (operator != null) {
			components[0] = operator.getValue();
		}

		if (ordering != null) {
			components[2] = ordering.getCode();
		}

		return Joiner.on(QueryFieldParameterDencoder.this.splitter).skipNulls().join(components);
	}

	private class Decoder {

		private final String[] split;

		private String value;
		private QueryResultsOrdering ordering;
		private ExpressionOperator operator;

		private Decoder(String[] split) {
			this.split = split;
		}

		public QueryParameter decode() {
			int valueStart = this.decodeOperator();
			int valueEnd = this.split.length;

			// Don't try to decode the ordering if there are only two elements
			// and the operator has been decoded.
			if (this.operator == null || this.split.length > 2) {
				valueEnd = this.decodeOrdering();
			}

			this.buildValue(valueStart, valueEnd);
			return new ParameterImpl(this.value, this.operator, this.ordering);
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

	public static final class ParameterImpl
	        implements QueryParameter {

		private String value;
		private ExpressionOperator operator;
		private QueryResultsOrdering ordering;

		public ParameterImpl(String value) {
			this(value, ExpressionOperator.EQUAL);
		}

		public ParameterImpl(String value, ExpressionOperator operator) {
			this(value, operator, null);
		}

		public ParameterImpl(String value, ExpressionOperator operator, QueryResultsOrdering ordering) {
			this.setValue(value);
			this.setOperator(operator);
			this.setOrdering(ordering);
		}

		@Override
		public String getValue() {
			return this.value;
		}

		public void setValue(String value) throws IllegalArgumentException {
			if (value == null) {
				throw new IllegalArgumentException("Parameter value cannot be null.");
			}

			this.value = value;
		}

		@Override
		public ExpressionOperator getOperator() {
			return this.operator;
		}

		public void setOperator(ExpressionOperator operator) {
			this.operator = operator;
		}

		@Override
		public QueryResultsOrdering getOrdering() {
			return this.ordering;
		}

		public void setOrdering(QueryResultsOrdering ordering) {
			this.ordering = ordering;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.operator == null) ? 0 : this.operator.hashCode());
			result = prime * result + ((this.ordering == null) ? 0 : this.ordering.hashCode());
			result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
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
			if (this.getClass() != obj.getClass()) {
				return false;
			}

			ParameterImpl other = (ParameterImpl) obj;
			if (this.operator != other.operator) {
				return false;
			}
			if (this.ordering != other.ordering) {
				return false;
			}
			if (this.value == null) {
				if (other.value != null) {
					return false;
				}
			} else if (!this.value.equals(other.value)) {
				return false;
			}
			return true;
		}

	}

}
