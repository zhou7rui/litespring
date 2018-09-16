package org.litespring.test.v4;

import org.junit.Test;
import org.litespring.core.annotaion.AnnotationAttributes;
import org.litespring.core.io.ClassPathResource;
import org.litespring.stereotype.Component;
import static org.junit.Assert.*;


public class MetadataReaderTest {

    @Test
    public void testGetMetaData() {
        ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");

        MetadataReader  reader = new SimpleMetadataReader(resource);

        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        assertTrue(annotationMetadata.hasAnnotation(annotation));
        AnnotationAttributes attributes = annotationMetadata.getAnnotationAttributes(annotation);
        assertEquals("petStore",attributes.get("value"));

    }
}
