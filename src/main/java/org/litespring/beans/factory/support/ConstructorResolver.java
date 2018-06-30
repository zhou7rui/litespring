package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.ConstructorArgument.ValueHolder;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorResolver {


    private final ConfigurableBeanFactory factory;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ConstructorResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }


    public Object autowireConstructor(final BeanDefinition beanDefinition) {

        Constructor<?> constructorToUse = null;

        Object[] argsToUse = null;

        Class<?> beanClass = null;


        try {

            beanClass = factory.getBeanClassloader().loadClass(beanDefinition.getBeanClassName());

        } catch (ClassNotFoundException e) {
            throw new BeanCreationException( beanDefinition.getID(), "Instantiation of bean failed, can't resolve class", e);
        }


        Constructor<?>[] candidates = beanClass.getConstructors();

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(factory);

        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        ConstructorArgument argument = beanDefinition.getConstructorArgument();

        for (int i = 0; i < candidates.length; i++) {

            Class<?>[] parameterTypes = candidates[i].getParameterTypes();

            if (parameterTypes.length != argument.getArgumentValuesCount()) {
                continue;
            }

            argsToUse = new Object[parameterTypes.length];

            // 参数的值是否匹配构造函数 记录构造函数
            boolean result = this.valuesMatchTypes(parameterTypes, argument.getArgumentValues(),
                    argsToUse, valueResolver, typeConverter);


            if (result) {
                constructorToUse = candidates[i];
                break;
            }

        }

        // 没有对应匹配的构造函数
        if (constructorToUse == null) {
            throw new BeanCreationException( beanDefinition.getID(), "can't find a apporiate constructor");
        }


        try {
            //  对应构造函数反射出对应的实例
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(beanDefinition.getID(), "can't find a create instance using " + constructorToUse);
        }


    }

    private boolean valuesMatchTypes(Class<?>[] parameterTypes, List<ValueHolder> argumentValues, Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver, SimpleTypeConverter typeConverter) {

        for (int i = 0; i < parameterTypes.length; i++) {

            ValueHolder valueHolder = argumentValues.get(i);
            // 获取参数的值
            Object originalValue = valueHolder.getValue();
            try {

                // 将参数的值转换成实际的值
                Object resolveValue = valueResolver.resolveValueIfNecessary(originalValue);
                Object covertObject = typeConverter.converterIfNecessary(resolveValue, parameterTypes[i]);
                // 实际的值 记录下来
                argsToUse[i] = covertObject;
            } catch (Exception e) {
                // 转换失败 表示类型不匹配 失败 返回false
                logger.error(e.getMessage());
                return false;
            }
        }

        return true;
    }


}
