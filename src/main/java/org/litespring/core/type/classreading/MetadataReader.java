package org.litespring.core.type.classreading;

import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;

public interface MetadataReader {
    ClassMetadata getClassMetadata();
    AnnotationMetadata getAnnotationMetadata();


}
