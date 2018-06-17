/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

    @Override
    public void registrySingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "beanName must not be null");

        Object object = this.singletonObjects.get(beanName);
        if (object != null) {
            throw new IllegalStateException("could not register object [" + singletonObject + "] " +
                    "under bean name '" + beanName + "': there is already object [" + singletonObject + "]");
        }
        this.singletonObjects.put(beanName, singletonObject);

    }

    @Override
    public Object getSingleton(String beanName) {

        return this.singletonObjects.get(beanName);
    }
}
