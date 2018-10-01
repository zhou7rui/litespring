package org.litespring.context.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.annotation.AnnotationBeanDefinition;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.context.support.BeanNameGenerator;
import org.litespring.core.annotaion.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.utils.ClassUtils;
import org.litespring.utils.StringUtils;

import java.beans.Introspector;
import java.util.Set;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {


    @Override
    public String generatorBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if(definition instanceof AnnotationBeanDefinition){
            String beanName = determineBeanNameFromAnnotation((AnnotationBeanDefinition)definition);
            return  beanName;
        }

        return buildDefaultBeanName(definition,registry);

    }

    protected String determineBeanNameFromAnnotation(AnnotationBeanDefinition definition) {

        AnnotationMetadata amd = definition.getMetaData();

        Set<String> types = amd.getAnnotationTypes();

        String beanName = null;
        for(String type : types){
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if(attributes.get("value") != null){
                Object value = attributes.get("value");
                if(value instanceof  String){
                    String strVal = (String) value;
                    if(StringUtils.hasLength(strVal)){
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;

    }


    protected String buildDefaultBeanName(BeanDefinition definition,BeanDefinitionRegistry registry){
        return  buildDefaultBeanName(definition);
    }

    private String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }




}
