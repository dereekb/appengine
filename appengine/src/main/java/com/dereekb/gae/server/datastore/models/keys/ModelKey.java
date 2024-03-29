package com.dereekb.gae.server.datastore.models.keys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.exception.ConversionFailureException;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
import com.dereekb.gae.model.extension.generation.impl.keys.StringModelKeyGenerator;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringLongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.exception.InvalidModelKeyTypeException;
import com.dereekb.gae.server.datastore.models.keys.exception.UninitializedModelKeyException;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;
import com.dereekb.gae.utilities.misc.keyed.Keyed;
import com.dereekb.gae.utilities.misc.keyed.exception.NullKeyException;
import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents a key for a model.
 * <p>
 * They key has either a name or number identifier. Keys with a
 * {@link ModelKeyType#NUMBER} identifier will match a key with a
 * {@link ModelKeyType#NAME} identifier that has the same string.
 *
 * @author dereekb
 */
public final class ModelKey
        implements UniqueModel {

	public static final Long UNINITIALIZED_KEY = 0L;

	private final int hashCode;
	private final ModelKeyType type;

	private final String name;
	private final Long id;

	private ModelKey() throws IllegalArgumentException {
		this.id = null;
		this.name = null;
		this.hashCode = 0;
		this.type = ModelKeyType.NULL;
	}

	public ModelKey(Integer id) throws IllegalArgumentException {
		this((id != null) ? new Long(id) : null);
	}

	public ModelKey(Long id) throws IllegalArgumentException {
		if (id == null || id < 0) {
			throw new IllegalArgumentException("Invalid number key '" + id + "'. Must be non-null and non-negative.");
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

	public static ModelKey nullKey() {
		return new ModelKey();
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

	@Deprecated
	public boolean isDefaultKey(ModelKey key) {
		return key.type == ModelKeyType.DEFAULT;
	}

	public boolean isNullKey() {
		return this.type == ModelKeyType.NULL;
	}

	public boolean isInitialized() {
		return this.type == ModelKeyType.NAME || this.id != UNINITIALIZED_KEY;
	}

	@Override
	public ModelKey getModelKey() {
		return this;
	}

	@Override
	public ModelKey keyValue() {
		return this;
	}

	/**
	 * @return String representation of the key value.
	 */
	public String keyAsString() {
		String string;

		switch (this.type) {
			case NAME:
				string = this.name;
				break;
			case NUMBER:
				string = this.id.toString();
				break;
			default:
				string = null;
				break;
		}

		return string;
	}

	/**
	 * @see #keyAsString()
	 */
	@JsonValue
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

	public static boolean isEqual(ModelKey key,
	                              ModelKey userKey) {
		boolean isEqual = false;

		if (key != null) {
			isEqual = key.equals(userKey);
		} else if (userKey == null) {
			isEqual = true;
		}

		return isEqual;
	}

	public static boolean isNullKey(ModelKey key) {
		return (key != null) ? key.isNullKey() : true;
	}

	public static List<ModelKey> readModelKeysFromKeyed(Iterable<? extends AlwaysKeyed<? extends UniqueModel>> keyedModels) {
		List<ModelKey> values = new ArrayList<>();

		if (keyedModels != null) {
			for (AlwaysKeyed<? extends UniqueModel> keyModel : keyedModels) {
				UniqueModel model = keyModel.keyValue();
				ModelKey key = model.keyValue();

				if (key != null) {
					values.add(key);
				}
			}
		}

		return values;
	}

	public static List<ModelKey> readModelKeys(Iterable<? extends Keyed<? extends ModelKey>> models) {
		List<ModelKey> values = new ArrayList<>();

		if (models != null) {
			for (Keyed<? extends ModelKey> model : models) {
				ModelKey key = model.keyValue();

				if (key != null) {
					values.add(key);
				}
			}
		}

		return values;
	}

	public static List<String> readStringKeys(Iterable<? extends Keyed<ModelKey>> models) {
		List<String> ids = new ArrayList<String>();

		readStringKeysIntoCollection(models, ids);

		return ids;
	}

	public static Set<String> readStringKeysSet(Iterable<? extends Keyed<ModelKey>> models) {
		Set<String> ids = new HashSet<String>();

		readStringKeysIntoCollection(models, ids);

		return ids;
	}

	public static void readStringKeysIntoCollection(Iterable<? extends Keyed<ModelKey>> models,
	                                                Collection<String> collection) {
		if (models != null) {
			for (Keyed<ModelKey> model : models) {
				ModelKey key = model.keyValue();

				if (key != null) {
					String id = key.keyAsString();
					collection.add(id);
				}
			}
		}
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
	 * {@code null} instead of throws an exception.
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

	public static Long strictReadIdentifier(ModelKey key) throws InvalidModelKeyTypeException {
		Long id = null;

		if (key != null) {
			id = key.getId();

			if (id == null) {
				throw new InvalidModelKeyTypeException();
			}
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

	public static String strictReadName(ModelKey key) throws InvalidModelKeyTypeException {
		String name = null;

		if (key != null) {
			name = key.getName();

			if (name == null) {
				throw new InvalidModelKeyTypeException();
			}
		}

		return name;
	}

	public static String readStringKey(Keyed<ModelKey> keyed) {
		ModelKey key = keyed.keyValue();

		if (key != null) {
			return key.toString();
		} else {
			return null;
		}
	}

	public static String readStringKey(ModelKey key) {
		String keyString = null;

		if (key != null) {
			keyString = key.keyAsString();
		}

		return keyString;
	}

	private static final String SPLITTER = ",";

	public static String keysAsString(Iterable<? extends UniqueModel> models) {
		return ModelKey.keysAsString(models, SPLITTER);
	}

	public static String keysAsString(Iterable<? extends UniqueModel> models,
	                                  String splitter) {
		Set<ModelKey> keys = ModelKey.makeModelKeySet(models);
		return ModelKey.keysAsString(keys, splitter);
	}

	public static String keysAsString(Set<ModelKey> keys) {
		return keysAsString(keys, SPLITTER);
	}

	public static String keysAsString(Set<ModelKey> keys,
	                                  String splitter) {
		return StringUtility.joinValues(splitter, keys);
	}

	public static List<ModelKey> convertKeysInString(ModelKeyType keyType,
	                                                 String stringKey) {
		return convertKeysInString(keyType, stringKey, SPLITTER);
	}

	public static List<ModelKey> convertKeysInString(ModelKeyType keyType,
	                                                 String keys,
	                                                 String splitter)
	        throws IllegalArgumentException {
		List<String> valuesList = StringUtility.separateValues(splitter, keys);
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
	 * Convenience function for converting.
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
	        throws IllegalArgumentException,
	            ConversionFailureException {
		return converterForKeyType(keyType).convert(values);
	}

	/**
	 * Converts a string to a {@link ModelKey}. If the
	 * string is {@code null}, will return {@code null}.
	 * <p>
	 * Same as calling {@link #safe(String)}
	 *
	 * @param numberString
	 *            String number to convert.
	 * @return {@link ModelKey} with a Number identifier
	 */
	public static ModelKey convertNameString(String nameString) {
		return ModelKey.safe(nameString);
	}

	/**
	 * Returns the {@link StringModelKeyConverter} for the input key type.
	 *
	 * @param keyType
	 *            {@link ModelKeyType} of values.
	 * @return {@link StringModelKeyConverter}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             if the input key type is unsupported.
	 */
	public static StringModelKeyConverter converterForKeyType(ModelKeyType keyType) throws IllegalArgumentException {
		switch (keyType) {
			case NAME:
				return StringModelKeyConverterImpl.CONVERTER;
			case NUMBER:
				return StringLongModelKeyConverterImpl.CONVERTER;
			default:
				throw new IllegalArgumentException("Invalid key type specified.");
		}
	}

	/**
	 * Returns the {@link Generator} for the input key type.
	 *
	 * @param keyType
	 *            {@link ModelKeyType} of values.
	 * @return {@link Generator}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             if the input key type is unsupported.
	 */
	public static Generator<ModelKey> generatorForKeyType(ModelKeyType keyType) throws IllegalArgumentException {
		switch (keyType) {
			case NAME:
				return StringModelKeyGenerator.GENERATOR;
			case NUMBER:
				return LongModelKeyGenerator.GENERATOR;
			default:
				throw new IllegalArgumentException("Invalid key type specified.");
		}
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
	 * @deprecated Is dangerous to use. Rely on a specific
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
	 * @throws NullKeyException
	 *             Thrown if any left model has no model key.
	 */
	public static <L extends Keyed<ModelKey>, R extends Keyed<ModelKey>> List<HandlerPair<L, R>> makePairs(Iterable<? extends L> leftModels,
	                                                                                                       Iterable<? extends R> rightModels)
	        throws NullKeyException {

		List<HandlerPair<L, R>> pairs = new ArrayList<>();
		Map<ModelKey, L> leftMap = makeModelKeyMap(leftModels);

		for (R right : rightModels) {
			ModelKey key = right.keyValue();
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
	 * @throws NullKeyException
	 *             Thrown if a model does not have a key.
	 */
	public static <T extends Keyed<ModelKey>> Map<ModelKey, T> makeModelKeyMap(Iterable<? extends T> models)
	        throws NullKeyException {
		return KeyedUtility.toMap(models);
	}

	/**
	 * Creates a map of model keys for the input models.
	 *
	 * @param models
	 *            {@link Iterable} collection. Never {@code null}.
	 * @return {@link Set}. Never {@code null}.
	 * @throws NullKeyException
	 *             Thrown if a model does not have a key.
	 */
	public static <T extends Keyed<ModelKey>> Set<ModelKey> makeModelKeySet(Iterable<? extends T> models)
	        throws NullKeyException {
		Set<ModelKey> keys = new HashSet<>();

		for (T model : models) {
			ModelKey key = model.keyValue();

			if (key == null) {
				throw new NullKeyException();
			} else {
				keys.add(key);
			}
		}

		return keys;
	}

	public static void assertAreAllInitializedKeys(List<ModelKey> modelKeys) throws UninitializedModelKeyException {
		if (areAllInitialized(modelKeys) == false) {
			throw new UninitializedModelKeyException();
		}
	}

	public static boolean areAllInitialized(Iterable<ModelKey> modelKeys) {
		for (ModelKey key : modelKeys) {
			if (key.isInitialized() == false) {
				return false;
			}
		}

		return true;
	}

	private static final String COMPOSITE_NAME_SEPARATOR = "_";

	/**
	 * Makes multiple composite keys, using the initial key as a root and the
	 * subkeys as the second component.
	 *
	 * @param root
	 *            The root {@link ModelKey}. Never {@code null}.
	 * @param subkeys
	 *            {@link List}. Never {@code null}, and its contents should
	 *            never be {@code null} either.
	 * @return {@link List}. Never {@code null}.
	 * @throws NullPointerException
	 *             Thrown if any input subkey is null.
	 */
	public static List<ModelKey> makeCompositeKeys(ModelKey root,
	                                               Iterable<ModelKey> subkeys) {
		if (root == null) {
			throw new NullPointerException("Root cannot be null.");
		}

		List<ModelKey> compositeKeys = new ArrayList<ModelKey>();

		for (ModelKey subkey : subkeys) {
			if (subkey == null) {
				throw new NullPointerException();
			}

			String name = safeMakeCompositeName(root, subkey);
			ModelKey composite = new ModelKey(name);
			compositeKeys.add(composite);
		}

		return compositeKeys;
	}

	/**
	 * Makes multiple composite keys, using the input keys as the roots and the
	 * subkey as the second component.
	 *
	 * @param roots
	 *            {@link List}. Never {@code null}, and its contents should
	 *            never be {@code null} either.
	 * @param subkey
	 *            Subkey {@link ModelKey}. Never {@code null}.
	 * @throws NullPointerException
	 *             Thrown if any input subkey is null.
	 */
	public static List<ModelKey> makeCompositeKeys(List<ModelKey> roots,
	                                               ModelKey subkey) {
		if (subkey == null) {
			throw new NullPointerException("Subkey cannot be null.");
		}

		List<ModelKey> compositeKeys = new ArrayList<ModelKey>();

		for (ModelKey root : roots) {
			if (root == null) {
				throw new NullPointerException();
			}

			String name = safeMakeCompositeName(root, subkey);
			ModelKey composite = new ModelKey(name);
			compositeKeys.add(composite);
		}

		return compositeKeys;
	}

	/**
	 * Does {{@link #makeCompositeName(Object...)} and wraps the value in a
	 * {@link MdoelKey}.
	 *
	 *
	 * @param components
	 *            Components array. Never {@code null}. Must be longer than 1
	 *            element. No elements should be {@code null}.
	 * @return {@link ModelKey}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             Thrown if less than 2 elements are input.
	 * @throws NullPointerException
	 *             Thrown if any input component is null.
	 */
	public static ModelKey makeCompositeKey(Object... components)
	        throws IllegalArgumentException,
	            NullPointerException {
		String name = makeCompositeName(components);
		return new ModelKey(name);
	}

	/**
	 * Creates a new composite name from the input.
	 *
	 * @param components
	 *            Components array. Never {@code null}. Must be longer than 1
	 *            element. No elements should be {@code null}.
	 * @return {@link String}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             Thrown if less than 2 elements are input.
	 * @throws NullPointerException
	 *             Thrown if any input component is null.
	 */
	public static String makeCompositeName(Object... components) throws IllegalArgumentException, NullPointerException {
		if (components.length < 2) {
			throw new IllegalArgumentException("Composite names must be generated with 2 or more elements.");
		}

		ListUtility.assertNoNulls(components);

		return safeMakeCompositeName(components);
	}

	private static String safeMakeCompositeName(Object... components) {
		return StringUtility.joinValues(COMPOSITE_NAME_SEPARATOR, components);
	}

	// MARK: Info
	public static ModelKeyType readModelKeyType(Class<?> type) throws NullPointerException {
		return readModelKeyInfo(type).value();
	}

	public static ModelKeyGenerationType readModelKeyGenerationType(Class<?> type) throws NullPointerException {
		return readModelKeyInfo(type).generation();
	}

	public static ModelKeyInfo readModelKeyInfo(Class<?> type) throws NullPointerException {
		ModelKeyInfo annotation = type.getAnnotation(ModelKeyInfo.class);

		if (annotation == null) {
			throw new NullPointerException("Class '" + type.getName() + "' has no model key information attached.");
		}

		return annotation;
	}

}
