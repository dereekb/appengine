package com.thevisitcompany.gae.deprecated.model.mod.relationships.modifiers;

import java.beans.PropertyDescriptor;

import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModel;

public abstract class RelationshipModifier {

	protected Object relationshipObject; // Object to invoke descriptor onto
	protected PropertyDescriptor descriptor; // Descriptor annotated as the Relationship Field

	public Object getRelationshipObject() {
		return relationshipObject;
	}

	public void setRelationshipObject(Object relationshipObject) {
		this.relationshipObject = relationshipObject;
	}

	public PropertyDescriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(PropertyDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public abstract <T> void createRelation(ObjectifyModel<T> object);

	public abstract <T> void deleteRelation(ObjectifyModel<T> object);
}
