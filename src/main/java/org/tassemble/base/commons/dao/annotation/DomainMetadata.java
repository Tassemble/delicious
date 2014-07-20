package org.tassemble.base.commons.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DomainMetadata {

    Class<?> domainClass();

    String tableName() default "";

    String idProperty() default "";

    String idColumn() default "";

    String policyIdProperty() default "";

    String policyIdColumn() default "";

}
