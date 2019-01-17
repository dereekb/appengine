package com.dereekb.gae.utilities.collections.iterator.cursor.impl;

import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link ResultsCursor} implementation that wraps a string.
 *
 * @author dereekb
 *
 */
public final class ResultsCursorImpl
        implements ResultsCursor {

	private String cursorString;

	public ResultsCursorImpl(ResultsCursor cursorString) {
		this(cursorString.getCursorString());
	}

	public ResultsCursorImpl(String cursorString) {
		super();
		this.setCursorString(cursorString);
	}

	/**
	 * Wraps the input string with a ResultsCursor, unless the input is empty or
	 * {@code null}.
	 *
	 * @param cursorString
	 *            {@link String}, or {@code null}.
	 * @return {@link ResultsCursorImpl} or {@code null}.
	 */
	public static ResultsCursorImpl wrap(String cursorString) {
		if (StringUtility.isEmptyString(cursorString)) {
			return make(cursorString);
		}

		return null;
	}

	/**
	 * Convenience function for {@code new ResultsCursorImpl(string)}.
	 *
	 * @param cursorString
	 *            {@link String}. Never {@code null}.
	 * @return {@link ResultsCursorImpl}. Never {@code null}.
	 */
	public static ResultsCursorImpl make(String cursorString) {
		return new ResultsCursorImpl(cursorString);
	}

	@Override
	public String getCursorString() {
		return this.cursorString;
	}

	public void setCursorString(String cursorString) {
		if (cursorString == null) {
			throw new IllegalArgumentException("cursorString cannot be null.");
		}

		this.cursorString = cursorString;
	}

	@Override
	public String toString() {
		return "IndexedModelCursorImpl [cursorString=" + this.cursorString + "]";
	}

}
