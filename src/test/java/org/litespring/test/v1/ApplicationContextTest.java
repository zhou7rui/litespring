/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.test.v1;

import static org.junit.Assert.*;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;

import java.nio.file.Paths;


public class ApplicationContextTest {


    @Test
    public void testGetBean() throws  Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v1.xml");

        PetStoreService service = (PetStoreService)context.getBean("petStore");

        assertNotNull(service);

    }

    @Test
    public void  testGetBeanFormFileSystemXmlApplicationContext(){

        String path = String.valueOf(Paths.get("src/test/resources/petstore-v1.xml"));

        ApplicationContext context = new FileSystemXmlApplicationContext(path);

        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");


    }


}
