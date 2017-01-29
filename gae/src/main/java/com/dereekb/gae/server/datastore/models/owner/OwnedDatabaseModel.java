package com.dereekb.gae.server.datastore.models.owner;

import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNotEmpty;

/**
 * {@link DatabaseModel} that implements {@link Owned}.
 * 
 * @author dereekb
 *
 */
public abstract class OwnedDatabaseModel extends DatabaseModel
        implements MutableOwnedModel {

	private static final long serialVersionUID = 1L;

	protected OwnedDatabaseModel() {}

	@Index(IfNotEmpty.class)
	@IgnoreSave({ IfEmpty.class })
	private String ownerId;

	@Override
	public String getOwnerId() {
		return this.ownerId;
	}

	@Override
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

}
