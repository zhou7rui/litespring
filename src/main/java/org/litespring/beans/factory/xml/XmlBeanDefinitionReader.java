/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {

    private static final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private static final String SCOPE_ATTRIBUTE = "scope";

    private BeanDefinitionRegistry beanDefinitionRegistry;


    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinition(Resource configFile) {
        InputStream in = null;
        try {
            in = configFile.getInputStream();
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            Element root = document.getRootElement();
            Iterator iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);
                if (element.attribute(SCOPE_ATTRIBUTE) != null) {
                    beanDefinition.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
                }
                this.beanDefinitionRegistry.registryBeanDefinition(id, beanDefinition);

            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException passing XML document failure");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
