/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.core.io;

import org.litespring.utils.Assert;
import org.litespring.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

    private String path;

    private ClassLoader classLoader;

    public ClassPathResource(String configFile) {
        this(configFile, null);
    }

    public ClassPathResource(String configFile, ClassLoader classLoader) {
        Assert.notNull(configFile, "ConfigFile must not be null");
        this.path = configFile;
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }


    @Override
    public InputStream getInputStream() throws IOException {

        InputStream inputStream = classLoader.getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException(path + "cannot be opened");
        }

        return inputStream;
    }

    @Override
    public String getDescription() {
        return this.path;
    }
}
