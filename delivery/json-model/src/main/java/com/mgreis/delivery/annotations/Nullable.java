package com.mgreis.delivery.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

/**
 * An annotation meant to be used by org.immutables when generating the Immutable classes.
 *
 * This annotation instructs the processor to consider the annotated element as being able
 * to hold (field), receive (set method, constructor) or pass (get method) a {@code null}.
 *
 * This is used so that the generated immutable classes can represent missing values
 * in transactions for fields that are not mandatory.
 *
 * This could also be used to annotate non-null fields so that they can be null and later fail
 * a validation (for validation and testing purposes).
 *
 * @author Mario Pereira
 * @since 1.0.0
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.SOURCE)
@Inherited
public @interface Nullable {
}
