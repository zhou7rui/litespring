/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory.config;

public interface SingletonBeanRegistry {

    void registrySingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

}
