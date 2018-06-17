/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.utils;

public class Assert {

    public static void notNull(Object obj,String message) {
        if(obj == null){
            throw new IllegalArgumentException(message);
        }

    }

}
