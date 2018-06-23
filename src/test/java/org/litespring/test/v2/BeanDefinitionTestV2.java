package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;

import java.util.List;

public class BeanDefinitionTestV2 {


    @Test
    public void testGetBeanDefinition(){
        DefaultBeanFactory factory = new DefaultBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));

        BeanDefinition definition = factory.getBeanDefinition("petStore");

        List<PropertyValue> pvs = definition.getPropertyValues();

        Assert.assertTrue(pvs.size() == 4);
        {
            PropertyValue  propertyValue = getPropertyValue("accountDao",pvs);
            Assert.assertNotNull(propertyValue);

            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);

        }
        {
            PropertyValue propertyValue = getPropertyValue("itemDao", pvs);

            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);

        }


    }


    private  PropertyValue getPropertyValue(String name,List<PropertyValue> list) {

        for (PropertyValue propertyValue : list) {

            if (name.equals(propertyValue.getName())) {

                return propertyValue;
            }
        }

        return null;
    }




}
