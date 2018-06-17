/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;

import org.litespring.core.io.Resource;
import org.litespring.utils.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext{

    private String path;
    private DefaultBeanFactory factory = null;

    private ClassLoader classLoader;

    AbstractApplicationContext(String path){
        factory = new DefaultBeanFactory();
        factory.setBeanClassloader(getBeanClassloader());
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(getResourceByPath(path));
        this.path = path;
    }

    protected abstract Resource getResourceByPath(String path);

    @Override
    public Object getBean(String beanID) {
        return  factory.getBean(beanID);
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
