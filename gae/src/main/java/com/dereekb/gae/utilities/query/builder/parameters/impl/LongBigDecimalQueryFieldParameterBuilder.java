package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.math.BigDecimal;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.misc.numbers.LongBigDecimalUtility;
import com.dereekb.gae.utilities.misc.numbers.impl.LongBigDecimalUtilityImpl;

/**
 * Builder for {@link LongBigDecimalQueryFieldParameter} values.
 * <p>
 * Wraps a {@link LongBigDecimalUtility} used for conversions.
 * 
 * @author dereekb
 *
 */
public class LongBigDecimalQueryFieldParameterBuilder {

	public static final LongBigDecimalQueryFieldParameterBuilder THREE_PRECISION_BUILDER = new LongBigDecimalQueryFieldParameterBuilder();

	private LongBigDecimalUtility conversionUtility;

	public LongBigDecimalQueryFieldParameterBuilder() {
		this(LongBigDecimalUtilityImpl.THREE_PRECISION_UTILITY);
	}

	public LongBigDecimalQueryFieldParameterBuilder(Integer precision) {
		this(new LongBigDecimalUtilityImpl(precision));
	}

	public LongBigDecimalQueryFieldParameterBuilder(LongBigDecimalUtility conversionUtility) {
		this.setConversionUtility(conversionUtility);
	}

	public LongBigDecimalUtility getConversionUtility() {
		return this.conversionUtility;
	}

	public void setConversionUtility(LongBigDecimalUtility conversionUtility) {
		if (conversionUtility == null) {
			throw new IllegalArgumentException("conversionUtility cannot be null.");
		}

		this.conversionUtility = conversionUtility;
	}

	public LongBigDecimalQueryFieldParameter make(String field,
	                                              Number value)
	        throws IllegalArgumentException {
		LongBigDecimalQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = this.make(field, value.longValue());
		}

		return fieldParameter;
	}

	public LongBigDecimalQueryFieldParameter make(String field,
	                                              Long encodedValue)
	        throws IllegalArgumentException {
		LongBigDecimalQueryFieldParameter fieldParameter = null;

		if (encodedValue != null) {
			fieldParameter = new LongBigDecimalQueryFieldParameter(field, encodedValue);
		}

		return fieldParameter;
	}

	public LongBigDecimalQueryFieldParameter make(String field,
	                                              BigDecimal value)
	        throws IllegalArgumentException {
		LongBigDecimalQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = new LongBigDecimalQueryFieldParameter(field, value);
		}

		return fieldParameter;
	}

	public LongBigDecimalQueryFieldParameter make(String field,
	                                              String parameterString)
	        throws IllegalArgumentException {
		LongBigDecimalQueryFieldParameter fieldParameter = null;

		if (parameterString != null) {
			fieldParameter = new LongBigDecimalQueryFieldParameter(field, parameterString);
		}

		return fieldParameter;
	}

	public LongBigDecimalQueryFieldParameter make(String field,
	                                              LongBigDecimalQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		LongBigDecimalQueryFieldParameter fieldParameter = null;

		if (parameter != null) {
			fieldParameter = new LongBigDecimalQueryFieldParameter(field, parameter);
		}

		return fieldParameter;
	}

	public class LongBigDecimalQueryFieldParameter extends LongQueryFieldParameter {

		public LongBigDecimalQueryFieldParameter() {
			super();
		}

		public LongBigDecimalQueryFieldParameter(String field, ExpressionOperator operator, BigDecimal value) {
			this(field, operator, LongBigDecimalQueryFieldParameterBuilder.this.conversionUtility.fromDecimal(value));
		}

		public LongBigDecimalQueryFieldParameter(String field, BigDecimal value) {
			this(field, LongBigDecimalQueryFieldParameterBuilder.this.conversionUtility.fromDecimal(value));
		}
		
		public LongBigDecimalQueryFieldParameter(String field, ExpressionOperator operator, Long encodedValue) {
			super(field, operator, encodedValue);
		}

		public LongBigDecimalQueryFieldParameter(String field, Long encodedValue) {
			super(field, encodedValue);
		}

		public LongBigDecimalQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
			super(field, parameterString);
		}

		public LongBigDecimalQueryFieldParameter(AbstractQueryFieldParameter<Long> parameter)
		        throws IllegalArgumentException {
			super(parameter);
		}

		public LongBigDecimalQueryFieldParameter(String field, AbstractQueryFieldParameter<Long> parameter)
		        throws IllegalArgumentException {
			super(field, parameter);
		}

		// MARK: AbstractQueryFieldParameters
		@Override
		protected String getParameterValue() {
			return this.getValue().toString();
		}

		@Override
		protected void setParameterValue(String value) throws IllegalArgumentException {
			try {
				BigDecimal decimal = new BigDecimal(value);
				Long longValue = LongBigDecimalQueryFieldParameterBuilder.this.conversionUtility.fromDecimal(decimal);
				this.setValue(longValue);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}

	}

}
