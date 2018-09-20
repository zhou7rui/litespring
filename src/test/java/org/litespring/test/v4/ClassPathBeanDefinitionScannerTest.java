package org.litespring.test.v4;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.stereotype.Component;
import static org.junit.Assert.*;

public class ClassPathBeanDefinitionScannerTest {

    @Test
    // TODO
    public void testParseScanedBean() {
        DefaultBeanFactory factory = new DefaultBeanFactory();

        String basePackages = "org/litespring/service/v4/PetStoreService.class";

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doCan(scanner);

        String annotation = Component.class.getName();
        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            assertTrue(bd instanceof ScannerGenericBeanDefinition);
            ScannerGenericBeanDefinition sbd = (ScannerGenericBeanDefinition)bd;
            AnnotationMetadata annotationMetadata = sbd.getMetaData();


        }


    }

}
