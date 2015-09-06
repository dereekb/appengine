package com.dereekb.gae.server.storage.file;

import com.dereekb.gae.server.storage.file.options.StorableFileOptionsImpl;

/**
 * {@link StorableData} extension that also provides the content type and
 * potential {@link StorableFileOptionsImpl}.
 *
 * @author dereekb
 *
 */
public interface StorableContent
        extends StorableData {

	/**
	 * Returns the content type. Generally a MIME type.
	 *
	 * @return {@link String} containing the content type. Never {@code null}.
	 */
	public String getContentType();

	/**
	 * {@link StorableFileOptionsImpl}
	 *
	 * @return {@link StorableFileOptionsImpl} if available, or {@code null} if none.
	 */
	public StorableFileOptionsImpl getOptions();

}
