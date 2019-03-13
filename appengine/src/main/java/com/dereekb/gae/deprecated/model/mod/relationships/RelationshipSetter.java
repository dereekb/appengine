package com.thevisitcompany.gae.deprecated.model.mod.relationships;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.thevisitcompany.gae.deprecated.model.mod.relationships.modifiers.RelationshipModifier;
import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Creates a reusable editor for a given class for creating relations between
 * objects.
 * 
 * @author dereekb
 */
public class RelationshipSetter<T> {

	private static class DescriptorSet<T> {

		private final String name;
		private final Class<?> target;
		private final PropertyDescriptor descriptor;
		private final RelationshipModifier modifier;

		public DescriptorSet(String name, Class<?> target, PropertyDescriptor descriptor) {
			this.name = name;
			this.target = target;
			this.descriptor = descriptor;
			this.modifier = this.buildModifier();
		}

		public boolean matchesNeed(Class<?> targetType,
		                           String name) {
			boolean matches = false;

			if (this.target.equals(targetType)) {
				if (name == null) {
					matches = true;
				} else {
					matches = name.equals(this.name);
				}
			}

			return matches;
		}

		public void createRelation(ObjectifyModel<T> object,
		                           ObjectifyModel<?> target) {
			RelationshipModifier modifier = this.getModifier(object);
			modifier.createRelation(target);
		}

		public void deleteRelation(ObjectifyModel<T> object,
		                           ObjectifyModel<?> target) {
			RelationshipModifier modifier = this.getModifier(object);
			modifier.deleteRelation(object);
		}

		private RelationshipModifier getModifier(ObjectifyModel<T> object) {
			this.modifier.setRelationshipObject(object);
			return this.modifier;
		}

		private RelationshipModifier buildModifier() {

			Method readMethod = this.descriptor.getReadMethod();
			RelationshipField fieldAnnotation = readMethod.getAnnotation(RelationshipField.class);
			Class<? extends RelationshipModifier> modifierClass = fieldAnnotation.modifierClass();
			RelationshipModifier modifier = null;

			try {
				modifier = modifierClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Could not initialize RelationshipModifier of type '" + modifierClass + "'.");
			}

			modifier.setDescriptor(this.descriptor);

			return modifier;
		}
	}

	private final Class<T> type;
	private DescriptorSet<T> currentDescriptor;

	public RelationshipSetter(Class<T> type) {
		this.type = type;
	}

	public static <T> RelationshipSetter<T> builder(Class<T> builder) {
		return new RelationshipSetter<T>(builder);
	}

	public void createRelation(ObjectifyModel<T> object,
	                           ObjectifyModel<?> target) {
		this.createRelation(object, target, null);
	}

	public void createRelation(ObjectifyModel<T> object,
	                           ObjectifyModel<?> target,
	                           String name) {
		Class<?> targetType = target.getClass();
		DescriptorSet<T> descriptorSet = this.getCurrentDescriptor(targetType, name);
		descriptorSet.createRelation(object, target);
	}

	public void deleteRelation(ObjectifyModel<T> object,
	                           ObjectifyModel<?> target) {
		this.deleteRelation(object, target, null);
	}

	public void deleteRelation(ObjectifyModel<T> object,
	                           ObjectifyModel<?> target,
	                           String name) {
		Class<?> targetType = target.getClass();
		DescriptorSet<T> descriptorSet = this.getCurrentDescriptor(targetType, name);
		descriptorSet.deleteRelation(object, target);
	}

	public DescriptorSet<T> getCurrentDescriptor(Class<?> targetType,
	                                             String name) {
		DescriptorSet<T> descriptorSet = this.currentDescriptor;

		if (descriptorSet == null || (descriptorSet.matchesNeed(targetType, name) == false)) {
			PropertyDescriptor descriptor = this.findAcceptableDescriptor(targetType, name);
			descriptorSet = new DescriptorSet<T>(name, targetType, descriptor);
			this.currentDescriptor = descriptorSet;
		}

		return descriptorSet;
	}

	private PropertyDescriptor findAcceptableDescriptor(Class<?> targetType,
	                                                    String name) {

		BeanInfo info = null;

		try {
			info = Introspector.getBeanInfo(this.type, Object.class);
		} catch (IntrospectionException e) {
			throw new RuntimeException("Introspector failed.");
		}

		PropertyDescriptor[] properties = info.getPropertyDescriptors();
		PropertyDescriptor descriptor = null;

		for (PropertyDescriptor property : properties) {

			Method readMethod = property.getReadMethod();

			if (readMethod != null) {
				RelationshipField fieldAnnotation = readMethod.getAnnotation(RelationshipField.class);

				if (fieldAnnotation != null) {
					if (annotationContainsIdentifier(targetType, fieldAnnotation)) {
						if (name == null || (name != null && annotationContainsName(name, fieldAnnotation))) {
							descriptor = property;
							break;
						}
					}
				}
			}
		}

		if (descriptor == null) {
			throw new RuntimeException("No field was found in class '" + this.type + "' with a relation to '"
			        + targetType + "' and name '" + name + "'.");
		}

		return descriptor;
	}

	private static boolean annotationContainsIdentifier(Class<?> relationId,
	                                                    RelationshipField fieldAnnotation) {

		Class<?>[] identifiers = fieldAnnotation.types();

		boolean contained = false;

		for (Class<?> type : identifiers) {
			if (relationId.equals(type)) {
				contained = true;
				break;
			}
		}

		return contained;
	}

	private static boolean annotationContainsName(String name,
	                                              RelationshipField fieldAnnotation) {

		String[] fieldNames = fieldAnnotation.name();

		boolean contained = false;

		for (String fieldName : fieldNames) {
			if (name.equals(fieldName)) {
				contained = true;
				break;
			}
		}

		return contained;
	}
}
