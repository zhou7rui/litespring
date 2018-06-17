/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;

    private String beanClassName;

    private  String scope = SCOPE_DEFAULT;


    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public boolean isSingleton() {
        return SCOPE_singleton.equals(getScope()) || SCOPE_DEFAULT.equals(getScope());
    }

    @Override
    public boolean isPrototype() {
        return SCOPE_prototype.equals(getScope());
    }

    @Override
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }
}
