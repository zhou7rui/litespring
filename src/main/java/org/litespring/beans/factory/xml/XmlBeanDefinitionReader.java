/*
 * MIT License
 * Copyright (c) 2018 zhourui
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import org.litespring.core.io.Resource;
import org.litespring.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {

    private static final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private static final String SCOPE_ATTRIBUTE = "scope";

    private static final String PROPERTY_ELEMENT = "property";

    private static final String NAME_ATTRIBUTE = "name";

    private static final String REF_ATTRIBUTE = "ref";

    private static final String VALUE_ATTRIBUTE = "value";

    private static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    private static final String TYPE_ATTRIBUTE = "type";

    private BeanDefinitionRegistry beanDefinitionRegistry;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";


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

                String  namespaceURI = element.getNamespaceURI();
                if(this.isDefaultNamespace(namespaceURI)){
                    this.parseDefaultElements(element);
                }else if (isContextNamespace(namespaceURI)){
                    this.parseContextElements(element);
                }
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

    private boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    public boolean isContextNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }


    public void parseDefaultElements(Element element){
        String id = element.attributeValue(ID_ATTRIBUTE);
        String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
        BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);
        if (element.attribute(SCOPE_ATTRIBUTE) != null) {
            beanDefinition.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
        }

        parseConstructorArgElements(element,beanDefinition);

        parsePropertyElement(element, beanDefinition);

        this.beanDefinitionRegistry.registryBeanDefinition(id, beanDefinition);
    }

    public void parseContextElements(Element element){
        String basePackage = element.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        scanner.doScan(basePackage);
    }



    public void parseConstructorArgElements(Element element, BeanDefinition beanDefinition) {

        Iterator iterator = element.elementIterator(CONSTRUCTOR_ARG_ELEMENT);


        while (iterator.hasNext()) {

            Element conArgElement = (Element) iterator.next();

            parseConstructorArgElement(conArgElement, beanDefinition);
        }


    }

    public void parseConstructorArgElement(Element element, BeanDefinition beanDefinition) {

        String typeAttr = element.attributeValue(TYPE_ATTRIBUTE);

        String nameAttr = element.attributeValue(NAME_ATTRIBUTE);

        Object value = parsePropertyValue(element, beanDefinition, null);

        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);

        if (StringUtils.hasLength(typeAttr)){
            valueHolder.setType(typeAttr);
        }

        if (StringUtils.hasLength(nameAttr)){
            valueHolder.setType(nameAttr);
        }


        beanDefinition.getConstructorArgument().addArgumentValues(valueHolder);


    }


    public void parsePropertyElement(Element element, BeanDefinition beanDefinition) {

        Iterator iterator = element.elementIterator(PROPERTY_ELEMENT);

        while (iterator.hasNext()) {

            Element propElement = (Element) iterator.next();

            String propertyName = propElement.attributeValue(NAME_ATTRIBUTE);

            if (!StringUtils.hasLength(propertyName)) {
                logger.error("Tag 'property' must have a 'name' attribute");
                return;
            }

            Object obj = parsePropertyValue(propElement, beanDefinition, propertyName);

            PropertyValue propertyValue = new PropertyValue(propertyName, obj);

            beanDefinition.getPropertyValues().add(propertyValue);

        }


    }

    public Object parsePropertyValue(Element propElement, BeanDefinition beanDefinition, String propertyName) {

        String elementName = (propertyName != null) ? "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";


        boolean hasRefAttribute = (propElement.attribute(REF_ATTRIBUTE) != null);

        boolean hasValueAttribute = (propElement.attribute(VALUE_ATTRIBUTE) != null);

        if (hasRefAttribute) {

            String refName = propElement.attributeValue(REF_ATTRIBUTE);

            if (!StringUtils.hasText(refName)) {
                logger.error("{} contains empty 'ref' attribute");
            }

            return new RuntimeBeanReference(refName);


        } else if (hasValueAttribute) {

            return new TypedStringValue(propElement.attributeValue(VALUE_ATTRIBUTE));
        } else {

            throw new RuntimeException(elementName + "must specify a ref of value");
        }

    }


}
