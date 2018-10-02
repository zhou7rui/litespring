package org.litespring.test.v4;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import org.litespring.context.annotation.ScannerGenericBeanDefinition;
import org.litespring.core.annotaion.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.stereotype.Component;
import static org.junit.Assert.*;

public class ClassPathBeanDefinitionScannerTest {

    @Test
    public void testParseScanedBean() {
        DefaultBeanFactory factory = new DefaultBeanFactory();

        String basePackages = "org/litespring/service/v4,org.litespring.dao.v4";

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackages);

        String annotation = Component.class.getName();
        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            assertTrue(bd instanceof ScannerGenericBeanDefinition);
            ScannerGenericBeanDefinition sbd = (ScannerGenericBeanDefinition) bd;
            AnnotationMetadata annotationMetadata = sbd.getMetaData();

            assertTrue(annotationMetadata.hasAnnotation(annotation));

            AnnotationAttributes attributes = annotationMetadata.getAnnotationAttributes(annotation);
            assertEquals("petStore", attributes.get("value"));
        }
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("accountDao");
            assertTrue(beanDefinition instanceof ScannerGenericBeanDefinition);

            ScannerGenericBeanDefinition sbd = (ScannerGenericBeanDefinition) beanDefinition;
            AnnotationMetadata amd = sbd.getMetaData();
            assertTrue(amd.hasAnnotation(annotation));


        }
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("itemDao");
            assertTrue(beanDefinition instanceof ScannerGenericBeanDefinition);

            ScannerGenericBeanDefinition sbd = (ScannerGenericBeanDefinition) beanDefinition;
            AnnotationMetadata amd = sbd.getMetaData();
            assertTrue(amd.hasAnnotation(annotation));
        }


    }

}
