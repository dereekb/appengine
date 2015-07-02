package com.thevisitcompany.gae.deprecated.model.mod.relationships.modifiers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Relationship Modifier that modifies the keys between objects.
 * @author dereekb
 */
public class RelationshipKeyModifier extends RelationshipModifier{

	public RelationshipKeyModifier(){};
	
	public <T> void createRelation(ObjectifyModel<T> object) {
		Key<T> key = object.getKey();
		this.writeKeyToItem(key);		
	}

	public <T> void deleteRelation(ObjectifyModel<T> object) {
		this.writeKeyToItem(null);
	}

	private <T> void writeKeyToItem(Key<T> key){
		Method write = this.descriptor.getWriteMethod();
		
		try {
			write.invoke(this.relationshipObject, new Object[]{ key });
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException("Error occured while invoking write of key '" + key + "' on object '" + this.relationshipObject + "' using method: " + write);
		}
	}
}
