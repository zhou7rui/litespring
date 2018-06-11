/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.utils.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {

    private static  final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();


    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return beanDefinitionMap.get(beanID);
    }

    @Override
    public Object getBean(String beanID) {

        BeanDefinition beanDefinition =  getBeanDefinition(beanID);
        if(beanDefinition == null){
            throw new BeanCreationException("Bean Definition does not exist");
        }

        String beanClassName = beanDefinition.getBeanClassName();

        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        try {

            Class<?> beanClass =  classLoader.loadClass(beanClassName);

            return beanClass.newInstance();

        } catch (Exception e) {
            throw new BeanCreationException("create bean for " +beanClassName+ "failure");
        }

    }

    private  void loadBeanDefinition(String configFile) {
        InputStream in = null;
        try {
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
            in = classLoader.getResourceAsStream(configFile);
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            Element root = document.getRootElement();
            Iterator iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(id,beanClassName);
                this.beanDefinitionMap.put(id, beanDefinition);

            }
        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("IOException passing XML document failure");
        }finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
