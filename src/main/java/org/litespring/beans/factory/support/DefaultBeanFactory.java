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

package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.utils.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
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
            if (bean == null) {
                bean = createBean(beanDefinition);
                registrySingleton(beanID, bean);
            }

            return bean;
        }
        return createBean(beanDefinition);

    }

    private Object createBean(BeanDefinition beanDefinition) {

        // 创建实例
        Object bean = instantiateBean(beanDefinition);

        // 设置实例属性
        populateBean(beanDefinition, bean);


        return bean;
    }

    private Object instantiateBean(BeanDefinition beanDefinition) {

        String beanClassName = beanDefinition.getBeanClassName();

        ClassLoader classLoader = getBeanClassloader();

        try {
            Class<?> beanClass = classLoader.loadClass(beanClassName);
            return beanClass.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + "failure");
        }

    }

    private void populateBean(BeanDefinition bd, Object bean) {
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        if (propertyValues == null && propertyValues.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();

        try {
            // 使用java Beans 得到bean信息, 获取到set 方法 通反射调用set方法
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            for (PropertyValue value : propertyValues) {

                String propertyName = value.getName();

                Object originalValue = value.getValue();

                Object resolveValue = resolver.resolveValueIfNecessary(originalValue);

                for (PropertyDescriptor pd : pds){
                    if(pd.getName().equals(propertyName)){
                        Object covertObject = converter.converterIfNecessary(resolveValue,pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean,covertObject);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]");
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
