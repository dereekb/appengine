package com.dereekb.gae.server.datastore.models.keys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.exception.NullModelKeyException;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.pairs.HandlerPair;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.google.common.base.Joiner;

/**
 * Represents a key for a model.
 * <p>
 * They key has either a name or number identifier. Keys with a
 * {@link ModelKeyType#NUMBER} identifier will match a key with a
 * {@link ModelKeyType#NAME} identifier that has the same string.
 *
 * @author dereekb
 */
@JsonIgnoreType
public final class ModelKey
        implements UniqueModel {

	public static final Long DEFAULT_KEY = 0L;

	private final int hashCode;
	private final ModelKeyType type;

	private final String name;
	private final Long id;

	public ModelKey(Integer id) throws IllegalArgumentException {
		this((id != null) ? new Long(id) : null);
	}

	public ModelKey(Long id) throws IllegalArgumentException {
		if (id == null || id < 0) {
			throw new IllegalArgumentException("Invalid number key '" + id + "'. Must be non-null and greater than 0.");
		}

		this.id = id;
		this.name = id.toString();
		this.hashCode = this.name.hashCode();
		this.type = ModelKeyType.NUMBER;
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

	/**
	 * @see {@link #keyAsString()} for getting the key's string value.
	 */
	@Override
	public String toString() {
		return this.keyAsString();
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
		return this.keyAsString().equals(other.keyAsString());
	}

	public static List<ModelKey> readModelKeys(Iterable<? extends UniqueModel> models) {
		List<ModelKey> values = new ArrayList<>();

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
		List<String> ids = new ArrayList<>();

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
		List<String> ids = new ArrayList<>();

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

	private static final String SPLITTER = ",";

	public static String keysAsString(Set<ModelKey> keys) {
		return keysAsString(keys, SPLITTER);
	}

	public static String keysAsString(Set<ModelKey> keys,
	                                  String splitter) {
		Joiner joiner = Joiner.on(splitter).skipNulls();
		return joiner.join(keys);
	}

	public static List<ModelKey> convertKeysInString(ModelKeyType keyType,
	                                                 String stringKey) {
		return convertKeysInString(keyType, stringKey, SPLITTER);
	}

	public static List<ModelKey> convertKeysInString(ModelKeyType keyType,
	                                                 String keys,
	                                                 String splitter)
	        throws IllegalArgumentException {
		String[] values = keys.split(splitter);
		List<String> valuesList = ListUtility.toList(values);
		return convert(keyType, valuesList);
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
	                                     Collection<String> values)
	        throws ConversionFailureException {
		List<ModelKey> keys;

		switch (keyType) {
			case NAME:
				keys = StringModelKeyConverterImpl.CONVERTER.convert(values);
				break;
			case NUMBER:
				keys = StringLongModelKeyConverterImpl.CONVERTER.convert(values);
				break;
			default:
				throw new IllegalArgumentException("Invalid key type specified.");
		}

		return keys;
	}

	/**
	 * Converts a string number with radix 10 to a {@link ModelKey}. If the
	 * string is
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
		return convertNumberString(numberString, 10);
	}

	/**
	 * Converts a number as a string to a {@link ModelKey}. If the string is
	 * {@code null}, will return {@code null}.
	 *
	 * @param numberString
	 *            String number to convert.
	 * @param radix
	 *            Number radix.
	 * @return {@link ModelKey} with a Number identifier.
	 * @throws NumberFormatException
	 *             if the input string is not {@code null} but cannot be
	 *             converted with {@link Long#Long(String)}.
	 */
	public static ModelKey convertNumberString(String numberString,
	                                           int radix)
	        throws NumberFormatException {
		ModelKey key = null;

		if (numberString != null) {
			Long id = Long.parseLong(numberString, radix);
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
	 * @return A {@link ModelKey} instance with the input key. Contains a
	 *         {@link ModelKeyType#NUMBER} id if applicable, or a
	 *         {@link ModelKeyType#NAME} id if not {@code null}. If the input is
	 *         null, this will return {@code null}.
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
		List<ModelKey> keys = new ArrayList<>();

		for (String identifier : identifiers) {
			ModelKey key = convert(identifier);

			if (key == null) {
				throw new IllegalArgumentException("Failed to convert value '" + identifier + "'.");
			}

			keys.add(key);
		}

		return keys;
	}

	/**
	 * Checks if all models have a valid {@link ModelKey} attached.
	 *
	 * @param models
	 * @return {@code true} if all models have a key set.
	 */
	public static boolean allModelsHaveKeys(Iterable<? extends UniqueModel> models) {
		boolean valid = true;

		for (UniqueModel model : models) {
			ModelKey key = model.getModelKey();

			if (key == null) {
				valid = false;
			}
		}

		return valid;
	}

	/**
	 * Matches "right" models to "left" models.
	 *
	 * @param leftModels
	 *            Models to map right pairs to. The left model may appear
	 *            multiple times in result pairs.
	 * @param rightModels
	 *            Models to map to the left. Will only appear once.
	 * @return {@link List} of {@link HandlerPair} values.
	 * @throws NullModelKeyException
	 *             Thrown if any left model has no model key.
	 */
	public static <L extends UniqueModel, R extends UniqueModel> List<HandlerPair<L, R>> makePairs(Iterable<? extends L> leftModels,
	                                                                                               Iterable<? extends R> rightModels)
	        throws NullModelKeyException {

		List<HandlerPair<L, R>> pairs = new ArrayList<>();
		Map<ModelKey, L> leftMap = makeModelKeyMap(leftModels);

		for (R right : rightModels) {
			ModelKey key = right.getModelKey();
			L left = leftMap.get(key);

			if (left != null) {
				pairs.add(new HandlerPair<>(left, right));
			}
		}

		return pairs;
	}

	/**
	 * Creates a map of models keyed by their {@link ModelKey} value.
	 *
	 * @param models
	 *            {@link Iterable} collection. Never {@code null}.
	 * @return {@link Map}. Never {@code null}.
	 * @throws NullModelKeyException
	 *             Thrown if a model does not have a key.
	 */
	public static <T extends UniqueModel> Map<ModelKey, T> makeModelKeyMap(Iterable<? extends T> models)
	        throws NullModelKeyException {
		Map<ModelKey, T> modelKeyMap = new HashMap<>();

		for (T model : models) {
			ModelKey key = model.getModelKey();

			if (key == null) {
				throw new NullModelKeyException();
			} else {
				modelKeyMap.put(key, model);
			}
		}

		return modelKeyMap;
	}

	/**
	 * Creates a map of model keys for the input models.
	 *
	 * @param models
	 *            {@link Iterable} collection. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 * @throws NullModelKeyException
	 *             Thrown if a model does not have a key.
	 */
	public static <T extends UniqueModel> Set<ModelKey> makeModelKeySet(Iterable<? extends T> models)
	        throws NullModelKeyException {
		Set<ModelKey> keys = new HashSet<>();

		for (T model : models) {
			ModelKey key = model.getModelKey();

			if (key == null) {
				throw new NullModelKeyException();
			} else {
				keys.add(key);
			}
		}

		return keys;
	}

}
