package com.dereekb.gae.utilities.model.attribute.impl;

import java.util.Date;

import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.model.attribute.ModelAttributeHelperUtilityInstance;
import com.dereekb.gae.utilities.model.attribute.ModelAttributeUpdateDelegate;
import com.dereekb.gae.utilities.model.attribute.ModelDateAttributeHelperUtility;
import com.dereekb.gae.utilities.time.DateUtility;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link ModelDateAttributeHelperUtility} implementation.
 *
 * @author dereekb
 *
 */
public class ModelDateAttributeHelperUtilityImpl extends AbstractModelAttributeHelperUtility<Date, ModelDateAttributeHelperUtilityImpl>
        implements ModelDateAttributeHelperUtility {

	public static final String NOT_IN_FUTURE_CODE_FORMAT = "%s_NOT_IN_FUTURE";

	public static final ModelDateAttributeHelperUtility SINGLETON = new ModelDateAttributeHelperUtilityImpl();

	private boolean assertFuture = false;
	private Long minimumFuture = 0L;
	private String futureCode = NOT_IN_FUTURE_CODE_FORMAT;

	public static ModelDateAttributeHelperUtilityImpl make() {
		return new ModelDateAttributeHelperUtilityImpl();
	}

	// MARK: Accessors
	public ModelDateAttributeHelperUtilityImpl future(Long minimum) {
		return this.future(true, minimum);
	}

	public ModelDateAttributeHelperUtilityImpl future(boolean future,
	                                                     Long minimum) {
		return this.future(future, minimum, null);
	}

	/**
	 * Asserts the value is future by atleast the specified amount.
	 *
	 * @param future
	 *            future.
	 * @param minimum
	 *            minimum amount in the future in ms.
	 * @param code
	 *            optional code to add to the invalid attribute.
	 * @return value. Never {@code null}.
	 */
	public ModelDateAttributeHelperUtilityImpl future(boolean future,
	                                                     Long minimum,
	                                                     String code) {
		if (minimum <= 0) {
			throw new IllegalArgumentException("Minimum must be greater than 0.");
		}

		this.assertFuture = future;
		this.minimumFuture = minimum;
		this.futureCode = ValueUtility.defaultTo(code, NOT_IN_FUTURE_CODE_FORMAT);
		return this.getThis();
	}

	// MARK: ModelAttributeHelperUtility
	@Override
	public Date getDecodedValue(Date value) {
		if (value.getTime() == 0L) {
			return null;
		} else {
			return value;
		}
	}

	@Override
	public ModelAttributeHelperUtilityInstance<Date> makeInstance(String attribute,
	                                                              ModelAttributeUpdateDelegate<Date> delegate) {
		return new DateAttributeInstance(attribute, delegate);
	}

	protected class DateAttributeInstance extends AttributeInstance<DateAttributeInstance> {

		public DateAttributeInstance(String attribute, ModelAttributeUpdateDelegate<Date> delegate) {
			super(attribute, delegate);
		}

		@Override
		public void assertValidDecodedValue(Date value) throws InvalidAttributeException {
			super.assertValidDecodedValue(value);

			// Check is in future.
			if (ModelDateAttributeHelperUtilityImpl.this.assertFuture && DateUtility.dateIsInTheFutureAtleast(value,
			        ModelDateAttributeHelperUtilityImpl.this.minimumFuture)) {
				this.throwInvalidAttributeException(null, "Value must be a time in the future.",
				        ModelDateAttributeHelperUtilityImpl.this.futureCode);
			}
		}

	}

}
