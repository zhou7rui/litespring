/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

public class BeanCreationException extends BeansException {

    private String beanName;

    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String beanName,String msg) {
        super("Error creating bean with name '" +beanName+ " : " + msg);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreationException(String beanName,String message, Throwable cause) {
        this(beanName,message);
        initCause(cause);
    }



    public String getBeanName() {
        return beanName;
    }
}
