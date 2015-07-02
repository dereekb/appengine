package com.dereekb.gae.utilities.collections.tree.map.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.tree.map.MapTree;
import com.dereekb.gae.utilities.collections.tree.map.exceptions.MapTreeBuilderOpenLiteralException;
import com.dereekb.gae.utilities.collections.tree.map.exceptions.MapTreeBuilderOpenParametersException;
import com.dereekb.gae.utilities.collections.tree.map.exceptions.MapTreeBuilderUnexpectedParametersException;
import com.dereekb.gae.utilities.collections.tree.map.exceptions.MapTreeBuilderUnexpectedTokenException;
import com.google.common.base.Joiner;

/**
 * Used for building a map tree using an input string language.
 * 
 * The language follows:
 * 
 * '{value}' - Denotes a value
 * '.' - Denotes a sub element
 * '[' - Opens an array
 * ']' - Closes an array
 * ',' - Denotes a new element in an array.
 * '(' - Opens a parameters statement for an element
 * ')' - Closes a parameters statement for an element
 * ' ' - Spaces at the beginning of values are ignored.
 * '\n' - New lines are also ignored.
 * 
 * The following values are valid:
 * 
 * 'a.b.c': A tree with depth 3
 * 'a,b,c': A tree with depth 1, with three elements
 * 'a.b.[c,d,e]': A tree with depth 3, with three elements as children of b
 * 'a(b,c,d)': A tree with depth 1, with 1 element with three parameters b,c,d.
 * 'a(b, c, d)': Same as the tree above.
 * 'a(b ,c ,d )': A tree with depth 1, with 1 element with three parameters 'b ','c ,'d '. Spaces trailing a character are preserved.
 * 
 * Other notes:
 * - All trees have a root element with a null value.
 * - Multiple '.' elements are ignored.
 * - Empty arrays are ignored.
 * - A '.' cannot directly follow a ',', and vice-versa.
 * - An array does not need to be closed, but should be for safety.
 * - Literals must be closed
 * - Parameters must be closed
 * 
 * @author dereekb
 */
public class MapTreeBuilder<T> {

	private MapTreeBuilderDelegate<T> delegate;
	private MapTreeValueReadOption readOption = MapTreeValueReadOption.COMMON_VALUE;

	public MapTreeBuilder() {}

