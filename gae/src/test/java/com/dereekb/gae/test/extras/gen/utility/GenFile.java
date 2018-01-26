package com.dereekb.gae.test.extras.gen.utility;

public interface GenFile {

	/**
	 * Returns the file name without the extension.
	 *
	 * @return
	 */
	public String getFileName();

	/**
	 * Returns the file extension type.
	 *
	 * @return
	 */
	public String getFileType();

	/**
	 *
	 * @return
	 */
	public String getOutputFileName();

	public String getFileStringContents() throws UnsupportedOperationException;

}
