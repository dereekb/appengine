package com.dereekb.gae.model.extension.links;

/**
 * Interface for models that are linked to a model that further describes them.
 *
 * Models don't always have information model links immediately, so the values
 * can be null.
 *
 * Identifiers are always stored as strings.
 *
 * @author dereekb
 */
public interface DescriptivelyLinkedModel {

	public static final String DEFAULT_LINK_TYPE = "INFO_LINK";

	/**
	 * Returns the type that this element describes.
	 *
	 * @return Linked info type, or null if none.
	 */
	public String getInfoType();

	/**
	 * Sets the info type.
	 *
	 * @param type
	 */
	public void setInfoType(String type);

	/**
	 * @return string representation of the identifier.
	 */
	public String getInfoIdentifier();

	/**
	 * Sets the info's identifier.
	 *
	 * @param identifier
	 */
	public void setInfoIdentifier(String identifier);

}