	public MapTreeBuilder(MapTreeBuilderDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public MapTree<T> decodeTrees(Iterable<String> strings) {
		String combinedString = this.combineTreeStrings(strings);
		MapTree<T> rootNode = this.decodeTree(combinedString);
		return rootNode;
	}

	/**
	 * Completes the input strings, then wraps them into one big array.
	 * 
	 * @param strings
	 * @return
	 */
	public String combineTreeStrings(Iterable<String> strings) {
		List<String> usableStrings = new ArrayList<String>();

		for (String string : strings) {
			String usableString = this.completeTreeString(string);
			usableStrings.add(usableString);
		}

		Joiner stringJoiner = Joiner.on(",");
		String combinedString = "[" + stringJoiner.join(usableStrings) + "]";
		return combinedString;
	}

	/**
	 * Completes a tree string by wrapping it in ARRAY braces if they don't exist in the string already.
	 * 
	 * @param string
	 * @return
	 */
	public String completeTreeString(String string) {

		if (string.endsWith("]") == false) {
			string = string + "]";
		}

		if (string.startsWith("[") == false) {
			string = "[" + string;
		}

		return string;
	}

	public MapTree<T> decodeTree(String string) {
		MapTreeBuilderInstance instance = new MapTreeBuilderInstance(string);
		MapTree<T> tree = instance.buildTree();
		return tree;
	}

	private class MapTreeBuilderInstance {

		private String treeString;
		private Integer index = 0;

		public MapTreeBuilderInstance(String treeString) throws IllegalArgumentException {
			if (treeString == null) {
				throw new IllegalArgumentException("Tree string cannot be null.");
			}

			this.treeString = treeString;
		}

		public MapTree<T> buildTree() {
			MapTree<T> root = new MapTree<T>();

			MapTreeReadingStart reading = new MapTreeReadingStart(root);
			reading.read();

			return root;
		}

		/**
		 * Starting point for a MapTreeReading.
		 * 
		 * @author dereekb
		 */
		private class MapTreeReadingStart extends MapTreeBuilderInstanceReading {

			private final MapTree<T> root;

			private MapTreeReadingStart(MapTree<T> root) {
				this.root = root;
			}

			@Override
			protected void read() {
				Collection<MapTree<T>> parents = SingleItem.withValue(this.root);
				MapTreeArrayReading arrayReading = new MapTreeArrayReading(parents);
				arrayReading.read();
				// MapTreeElementReading elementReading = new MapTreeElementReading(parents);
				// elementReading.read();
			}

		}

		/**
		 * Reads a single element, be it a set of CHARACTERS, a LITERAL, or an ARRAY and any following NEW_CHILD values, and then returns.
		 * Parents are forwarded to the next sub-call.
		 * 
		 * Returns when it reads a NEW_ELEMENT, or ARRAY_END.
		 * 
		 * Raises an exception if it encounters a PARAMETER_START, ARRAY_END, or END
		 * 
		 * @author dereekb
		 *
		 */
		private class MapTreeElementReading extends MapTreeBuilderInstanceReading {

			private List<MapTree<T>> newElements = null;

			public MapTreeElementReading(Collection<MapTree<T>> parents) {
				super(parents);
			}

			@Override
			protected void read() {
				MapTreeToken nextType = this.skipToNextNonEmptyToken();

				switch (nextType) {
					case WILD_CARD: {
						this.readWildcard();
						index += 1;
						newElements = Collections.emptyList();
						return; // Return without updating new elements.
					}
					case CHARACTER:
					case LITERAL: {
						MapTreeValue treeValue = this.readNextTreeValue();
						this.newElements = this.appendValueToParents(treeValue);
					}
						break;
					case ARRAY_START: {
						index += 1;
						MapTreeArrayReading arrayReading = new MapTreeArrayReading(this.parents);
						arrayReading.read();
						this.newElements = arrayReading.getValues();
					}
						break;
					case PARAMETER_START:
					case PARAMETER_END: {
						throw new MapTreeBuilderUnexpectedParametersException(index);
					}
					default: {
						throw new MapTreeBuilderUnexpectedTokenException(index);
					}
				}

				nextType = this.safePeekNextCharacterType();

				// Check to see if there is a child attached to this element, and continue reading with the new elements as it's parents.
				if (nextType == MapTreeToken.NEW_CHILD) {
					index += 1;
					MapTreeElementReading elementReading = new MapTreeElementReading(newElements);
					elementReading.read();
				}
			}

			private void readWildcard() {
				for (MapTree<T> parent : this.parents) {
					parent.setWildcard(true);
				}
			}

			private List<MapTree<T>> appendValueToParents(MapTreeValue treeValue) {
				List<MapTree<T>> newElements = new ArrayList<MapTree<T>>();

				switch (readOption) {
					case COMMON_VALUE: {
						T value = delegate.valueForString(treeValue);

						for (MapTree<T> parent : this.parents) {
							MapTree<T> newElement = parent.put(value);
							newElements.add(newElement);
						}
					}
						break;
					case UNIQUE_VALUE: {
						for (MapTree<T> parent : this.parents) {
							T value = delegate.valueForString(treeValue);
							MapTree<T> newElement = parent.put(value);
							newElements.add(newElement);
						}
					}
						break;
				}

				return newElements;
			}

			public List<MapTree<T>> getNewElements() {
				return newElements;
			}

		}

		/**
		 * An array reading. Starts after a ARRAY_START token.
		 * 
		 * @author dereekb
		 *
		 */
		private class MapTreeArrayReading extends MapTreeBuilderInstanceReading {

			private List<MapTree<T>> values = new ArrayList<MapTree<T>>();

			public MapTreeArrayReading(Collection<MapTree<T>> parents) {
				super(parents);
			}

			public List<MapTree<T>> getValues() {
				return values;
			}

			/**
			 * Reads the array until it reaches the end-token for itself.
			 */
			public void read() {

				READ_LOOP: while (this.hasMoreCharacters()) {
					MapTreeToken nextType = this.peekNextCharacterType();

					switch (nextType) {
						case NEW_ELEMENT: {
							index += 1;
						}
						case NEW_LINE:
						case SPACE:
						case WILD_CARD:
						case CHARACTER:
						case LITERAL:
						case ARRAY_START: {
							MapTreeElementReading elementReading = new MapTreeElementReading(this.parents);
							elementReading.read();
							List<MapTree<T>> elements = elementReading.getNewElements();
							this.values.addAll(elements);
						}
							break;
						case ARRAY_END: {
							index += 1; // Pass over the array end before returning.
						}
						case END:
							break READ_LOOP;
						case NEW_CHILD:
						case PARAMETER_START:
						case PARAMETER_END: {
							throw new MapTreeBuilderUnexpectedParametersException(index);
						}
					}
				}
			}
		}

		/**
		 * Abstract reading instance that contains various helper functions for performing a read.
		 * 
		 * Can also read values, etc.
		 * 
		 * @author dereekb
		 *
		 */
		private abstract class MapTreeBuilderInstanceReading {

			// protected final Integer startIndex = index;
			protected final Collection<MapTree<T>> parents;

			/**
			 * Start-only constructor
			 */
			public MapTreeBuilderInstanceReading() {
				this.parents = null;
			}

			public MapTreeBuilderInstanceReading(Collection<MapTree<T>> parents) {
				this.parents = parents;
			}

			/**
			 * Reads the input value, and modifies the
			 */
			protected abstract void read();

			protected boolean hasMoreCharacters() {
				return (index < treeString.length());
			}

			protected char peekNextCharacter() throws IndexOutOfBoundsException {
				return this.peekNextCharacter(0);
			}

			protected char peekNextCharacter(Integer skip) throws IndexOutOfBoundsException {
				char character = treeString.charAt(index + skip);
				return character;
			}

			protected MapTreeToken peekNextCharacterType() throws IndexOutOfBoundsException {
				return this.peekNextCharacterType(0);
			}

			protected MapTreeToken peekNextCharacterType(Integer skip) throws IndexOutOfBoundsException {
				char character = treeString.charAt(index + skip);
				MapTreeToken token = MapTreeToken.typeForChar(character);
				return token;
			}

			/**
			 * Checks to see if there are more characters before checking. If there are none remaining, returns {@link MapTreeToken.END}.
			 * 
			 * @return
			 */
			protected MapTreeToken safePeekNextCharacterType() {
				return this.safePeekNextCharacterType(0);
			}

			/**
			 * Checks to see if there are more characters before checking. If there are none remaining, returns {@link MapTreeToken.END}.
			 * 
			 * @param skip How far ahead in the index to skip.
			 * @return
			 */
			protected MapTreeToken safePeekNextCharacterType(Integer skip) {
				MapTreeToken token = null;

				if (this.hasMoreCharacters()) {
					token = this.peekNextCharacterType(skip);
				} else {
					token = MapTreeToken.END;
				}

				return token;
			}

			/**
			 * Safely peeks at the next non-empty token, while skipping empty tokens.
			 * 
			 * @return
			 */
			protected MapTreeToken skipToNextNonEmptyToken() {
				MapTreeToken token = null;

				SKIP_LOOP: while (true) {
					token = this.safePeekNextCharacterType();

					SWITCH: switch (token) {
						case NEW_LINE:
						case SPACE: { // Spaces and new lines are ignored.
							index += 1; // Skip the array end token.
						}
							break SWITCH;
						default: {
							break SKIP_LOOP;
						}
					}
				}

				return token;
			}

			/**
			 * Reads the tree value, starting at a literal or character value.
			 * 
			 * Will return with the index pointing to the character after the read tree value.
			 * 
			 * @return
			 */
			protected MapTreeValue readNextTreeValue() {
				List<String> parameters = null;
				String value = this.readTreeStringValue();

				if (this.hasMoreCharacters()) {
					MapTreeToken currentType = this.peekNextCharacterType();
					boolean hasParameters = (currentType == MapTreeToken.PARAMETER_START);

					if (hasParameters) {
						parameters = this.readParameters();
					}
				}

				MapTreeValue treeValue = new MapTreeValue(value, parameters);
				return treeValue;
			}

			private List<String> readParameters() {
				Integer startIndex = index;

				List<String> parameters = new ArrayList<String>();
				MapTreeToken currentType = this.peekNextCharacterType();

				if (currentType == MapTreeToken.PARAMETER_START) {
					index += 1; // Skip the starting parameter.
					currentType = this.safePeekNextCharacterType();
				} else {
					throw new RuntimeException("Attempted to start reading parameters where there was none.");
				}

				while (parameters.isEmpty() || currentType == MapTreeToken.NEW_ELEMENT) {
					if (currentType == MapTreeToken.NEW_ELEMENT) {
						index += 1;
					}

					String parameter = this.readTreeStringValue();
					parameters.add(parameter);

					currentType = this.skipToNextNonEmptyToken();
				}

				if (currentType != MapTreeToken.PARAMETER_END) {
					throw new MapTreeBuilderOpenParametersException(startIndex);
				}

				index += 1; // Skip the parameter ending.
				return parameters;
			}

			/**
			 * Reads the next characters value, whether they are wrapped in a literal or not.
			 * 
			 * Will return with the index pointing to the character after the read value.
			 * 
			 */
			private String readTreeStringValue() {
				String value = null;
				MapTreeToken currentType = this.skipToNextNonEmptyToken();

				if (currentType == MapTreeToken.LITERAL) {
					value = this.readLiteral();
				} else {
					value = this.readCharactersValue();
				}

				return value;
			}

			/**
			 * Reads in literal value and removes the quotation marks surrounding the literal.
			 *
			 * Should start on a literal value. Will return with the index pointing to the character after the literal closes.
			 * 
			 * @return
			 * @throws MapTreeBuilderOpenLiteralException Thrown if the literal is not closed.
			 */
			private String readLiteral() throws MapTreeBuilderOpenLiteralException {
				Integer startIndex = index;

				String literal = null;
				StringBuilder builder = new StringBuilder();

				MapTreeToken currentType = this.peekNextCharacterType();

				if (currentType == MapTreeToken.LITERAL) {
					index += 1; // Skip the starting literal.
					currentType = this.safePeekNextCharacterType();
				} else {
					throw new RuntimeException("Attempted to start reading literal where there was none.");
				}

				while (currentType != MapTreeToken.END && currentType != MapTreeToken.LITERAL) {
					char character = this.peekNextCharacter();
					builder.append(character);

					index += 1;
					currentType = this.safePeekNextCharacterType();
				}

				if (currentType != MapTreeToken.LITERAL) {
					throw new MapTreeBuilderOpenLiteralException(startIndex);
				}

				index += 1; // Skip the ending literal
				literal = builder.toString();
				return literal;
			}

			/**
			 * Reads character values until a non-character type is encountered.
			 * 
			 * Should start on a character value. Will return with the index pointing to the next non-character value.
			 * 
			 * @return
			 */
			private String readCharactersValue() {
				String value = null;
				StringBuilder builder = new StringBuilder();

				MapTreeToken currentType = this.peekNextCharacterType();
				while (currentType == MapTreeToken.CHARACTER || currentType == MapTreeToken.SPACE) {
					char character = this.peekNextCharacter();
					builder.append(character);

					index += 1;
					currentType = this.safePeekNextCharacterType();
				}

				value = builder.toString();
				return value;
			}
		}

	}

	/**
	 * Describes how to handle the read values.
	 * 
	 * @author dereekb
	 *
	 */
	public static enum MapTreeValueReadOption {

		/**
		 * A single common value is transformed by the delegate and shared between parents.
		 * 
		 * It is best for cases where the generated tree is not going to be altered afterwards.
		 */
		COMMON_VALUE(),

		/**
		 * A unique value is generated for each parent, so they do not share the same object.
		 * 
		 * This may be more computationally intensive, depending on the delegate's implementations.
		 * 
		 * It is best for cases where the generated tree's values may be altered.
		 */
		UNIQUE_VALUE()

	}

	// TODO: Remove suppressed warning. Use the bit/char value or remove them.
	@SuppressWarnings("unused")
	private enum MapTreeToken {

		CHARACTER(0, '#'),
		WILD_CARD(1, '*'),
		NEW_CHILD(2, '.'),
		NEW_ELEMENT(3, ','),
		ARRAY_START(4, '['),
		ARRAY_END(5, ']'),
		PARAMETER_START(6, '('),
		PARAMETER_END(7, ')'),
		LITERAL(8, '"'),
		SPACE(9, ' '),
		NEW_LINE(10, '\n'),
		END(11, '\0');

		private final int bit;
		private final char value;

		private MapTreeToken(int bit, char value) {
			this.bit = bit;
			this.value = value;
		}

		public static MapTreeToken typeForChar(char token) {
			MapTreeToken type = CHARACTER;

			switch (token) {
				case '*':
					type = WILD_CARD;
					break;
				case '.':
					type = NEW_CHILD;
					break;
				case ',':
					type = NEW_ELEMENT;
					break;
				case '[':
					type = ARRAY_START;
					break;
				case ']':
					type = ARRAY_END;
					break;
				case '(':
					type = PARAMETER_START;
					break;
				case ')':
					type = PARAMETER_END;
					break;
				case ' ':
					type = SPACE;
					break;
				case '\n':
					type = NEW_LINE;
					break;
				case '"':
					type = LITERAL;
					break;
			}

			return type;
		}

		public int getBit() {
			return bit;
		}

		public char getValue() {
			return value;
		}

	}

	public MapTreeBuilderDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(MapTreeBuilderDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
