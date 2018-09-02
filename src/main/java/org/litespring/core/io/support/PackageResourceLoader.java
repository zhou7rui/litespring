package org.litespring.core.io.support;

import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;
import org.litespring.utils.Assert;
import org.litespring.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class PackageResourceLoader {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Resource[] getResources(String basePackage) {

        Assert.notNull(basePackage, "basePackage must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(location);
        File rootDir = new File(url.getFile());
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] resources = new Resource[matchingFiles.size()];
        int i = 0;
        for (File file : matchingFiles) {
            resources[i++] = new FileSystemResource(file);
        }

        return resources;

    }

    /**
     * 取得匹配结果
     * @param rootDir
     * @return
     */
    private Set<File> retrieveMatchingFiles(File rootDir) {

        if (!rootDir.exists()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it ");
            }
            return Collections.emptySet();
        }
        if(!rootDir.isDirectory()){
            if(logger.isWarnEnabled()){
                logger.warn("Could not retrieve contents of directory [" + rootDir.getAbsolutePath() + "]");
            }
            Collections.emptySet();
        }
        if (!rootDir.canRead()) {
            if (logger.isWarnEnabled()) {
                logger.warn("");
            }
        }
        Set<File> result = new LinkedHashSet<>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;


    }

    /**
     * 递归获取匹配的文件
     * @param dir 文件夹
     * @param result 匹配结果
     */
    private void doRetrieveMatchingFiles(File dir, Set<File> result) {
        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
            }
            return;
        }

        for (File content : dirContents) {
            if (content.isDirectory()) {
                if (!content.canRead()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
                                "] because the application is not allowed read the directory");
                    }

                }else {
                    doRetrieveMatchingFiles(content,result);
                }
            }else {
                result.add(content);
            }
        }
    }


    public ClassLoader getClassLoader() {
        return ClassUtils.getDefaultClassLoader();
    }
}
