package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v2.PetStoreService;

public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty() {

        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v2.xml");

        PetStoreService storeService = (PetStoreService) context.getBean("petStore");

        Assert.assertNotNull(storeService.getAccountDao());

        Assert.assertNotNull(storeService.getItemDao());

    }


}
