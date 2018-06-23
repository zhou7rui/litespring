package org.litespring.beans;

public interface TypeConverter {

    <T> T converterIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
