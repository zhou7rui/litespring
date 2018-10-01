package org.litespring.core.type;

import org.litespring.core.annotaion.AnnotationAttributes;

import java.util.Set;

public interface AnnotationMetadata extends ClassMetadata{

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);

}
