package org.litespring.beans.propertyeditors;

import org.litespring.utils.StringUtils;

import java.beans.PropertyEditorSupport;

public class CustomBooleanEditor extends PropertyEditorSupport {


    public static final String VALUE_TRUE = "true";
    public static final String VALUE_FALSE = "false";

    public static final String VALUE_ON = "on";
    public static final String VALUE_OFF = "off";

    public static final String VALUE_YES = "yes";
    public static final String VALUE_NO = "no";

    public static final String VALUE_1 = "1";
    public static final String VALUE_0 = "0";


//    private final String trueString;
//
//    private final String falseString;

    private final boolean allowEmpty;

    public CustomBooleanEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }


//    public CustomBooleanEditor(String trueString, String falseString, boolean allowEmpty) {
//        this.trueString = trueString;
//        this.falseString = falseString;
//        this.allowEmpty = allowEmpty;
//    }


    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String input = (text != null) ?  text.trim() : null;

        if(this.allowEmpty && StringUtils.hasLength(input)){
            //Treat empty String as null value
            setValue(null);
        }

        if(VALUE_TRUE.equals(text) || VALUE_ON.equals(text) || VALUE_1.equals(text) || VALUE_YES.equals(text)){

            setValue(Boolean.TRUE);

        }else if (VALUE_FALSE.equals(text) || VALUE_OFF.equals(text) || VALUE_0.equals(text) || VALUE_NO.equals(text)){
            setValue(Boolean.FALSE);
        }else {

            throw new IllegalArgumentException("Invalid boolean value [" + text + "]");

        }

    }


    @Override
    public String getAsText() {

        if (Boolean.TRUE.equals(getValue())){
            return VALUE_TRUE;
        }else if (Boolean.FALSE.equals(getValue())){
            return VALUE_FALSE;
        }else {
            return "";
        }
    }
}
