package com.thevisitcompany.gae.deprecated.model.mod.relationships.modifiers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Modifies a set.
 * 
 * @author dereekb
 */
public class RelationshipKeySetModifier extends RelationshipModifier {

	public RelationshipKeySetModifier() {};

	@Override
	public <T> void createRelation(ObjectifyModel<T> object) {
		Key<T> key = object.getKey();
		Set<Key<T>> set = this.getSetFromItem();
		set.add(key);
	}

	@Override
	public <T> void deleteRelation(ObjectifyModel<T> object) {
		Key<T> key = object.getKey();
		Set<Key<T>> set = this.getSetFromItem();
		set.remove(key);
	}

	@SuppressWarnings("unchecked")
	private <T> Set<Key<T>> getSetFromItem() {
		Method read = this.descriptor.getReadMethod();
		Set<Key<T>> set = null;

		try {
			set = (Set<Key<T>>) read.invoke(relationshipObject);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("Error occured while invoking set read on object '" + relationshipObject + "'.");
		}

		return set;
	}

}
