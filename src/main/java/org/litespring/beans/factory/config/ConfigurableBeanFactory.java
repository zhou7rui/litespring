/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory{

    void setBeanClassloader(ClassLoader classloader);

    ClassLoader getBeanClassloader();

}
