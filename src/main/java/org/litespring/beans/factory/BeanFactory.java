/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

public interface BeanFactory {


    BeanDefinition getBeanDefinition(String beanID);

    Object getBean(String beanID);

}
