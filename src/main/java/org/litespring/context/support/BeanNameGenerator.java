package org.litespring.context.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;

public interface BeanNameGenerator {

    String generatorBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry);
}
