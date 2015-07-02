package com.dereekb.gae.server.storage.file;

/**
 * A storable object with data of itself attached.
 * 
 * @author dereekb
 *
 */
public interface StorableData
        extends StorableFile {

	public byte[] getBytes();

}
