package com.dereekb.gae.server.datastore.models;

import java.util.Date;

import com.dereekb.gae.utilities.time.model.MutableDatedModel;
import com.googlecode.objectify.annotation.Index;

/**
 * {@link DatabaseModel} extension with an indexed date.
 *
 * @author dereekb
 *
 */
public abstract class DatedDatabaseModel extends DatabaseModel
        implements MutableDatedModel {

	private static final long serialVersionUID = 1L;

	@Index
	protected Date date = this.initDate();

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	// MARK: Internal
	protected Date initDate() {
		return new Date();
	}

}
