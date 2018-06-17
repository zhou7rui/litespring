/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.context.support;

import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {


    public ClassPathXmlApplicationContext(String configFile) {
       super(configFile);
    }

    @Override
    public Resource getResourceByPath(String path) {
        return new ClassPathResource(path,getBeanClassloader());
    }


}
