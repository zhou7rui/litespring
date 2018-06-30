package org.litespring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ConstructorArgument {

    private final List<ValueHolder> argumentValues = new LinkedList<>();


    public void addArgumentValues(ValueHolder holder) {
        this.argumentValues.add(holder);

    }

    // 返回一个不可修改的list
    public List<ValueHolder> getArgumentValues(){
        return Collections.unmodifiableList(argumentValues);
    }

    public int getArgumentValuesCount(){
        return argumentValues.size();
    }

    public boolean isEmpty(){
        return argumentValues.isEmpty();
    }


    public void clear(){
        argumentValues.clear();
    }


    public static class ValueHolder {

        private Object value;

        private String type;

        private String name;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public ValueHolder(Object value, String type) {
            this.value = value;
            this.type = type;
        }

        public ValueHolder(Object value, String type, String name) {
            this.value = value;
            this.type = type;
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



}
