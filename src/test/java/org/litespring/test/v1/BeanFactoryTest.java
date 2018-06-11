/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.test.v1;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

import static org.junit.Assert.*;

public class BeanFactoryTest {


    @Test
    public void testGetBean() {

        BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");

        BeanDefinition bd = beanFactory.getBeanDefinition("petStore");

        assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService) beanFactory.getBean("petStore");

        assertNotNull(petStoreService);

    }


    @Test
    public void testInvalidBean() {

        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            factory.getBean("invalidBean");

        } catch (Exception e) {
            return;
        }
        fail("expect BeanCreationException ");

    }

    @Test
    public void testInvalidXml() {

        try {
            new DefaultBeanFactory("xxxx.xml");
        } catch (Exception e) {
            return;
        }
        fail("expect BeanCreationException ");

    }


}
