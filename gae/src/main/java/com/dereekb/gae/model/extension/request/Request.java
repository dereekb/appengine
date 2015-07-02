package com.dereekb.gae.model.extension.request;

import java.util.Date;
import java.util.HashMap;

import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNull;

/**
 * Default system request type.
 *
 * Generated requests should be idempotent, so creating a request twice should
 * result in a single {@link Request} object created in the database, with the
 * same info.
 *
 * @author dereekb
 */
@Entity
public final class Request extends DatabaseModel
        implements ObjectifyModel<Request> {

	private static final long serialVersionUID = 1L;

	/**
	 * Unique identifier for this request.
	 */
	@Id
	private String identifier;

	/**
	 * Type of this request.
	 */
	@Index
	private String type;

	/**
	 * Target identifier.
	 */
	@Index
	private String target;

	/**
	 * Date the request was created.
	 */
	private Date date = new Date();

	/**
	 * Keyed information for this request.
	 */
	@IgnoreSave({ IfNull.class, IfEmpty.class })
	private HashMap<String, String> info = new HashMap<String, String>();

	public Request() {}

	public Request(String type) {
		this.type = type;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public HashMap<String, String> getInfo() {
		return this.info;
	}

	public void setInfo(HashMap<String, String> info) {
		if (info == null) {
			info = new HashMap<String, String>();
		}

		this.info = info;
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return new ModelKey(this.identifier);
	}

	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.readName(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public Key<Request> getObjectifyKey() {
		return Key.create(Request.class, this.identifier);
	}

	@Override
	public String toString() {
		return "Request [identifier=" + this.identifier + ", type=" + this.type + ", target=" + this.target + ", date="
		        + this.date + ", info=" + this.info + "]";
	}

}
