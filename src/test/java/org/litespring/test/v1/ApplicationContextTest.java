/*
 * MIT License
 * Copyright (c) 2018 zhourui
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

        assertNotNull(petStoreService);


    }


}
