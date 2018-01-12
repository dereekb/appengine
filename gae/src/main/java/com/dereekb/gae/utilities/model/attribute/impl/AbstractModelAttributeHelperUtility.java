package com.dereekb.gae.utilities.model.attribute.impl;

import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.model.attribute.ModelAttributeHelperUtility;
import com.dereekb.gae.utilities.model.attribute.ModelAttributeHelperUtilityInstance;
import com.dereekb.gae.utilities.model.attribute.ModelAttributeUpdateDelegate;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link ModelAttributeHelperUtility} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractModelAttributeHelperUtility<T, X extends AbstractModelAttributeHelperUtility<T, X>>
        implements ModelAttributeHelperUtility<T> {

	public static final String REQUIRED_CODE_FORMAT = "%s_REQUIRED";

	private boolean assertNotNull = false;
	private String requiredCode = REQUIRED_CODE_FORMAT;

	// MARK: Accessors
	public X required() {
		return this.required(true);
	}

	public X required(boolean required) {
		return this.required(required, null);
	}

	/**
	 * Asserts the value is required and cannot be null.
	 *
	 * @param required
	 *            required.
	 * @param code
	 *            optional code to add to the invalid attribute.
	 * @return value. Never {@code null}.
	 */
	public X required(boolean required,
	                  String code) {
		this.assertNotNull = required;
		this.requiredCode = ValueUtility.defaultTo(code, REQUIRED_CODE_FORMAT);
		return this.getThis();
	}

	// MARK: ModelAttributeHelperUtility
	@Override
	public boolean isChangeValue(T value) {
		return value != null;
	}

	@Override
	public abstract T getDecodedValue(T value);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ModelAttributeHelperUtilityInstance<T> makeInstance(String attribute,
	                                                           ModelAttributeUpdateDelegate<T> delegate) {
		return new AttributeInstance(attribute, delegate);
	}

	// MARK: Internal
	@SuppressWarnings("unchecked")
	protected X getThis() {
		return (X) this;
	}

	// MARK: Instance
	protected class AttributeInstance<Y extends AttributeInstance<Y>>
	        implements ModelAttributeHelperUtilityInstance<T> {

		private String attribute;
		private ModelAttributeUpdateDelegate<T> delegate;
		private boolean initial = false;

		public AttributeInstance(String attribute, ModelAttributeUpdateDelegate<T> delegate) {
			this.setAttribute(attribute);
			this.setDelegate(delegate);
		}

		@Override
		public String getAttribute() {
			return this.attribute;
		}

		public void setAttribute(String attribute) {
			if (attribute == null) {
				throw new IllegalArgumentException("attribute cannot be null.");
			}

			this.attribute = attribute;
		}

		public ModelAttributeUpdateDelegate<T> getDelegate() {
			return this.delegate;
		}

		public void setDelegate(ModelAttributeUpdateDelegate<T> delegate) {
			if (delegate == null) {
				throw new IllegalArgumentException("delegate cannot be null.");
			}

			this.delegate = delegate;
		}

		public Y initial() {
			return this.initial(true);
		}

		public Y initial(boolean initial) {
			this.initial = initial;
			return this.getThis();
		}

		// MARK: Internal
		@SuppressWarnings("unchecked")
		protected Y getThis() {
			return (Y) this;
		}

		// MARK: ModelAttributeHelperUtility
		@Override
		public boolean isChangeValue(T value) {
			return AbstractModelAttributeHelperUtility.this.isChangeValue(value);
		}

		@Override
		public T getDecodedValue(T value) {
			return AbstractModelAttributeHelperUtility.this.getDecodedValue(value);
		}

		@Override
		public ModelAttributeHelperUtilityInstance<T> makeInstance(String attribute,
		                                                           ModelAttributeUpdateDelegate<T> delegate) {
			return AbstractModelAttributeHelperUtility.this.makeInstance(attribute, delegate);
		}

		// MARK: ModelAttributeHelperUtilityInstance
		@Override
		public void tryUpdateValue(T input) throws InvalidAttributeException {
			if (this.isChangeValue(input)) {
				T value = this.getDecodedValue(input);
				this.assertValidDecodedValue(value);
				this.delegate.modifyValue(value);
			}
		}

		@Override
		public void assertValidDecodedValue(T value) throws InvalidAttributeException {

			// Only check for null by default.
			if (AbstractModelAttributeHelperUtility.this.assertNotNull || this.initial) {
				this.assertNotNull(value);
			}
		}

		protected void assertNotNull(T value) {
			if (value == null) {
				this.throwInvalidAttributeException(null, "A value is required. Cannot be null.",
				        AbstractModelAttributeHelperUtility.this.requiredCode);
			}
		}

		// MARK: Utility
		public void throwInvalidAttributeException(T value,
		                                           String reason,
		                                           String codeFormat)
		        throws InvalidAttributeException {
			throw this.makeInvalidAttributeException(value, reason, codeFormat);
		}

		public InvalidAttributeException makeInvalidAttributeException(T value,
		                                                               String reason,
		                                                               String codeFormat) {
			String attribute = this.getAttribute();
			String code = String.format(codeFormat, attribute).toUpperCase();
			return new InvalidAttributeException(attribute, value, reason, code);
		}

	}

}
