package com.thevisitcompany.gae.deprecated.model.mod.relationships;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.thevisitcompany.gae.deprecated.model.mod.relationships.modifiers.RelationshipKeyModifier;
import com.thevisitcompany.gae.deprecated.model.mod.relationships.modifiers.RelationshipModifier;

//TODO: Deprecate this any related elements.
@Retention(RetentionPolicy.RUNTIME)
@Target( { METHOD })
public @interface RelationshipField {

	/**
	 * Name of this relation.
	 *
	 * The relationship manager will attempt to pair relations with the same name and class.
	 * @return
	 */
	String[] name() default "rel";

	/**
	 * Type(s) that this field handles.
	 * @return
	 */
	Class<?>[] types();

	/**
	 * Class of the Relationship Modifier.
	 * @return
	 */
	Class<? extends RelationshipModifier> modifierClass() default RelationshipKeyModifier.class;

}
