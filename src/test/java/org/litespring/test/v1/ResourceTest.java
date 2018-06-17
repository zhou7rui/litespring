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
