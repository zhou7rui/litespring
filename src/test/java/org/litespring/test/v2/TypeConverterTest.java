package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.TypeMismatchException;

public class TypeConverterTest {

    @Test
    public void testConverterStringToInt() {


        TypeConverter converter = new SimpleTypeConverter();

        Integer integer = converter.converterIfNecessary("7",Integer.class);

        Assert.assertEquals(7, integer.intValue());

        try {
            converter.converterIfNecessary("7.1", Integer.class);
        }catch (TypeMismatchException e){
            return;
        }
        Assert.fail();


    }

    @Test
    public void testConverterStringToBoolean() {

        TypeConverter converter = new SimpleTypeConverter();

        Boolean bool = converter.converterIfNecessary("true",Boolean.class);
        Assert.assertEquals(true,bool.booleanValue());


        try {
            converter.converterIfNecessary("foo",Boolean.class);
        }catch (TypeMismatchException e){
            return;
        }
        Assert.fail();

    }


}
