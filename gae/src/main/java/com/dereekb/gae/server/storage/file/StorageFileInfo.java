package com.dereekb.gae.server.storage.file;

import java.util.Date;

/**
 * Contains meta-data about the given file.
 * 
 * @author dereekb
 *
 */
public interface StorageFileInfo {

	public String getContentType();

	public String getFilename();

	public Date getCreationDate();

	public Long getSize();

}
