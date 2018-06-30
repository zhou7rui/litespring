package org.litespring.test.v3;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.ConstructorArgument.ValueHolder;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class BeanDefinitionTestV3 {


    @Test
    public void testGetBeanDefinition() {

        DefaultBeanFactory factory = new DefaultBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v3.xml"));

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");

        assertEquals(beanDefinition.getBeanClassName(), "org.litespring.service.v3.PetStoreService");

        ConstructorArgument args = beanDefinition.getConstructorArgument();

        List<ValueHolder> valueHolders = args.getArgumentValues();

        assertEquals(3,valueHolders.size());

        RuntimeBeanReference ref1 = (RuntimeBeanReference)valueHolders.get(0).getValue();
        assertEquals(ref1.getBeanName(), "accountDao");

        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        assertEquals(ref2.getBeanName(), "itemDao");

        TypedStringValue stringValue = (TypedStringValue)valueHolders.get(2).getValue();

        assertEquals("7", stringValue.getValue());


    }


}
