package com.dereekb.gae.extras.gen.utility;

import java.util.List;

public interface GenFolder {

	public String getFolderName();

	public List<GenFolder> getFolders();

	public List<GenFile> getFiles();

	/**
	 * Returns a new folder that wraps this one.
	 *
	 * @param folder
	 *            {@link String}. Never {@code null}.
	 * @return {@link GenFolder}. Never {@code null}.
	 */
	public GenFolder wrap(String folder);

}
