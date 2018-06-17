/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.utils.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {


    private ClassLoader classLoader;


    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory() {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return beanDefinitionMap.get(beanID);
    }

    @Override
    public void registryBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID, beanDefinition);
    }


    @Override
    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = getBeanDefinition(beanID);
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        if (beanDefinition.isSingleton()) {
            Object bean = getSingleton(beanID);
            if(bean == null) {
                bean = createBean(beanDefinition);
                registrySingleton(beanID,bean);
            }

            return bean;
        }
       return createBean(beanDefinition);

    }

    private Object createBean(BeanDefinition beanDefinition){

        String beanClassName = beanDefinition.getBeanClassName();

        ClassLoader classLoader = getBeanClassloader();
        try {

            Class<?> beanClass = classLoader.loadClass(beanClassName);

            return beanClass.newInstance();

        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + "failure");
        }
    }


    @Override
    public void setBeanClassloader(ClassLoader classloader) {
        this.classLoader = classloader;
    }

    @Override
    public ClassLoader getBeanClassloader() {
        return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
    }
}
