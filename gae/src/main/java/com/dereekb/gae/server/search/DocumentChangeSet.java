package com.dereekb.gae.server.search;

import java.util.List;

/**
 * Used by the {@link DocumentSearchController} for changing a collection of
 * documents concurrently.
 *
 * @author dereekb
 */
public interface DocumentChangeSet {

	public String getIndexName();

	public List<DocumentChangeModel> getDocumentModels();

	public void setSuccess(boolean success);

}
