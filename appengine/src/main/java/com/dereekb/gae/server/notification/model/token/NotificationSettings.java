package com.dereekb.gae.server.notification.model.token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyGenerationType;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcement;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.server.datastore.objectify.model.ObjectifyDatabaseEntityInfo;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Database model that contains an array of {@link NotificationToken} values and
 * notification settings for a user.
 *
 * @author dereekb
 *
 */
@Cache
@Entity
@ModelKeyInfo(value = ModelKeyType.NUMBER, generation = ModelKeyGenerationType.RELATED_NAME)
@ObjectifyDatabaseEntityInfo(keyEnforcement = ObjectifyDatabaseEntityKeyEnforcement.MUST_BE_PROVIDED_AND_UNIQUE)
public class NotificationSettings extends DatabaseModel
        implements ObjectifyModel<NotificationSettings> {

	private static final long serialVersionUID = 1L;

	@Id
	private Long identifier;

	/**
	 * List of embedded notification tokens.
	 */
	private List<NotificationToken> tokens = new ArrayList<NotificationToken>();

	public NotificationSettings() {}

	public NotificationSettings(Long identifier) {
		this.identifier = identifier;
	}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public List<NotificationToken> getTokens() {
		return this.tokens;
	}

	public void setTokens(Collection<NotificationToken> tokens) {
		this.tokens = ListUtility.newList(tokens);
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
	}

	@Override
	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.readIdentifier(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public Key<NotificationSettings> getObjectifyKey() {
		return ObjectifyKeyUtility.createKey(NotificationSettings.class, this.identifier);
	}

	@Override
	public String toString() {
		return "NotificationSettings [identifier=" + this.identifier + ", tokens=" + this.tokens + "]";
	}

}
