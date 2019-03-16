package com.dereekb.gae.utilities.misc.path;

import java.util.List;

/**
 * Represents a simple file/URI path.
 *
 * @author dereekb
 *
 */
public interface SimplePath {

	/**
	 * Returns the path.
	 *
	 * @return {@link String} path. Never {@code null}.
	 */
	public String getPath();

	/**
	 * Returns path components.
	 * <p>
	 * May be empty if the path is a divider.
	 *
	 * @return {@link List} of path components. Never {@code null}.
	 */
	public List<String> getPathComponents();

	/**
	 * Appends this and the target path together.
	 *
	 * @param path
	 *            {@link String} path. Never {@code null}.
	 * @return {@link SimplePath} containing the result.
	 */
	public SimplePath append(String path);

	/**
	 * Appends this and the target path together.
	 *
	 * @param path
	 *            {@link String} path. Never {@code null}.
	 * @return {@link SimplePath} containing the result.
	 */
	public SimplePath append(SimplePath path);

}
