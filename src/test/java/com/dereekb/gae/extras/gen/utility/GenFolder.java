package com.dereekb.gae.extras.gen.utility;

import java.util.List;

/**
 * A generation folder. Does not correspond to any existing files, but instead a
 * list of files/folders that are being generated.
 *
 * @author dereekb
 *
 */
public interface GenFolder {

	/**
	 * Returns the name of the folder.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getFolderName();

	/**
	 * Returns the subfolders in this folder.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<GenFolder> getFolders();

	/**
	 * Returns the files in this folder.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<GenFile> getFiles();

	/**
	 * Returns {@code true} if a file with the specified name exists.
	 *
	 * @param filename
	 *            {@link String}. Never {@code null}.
	 * @return {@code true} if a file exists with the input name.
	 */
	public boolean hasFileWithName(String filename);

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
