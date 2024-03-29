package com.dereekb.gae.model.general.time.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EncodedWeekSpanValidator.class })
public @interface ValidEncodedWeekSpan {

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String message() default "Invalid Week Spans";

}