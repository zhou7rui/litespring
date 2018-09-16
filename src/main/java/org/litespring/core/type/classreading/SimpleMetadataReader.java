package org.litespring.core.type.classreading;


import org.litespring.core.io.Resource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SimpleMetadataReader implements MetadataReader {

    private final Resource resource;

    private final ClassMetadata classMetadata;

    private final AnnotationMetadata annotationMetadata;

    public SimpleMetadataReader(Resource resource) throws IOException {

        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader;

        try {
            classReader = new ClassReader(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }

        }

        AnnotationMetaDataReadingVisitor visitor = new AnnotationMetaDataReadingVisitor();
        classReader.accept(visitor,ClassReader.SKIP_DEBUG);

        this.resource = resource;
        this.annotationMetadata = visitor;
        this.classMetadata = visitor;
    }


    public Resource getResource() {
        return resource;
    }

    public ClassMetadata getClassMetadata() {
        return classMetadata;
    }

    public AnnotationMetadata getAnnotationMetadata() {
        return annotationMetadata;
    }
}
