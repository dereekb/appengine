package com.dereekb.gae.model.extension.search.document.index.component.builder.impl.derivative;

/**
 * Used for reading the derivative model from the input type.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface DerivativeDocumentBuilderDelegate<T> {

	/**
	 * Reads the derivative type.
	 *
	 * @param model
	 * @return A string value carrying the type. Used as a key in
	 *         {@link DerivativeDocumentBuilder}.
	 *
	 * @throws NoDerivativeTypeException
	 *             Thrown if no type is set.
	 */
	public String readDerivativeType(T model) throws NoDerivativeTypeException;

	/**
	 * Returns the derivative identifier.
	 *
	 * @param model
	 * @return
	 */
	public String readDerivativeIdentifier(T model);

}
