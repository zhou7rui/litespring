package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {

    private final DefaultBeanFactory factory;


    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }


    public Object resolveValueIfNecessary(Object value) {

        if (value instanceof RuntimeBeanReference) {

            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            String beanName = reference.getBeanName();
            return factory.getBean(beanName);
        }else if (value instanceof TypedStringValue){

            TypedStringValue stringValue = (TypedStringValue) value;
            return stringValue.getValue();
        }else {
            // TODO
            throw new RuntimeException("the value " + value + "has not implemented");
        }

    }
}
