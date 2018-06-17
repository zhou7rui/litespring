/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanID);

    void registryBeanDefinition(String BeanID, BeanDefinition beanDefinition);


}
