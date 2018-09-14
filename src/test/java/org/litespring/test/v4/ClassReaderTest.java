package org.litespring.test.v4;


import org.junit.Test;
import org.litespring.core.annotaion.AnnotationAttributes;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.type.classreading.AnnotationMetaDataReadingVisitor;
import org.litespring.core.type.classreading.ClassMetaDataReadingVisitor;
import org.springframework.asm.ClassReader;

import static org.junit.Assert.*;

import java.io.IOException;

public class ClassReaderTest {

    @Test
    public void testGetClassMetaData() throws IOException{

        ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        ClassMetaDataReadingVisitor visitor = new ClassMetaDataReadingVisitor();

        reader.accept(visitor,ClassReader.SKIP_DEBUG);

        assertFalse(visitor.isAbstract());
        assertFalse(visitor.isInterface());
        assertFalse(visitor.isFinal());
        assertNotNull(visitor.getSuperClassName());
        assertEquals(visitor.getInterfaces().length,0);

    }

    @Test
    public void testGetAnnotation() throws Exception{

        ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetaDataReadingVisitor visitor = new AnnotationMetaDataReadingVisitor();

        reader.accept(visitor,ClassReader.SKIP_DEBUG);
        String annotation = "org.litespring.stereotype.Component";
        assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

        assertEquals("petStore",attributes.get("value"));



    }


}
