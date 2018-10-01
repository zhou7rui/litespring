package org.litespring.context.annotation;

import org.litespring.beans.factory.annotation.AnnotationBeanDefinition;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.type.AnnotationMetadata;

public class ScannerGenericBeanDefinition extends GenericBeanDefinition implements AnnotationBeanDefinition {

    private  final  AnnotationMetadata metadata;


    public ScannerGenericBeanDefinition(AnnotationMetadata metaData) {
        super();
        this.metadata = metaData;

        setBeanClassName(this.metadata.getClassName());
    }

    public AnnotationMetadata getMetaData() {
        return metadata;
    }
}
