package org.litespring.beans;

public class PropertyValue {

    private final String name;

    private final Object value;

    private boolean converted = false;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public synchronized boolean isConverted() {
        return converted;
    }


}
