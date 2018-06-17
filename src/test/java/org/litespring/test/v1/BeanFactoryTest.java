/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.test.v1;

import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v1.PetStoreService;

import static org.junit.Assert.*;

public class BeanFactoryTest {

    DefaultBeanFactory beanFactory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
         beanFactory = new DefaultBeanFactory();
         reader = new XmlBeanDefinitionReader(beanFactory);
    }

    @Test
    public void testGetBean() {

        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition bd = beanFactory.getBeanDefinition("petStore");

        assertTrue(bd.isSingleton());
        assertFalse(bd.isPrototype());

        assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

        assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService) beanFactory.getBean("petStore");

        assertNotNull(petStoreService);

        PetStoreService petStoreService1 = (PetStoreService) beanFactory.getBean("petStore");

        assertEquals(petStoreService,petStoreService1);

        BeanDefinition bd1 = beanFactory.getBeanDefinition("prototypePetStore");

        assertTrue(bd1.isPrototype());
        assertFalse(bd1.isSingleton());
        PetStoreService prototypePetStore = (PetStoreService) beanFactory.getBean("prototypePetStore");

        assertNotNull(prototypePetStore);
        PetStoreService prototypePetStore1 = (PetStoreService) beanFactory.getBean("prototypePetStore");

        assertNotEquals(petStoreService,prototypePetStore1);






    }


    @Test
    public void testInvalidBean() {

        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        try {
            beanFactory.getBean("invalidBean");

        } catch (Exception e) {
            return;
        }
        fail("expect BeanCreationException ");

    }

    @Test
    public void testInvalidXml() {
        try {
            reader.loadBeanDefinition(new ClassPathResource("xxx.xml"));
        } catch (Exception e) {
            return;
        }
        fail("expect BeanCreationException ");

    }


}
