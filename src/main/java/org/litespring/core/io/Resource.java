/*
 * Copyright (C) zhourui Company, 2017.All Rights Reserved.
 */

package org.litespring.core.io;


import java.io.IOException;
import java.io.InputStream;

public interface Resource {

    InputStream getInputStream() throws IOException;

    String getDescription();

}
