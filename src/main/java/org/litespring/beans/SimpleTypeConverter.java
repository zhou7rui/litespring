package org.litespring.beans;

import org.litespring.beans.propertyeditors.CustomBooleanEditor;
import org.litespring.beans.propertyeditors.CustomNumberEditor;
import org.litespring.utils.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

public class SimpleTypeConverter implements TypeConverter {

    private Map<Class<?>, PropertyEditor> defaultEditors;

    public SimpleTypeConverter() {

    }

    @Override
    public <T> T converterIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {

        if (ClassUtils.isAssignableValue(requiredType,value)){
            return (T) value;
        }else if (value instanceof  String) {
            PropertyEditor editor = findDefaultEditor(requiredType);

            try {
                editor.setAsText((String) value);
            }catch (IllegalArgumentException e){
                throw new  TypeMismatchException(value,requiredType);
            }

            return (T) editor.getValue();
        }else {
            throw  new RuntimeException("Todo: can't convert value for" + value + "class:" + requiredType);
        }

    }


    private PropertyEditor findDefaultEditor(Class<?> requiredType){

        PropertyEditor editor = getDefaultEditor(requiredType);
        if(editor == null){

            throw new RuntimeException("Editor for " + requiredType + "has not been implemented");
        }

        return editor;
    }

    private PropertyEditor getDefaultEditor(Class<?> requiredType){

        if(defaultEditors == null){
            createDefaultEditors();
        }

        return defaultEditors.get(requiredType);
    }

    private void createDefaultEditors(){

        defaultEditors = new HashMap<>();
        defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class,true));
        defaultEditors.put(int.class, new CustomNumberEditor(Integer.class,true));


        defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));
        defaultEditors.put(boolean.class, new CustomBooleanEditor(true));
    }


}
