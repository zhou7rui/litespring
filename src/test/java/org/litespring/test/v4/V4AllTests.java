package org.litespring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ClassReaderTest.class,
        PackageResourceLoaderTest.class
})
public class V4AllTests {
}
