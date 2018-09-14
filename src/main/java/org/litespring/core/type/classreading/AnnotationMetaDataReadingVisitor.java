package org.litespring.core.type.classreading;



import org.litespring.core.annotaion.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationMetaDataReadingVisitor extends ClassMetaDataReadingVisitor {

    private final Set<String> annotationSet = new LinkedHashSet<>(4);

    private final Map<String,AnnotationAttributes> attributesMap = new LinkedHashMap<>(4);

    public AnnotationMetaDataReadingVisitor() {

    }


    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        this.annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className, this.attributesMap);
    }

    public Set<String> getAnnotationTypes() {
        return annotationSet;
    }

    public AnnotationAttributes getAnnotationAttributes(String annotation) {
        return attributesMap.get(annotation);
    }

    public boolean hasAnnotation(String annotation) {
        return attributesMap.containsKey(annotation);
    }
}
