package com.dereekb.gae.server.storage.file;

import com.dereekb.gae.server.storage.file.options.StorableFileOptions;
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
	 * Returns the storable options.
	 *
	 * @return {@link StorableFileOptions} if available, or {@code null} if
	 *         none.
	 */
	public StorableFileOptions getOptions();

}
