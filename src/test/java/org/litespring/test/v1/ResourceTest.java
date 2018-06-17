/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ResourceTest {


    @Test
    public void testClassPathResource() throws Exception{

        Resource resource = new ClassPathResource("petstore-v1.xml");
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            assertNotNull(inputStream);
        }finally {
            inputStream.close();
        }

    }


    @Test
    public void testFileSystemResource() throws Exception{
        String path = String.valueOf(Paths.get("src/test/resources/petstore-v1.xml"));

        Resource resource = new FileSystemResource(path);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            assertNotNull(inputStream);
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
        }

    }



}
