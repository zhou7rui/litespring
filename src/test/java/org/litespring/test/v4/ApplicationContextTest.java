package org.litespring.test.v4;


import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v4.PetStoreService;

public class ApplicationContextTest {


    @Test
    //TODO
    public void testGetBeanProperty() {
       ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v4.xml");

       PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");

       assertNotNull(petStoreService.getAccountDao());
       assertNotNull(petStoreService.getItemDao());


    }

}
