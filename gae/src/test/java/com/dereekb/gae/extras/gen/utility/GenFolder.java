package com.dereekb.gae.extras.gen.utility;

import java.util.List;

public interface GenFolder {

	public String getFolderName();

	public List<GenFolder> getFolders();

	public List<GenFile> getFiles();

	/**
	 * Returns a file with the specified name, if it exists.
	 * <p>
	 * Is not case-sensitive.
	 *
	 * @param filename
	 *            {@link String}. Never {@code null}.
	 * @return {@link GenFile}, or {@code null} if it doesn't exist.
	 */
	public GenFile getFileWithName(String filename);

	/**
	 * Returns a new folder that wraps this one.
	 *
	 * @param folder
	 *            {@link String}. Never {@code null}.
	 * @return {@link GenFolder}. Never {@code null}.
	 */
	public GenFolder wrap(String folder);

}
