package com.dereekb.gae.server.datastore.models.keys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringLongModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;

/**
 * Represents a key for a model.
 *
 * They key has either a name or number identifier.
 *
 * @author dereekb
 */
public final class ModelKey
        implements UniqueModel {

	public static final Long DEFAULT_KEY = 0L;

	private final int hashCode;
	private final ModelKeyType type;

	private final String name;
	private final Long id;

	@Deprecated
	public ModelKey() {
		this.id = DEFAULT_KEY;
		this.name = null;
		this.hashCode = 0;
		this.type = ModelKeyType.DEFAULT;
	}

	public ModelKey(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Invalid name key. Must be non-null and not empty.");
		}

		this.name = name;
		this.id = null;
		this.hashCode = name.hashCode();
		this.type = ModelKeyType.NAME;
	}

	public ModelKey(Long id) throws IllegalArgumentException {
		if (id == null || id < 0) {
			throw new IllegalArgumentException("Invalid number key. Must be non-null and greater than 0.");
		}

		this.id = id;
		this.name = null;
		this.hashCode = id.hashCode();
		this.type = ModelKeyType.NUMBER;
	}

	public ModelKeyType getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public Long getId() {
		return this.id;
	}

	public boolean isSameType(ModelKey key) {
		return key.type == this.type;
	}

	public boolean isDefaultKey(ModelKey key) {
		return key.type == ModelKeyType.DEFAULT;
	}

	@Override
	public ModelKey getModelKey() {
		return this;
	}

	/**
	 * @return String representation of the key value.
	 */
	public String keyAsString() {
		String string;

		if (this.type == ModelKeyType.NAME) {
			string = this.name;
		} else {
			string = this.id.toString();
		}

		return string;
	}

	@Override
	public String toString() {
		return "ModelKey [ key= " + this.keyAsString() + "]";
	}

	@Override
	public int hashCode() {
		return this.hashCode;
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
		ModelKey other = (ModelKey) obj;
		if (this.isSameType(other)) {
			if (this.type == ModelKeyType.NAME) {
				return this.name.equals(other.name);
			} else {
				return this.id.equals(other.id);
			}
		} else {
			return false;
		}
	}

	public static List<ModelKey> readModelKeys(Iterable<? extends UniqueModel> models) {
		List<ModelKey> values = new ArrayList<ModelKey>();

		if (models != null) {
			for (UniqueModel model : models) {
				ModelKey key = model.getModelKey();

				if (key != null) {
					values.add(key);
				}
			}
		}

		return values;
	}

	public static List<String> readStringKeys(Iterable<? extends UniqueModel> models) {
		List<String> ids = new ArrayList<String>();

		if (models != null) {
			for (UniqueModel model : models) {
				ModelKey key = model.getModelKey();

				if (key != null) {
					String id = key.keyAsString();
					ids.add(id);
				}
			}
		}

		return ids;
	}

	public static List<String> keysAsStrings(Iterable<ModelKey> keys) {
		List<String> ids = new ArrayList<String>();

		if (keys != null) {
			for (ModelKey key : keys) {
				String id = key.keyAsString();
				ids.add(id);
			}
		}

		return ids;
	}

	/**
	 * Attempts to create a new {@link ModelKey} using the passed value. Returns
	 * null instead of throws an exception.
	 *
	 * @param identifier
	 * @return A {@link ModelKey} if the identifier is valid. Null if it is not.
	 */
	public static ModelKey safe(Long identifier) {
		try {
			return new ModelKey(identifier);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Attempts to create a new {@link ModelKey} using the passed value. Returns
	 * null instead of throws an exception.
	 *
	 * @param name
	 * @return A {@link ModelKey} if the name is valid. Null if it is not.
	 */
	public static ModelKey safe(String name) {
		try {
			return new ModelKey(name);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public static Long readIdentifier(ModelKey key) {
		Long id = null;

		if (key != null) {
			id = key.getId();
		}

		return id;
	}

	public static String readName(ModelKey key) {
		String name = null;

		if (key != null) {
			name = key.getName();
		}

		return name;
	}

	public static String readStringKey(ModelKey key) {
		String keyString = null;

		if (key != null) {
			keyString = key.keyAsString();
		}

		return keyString;
	}

	/**
	 * Same as {@link #convert(String)}, except with the {@link ModelKeyType}
	 * specified.
	 *
	 * Will return null if the input identifier is null or empty, or an invalid
	 * number if the {@code keyType} is set to {@link ModelKeyType#NUMBER} and
	 * is not a valid number.
	 *
	 * @param keyType
	 *            Target key type.
	 * @param identifier
	 *            String identifier
	 * @return {@link ModelKey} instance if valid, or {@code null}.
	 */
	public static ModelKey convert(ModelKeyType keyType,
	                               String identifier) {
		ModelKey modelKey = null;

		if (identifier != null && identifier.isEmpty() == false) {
			switch (keyType) {
				case NAME:
					modelKey = new ModelKey(identifier);
					break;
				case NUMBER:
					Long id = new Long(identifier);
					modelKey = new ModelKey(id);
					break;
				default:
					break;
			}
		}

		return modelKey;
	}

	/**
	 * Convenience function for converting
	 *
	 * @param keyType
	 *            {@link ModelKeyType} of values.
	 * @param values
	 *            {@link Collection} of values. Never {@code null}.
	 * @return {@link List} of {@link ModelKey} values corresponding to the
	 *         input types. Never {@code null}.
	 * @throws ConversionFailureException
	 *             if the conversion fails.
	 */
	public static List<ModelKey> convert(ModelKeyType keyType,
	                                     Collection<String> values) throws ConversionFailureException {
		List<ModelKey> keys;

		switch (keyType) {
			case NAME:
				keys = StringModelKeyConverter.CONVERTER.convert(values);
				break;
			case NUMBER:
				keys = StringLongModelKeyConverter.CONVERTER.convert(values);
				break;
			default:
				throw new IllegalArgumentException("Invalid key type specified.");
		}

		return keys;
	}

	/**
	 * Converts a number as a string to a {@link ModelKey}. If the string is
	 * {@code null}, will return {@code null}.
	 *
	 * @param numberString
	 *            String number to convert.
	 * @return {@link ModelKey} with a Number identifier.
	 * @throws NumberFormatException
	 *             if the input string is not {@code null} but cannot be
	 *             converted with {@link Long#Long(String)}.
	 */
	public static ModelKey convertNumberString(String numberString) throws NumberFormatException {
		ModelKey key = null;

		if (numberString != null) {
			Long id = new Long(numberString);
			key = new ModelKey(id);
		}

		return key;
	}

	/**
	 * Attempts to convert the identifier.
	 *
	 * Tries first to convert to a number id, then to a string id.
	 *
	 * The system should instead use a {@link DirectionalConverter} instead of
	 * this method where possible.
	 *
	 * @param identifier
	 *            Identifier to convert. Can be null.
	 * @return A {@link ModelKey} instance with the input key. Contains a number
	 *         id if applicable, or a String id if not null. If the input is
	 *         null, this will return null.
	 */
	public static ModelKey convert(String identifier) {
		ModelKey modelKey = null;

		if (identifier != null && identifier.isEmpty() == false) {
			try {
				Long id = new Long(identifier);
				modelKey = new ModelKey(id);
			} catch (NumberFormatException e) {
				modelKey = new ModelKey(identifier);
			}
		}

		return modelKey;
	}

	/**
	 * Attempts to convert the input identifiers.
	 *
	 * Tries first to convert to a number id, then to a string id.
	 *
	 * The system should instead use a {@link DirectionalConverter} instead of
	 * this method where possible, as it calls {@link #convert(String)} for each
	 * element.
	 *
	 * @param identifiers
	 *            Identifiers to convert. Neither it or any of it's identifiers
	 *            can be null.
	 * @return A collection of {@link ModelKey} instance with the input key.
	 *         Contains a number id if applicable, or a String id if not null.
	 * @throws IllegalArgumentException
	 *             if any of the input identifiers cannot be converted.
	 * @Deprecated Is dangerous to use. Rely on a specific
	 *             {@link DirectionalConverter} instead.
	 */
	@Deprecated
	public static List<ModelKey> convert(List<String> identifiers) throws IllegalArgumentException {
		List<ModelKey> keys = new ArrayList<ModelKey>();

		for (String identifier : identifiers) {
			ModelKey key = convert(identifier);

			if (key == null) {
				throw new IllegalArgumentException("Failed to convert value '" + identifier + "'.");
			}

			keys.add(key);
		}

		return keys;
	}

}
