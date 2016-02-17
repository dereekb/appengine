package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import java.util.Arrays;
import java.util.List;

import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryConditionOperator;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryResultsOrdering;
import com.google.common.base.Joiner;

/**
 * Used for encoding and decoding a string for a {@link AbstractQueryFieldParameter}.
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

	public Parameter decodeString(String parameterString) throws IllegalArgumentException {
		String[] split = parameterString.split(this.splitter);
		Parameter parameter;

		if (split.length == 1) {
			parameter = new Parameter(parameterString);
		} else {
			Decoder decoder = new Decoder(split);
			parameter = decoder.decode();
		}

		return parameter;
	}

	public String encodeString(Parameter parameter) {
		String value = parameter.getValue();
		ObjectifyQueryResultsOrdering ordering = parameter.getOrdering();
		ObjectifyQueryConditionOperator operator = parameter.getOperator();

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
		private ObjectifyQueryResultsOrdering ordering;
		private ObjectifyQueryConditionOperator operator;

		private Decoder(String[] split) {
			this.split = split;
		}

		public Parameter decode() {
			int valueStart = this.decodeOperator();
			int valueEnd = this.split.length - 1;

			// Don't try to decode the ordering if there are only two elements
			// and the operator has been decoded.
			if (this.operator == null || this.split.length > 2) {
				valueEnd = this.decodeOrdering();
			}

			this.buildValue(valueStart, valueEnd);
			return new Parameter(this.value, this.operator, this.ordering);
		}

		private void buildValue(int start,
		                        int end) {
			List<String> components = Arrays.asList(this.split).subList(start, end);
			this.value = Joiner.on(QueryFieldParameterDencoder.this.splitter).join(components);
		}

		private int decodeOperator() {
			int valueStart;

			try {
				this.operator = ObjectifyQueryConditionOperator.fromString(this.split[0]);
				valueStart = 1;
			} catch (IllegalArgumentException e) {
				valueStart = 0; // Was not a condition.
			}

			return valueStart;
		}

		private int decodeOrdering() {
			int valueEnd;

			try {
				this.ordering = ObjectifyQueryResultsOrdering.fromString(this.split[this.split.length - 1]);
				valueEnd = this.split.length - 1;
			} catch (IllegalArgumentException e) {
				valueEnd = this.split.length;
			}

			return valueEnd;
		}

	}

	public static final class Parameter {

		private String value;
		private ObjectifyQueryConditionOperator operator;
		private ObjectifyQueryResultsOrdering ordering;

		public Parameter(String value) {
			this(value, ObjectifyQueryConditionOperator.Equal);
		}

		public Parameter(String value, ObjectifyQueryConditionOperator operator) {
			this(value, operator, null);
		}

		public Parameter(String value, ObjectifyQueryConditionOperator operator, ObjectifyQueryResultsOrdering ordering) {
			this.setValue(value);
			this.setOperator(operator);
			this.setOrdering(ordering);
		}

		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public ObjectifyQueryConditionOperator getOperator() {
			return this.operator;
		}

		public void setOperator(ObjectifyQueryConditionOperator operator) {
			this.operator = operator;
		}

		public ObjectifyQueryResultsOrdering getOrdering() {
			return this.ordering;
		}

		public void setOrdering(ObjectifyQueryResultsOrdering ordering) {
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
			Parameter other = (Parameter) obj;
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
