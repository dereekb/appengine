package com.dereekb.gae.server.search;

import java.util.List;

/**
 * Used by the {@link DocumentSearchController} for changing a collection of
 * documents concurrently.
 *
 * @author dereekb
 */
@Deprecated
public interface DocumentChangeSet {

	/**
	 *
	 * @return Document Index name.
	 */
	public String getIndexName();

	/**
	 *
	 * @return {@link List} of {@link DocumentChangeModel} values in this
	 *         {@link DocumentChangeSet}.
	 */
	public List<DocumentChangeModel> getDocumentModels();

	/**
	 * @return {@code true} if changes are performed successfully, or
	 *         {@code null} if no changes have been performed yet.
	 */
	public boolean getSuccess();

	public void setSuccess(boolean success);

}
