package org.litespring.context.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.context.support.BeanNameGenerator;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.stereotype.Component;
import org.litespring.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathBeanDefinitionScanner {


    private final BeanDefinitionRegistry registry;


    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packageToScan) {
        String[] basePackages = StringUtils.tokenizeToStringArray(packageToScan, ",");

        Set<BeanDefinition> beanDefinitionSet = new LinkedHashSet<>();

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);

            for (BeanDefinition candidate : candidates) {
                beanDefinitionSet.add(candidate);
                registry.registryBeanDefinition(candidate.getID(), candidate);
            }

        }
        return beanDefinitionSet;
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) {

        Set<BeanDefinition> candidates = new LinkedHashSet<>();

        Resource[] resources = this.resourceLoader.getResources(basePackage);

        for (Resource resource : resources) {
            try {
                MetadataReader metadataReader = new SimpleMetadataReader(resource);
                if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                    ScannerGenericBeanDefinition sbd = new ScannerGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                    String beanName = this.beanNameGenerator.generatorBeanName(sbd,this.registry);
                    sbd.setId(beanName);
                    candidates.add(sbd);
                }
            } catch (IOException e) {
                throw new BeanDefinitionStoreException("Failed to read candidate component class:" + resource, e);
            }

        }

        return candidates;
    }


}
