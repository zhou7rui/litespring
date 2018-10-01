package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.core.type.AnnotationMetadata;

public interface AnnotationBeanDefinition extends BeanDefinition {
     AnnotationMetadata getMetaData();

}
