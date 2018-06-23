package org.litespring.test.v2;

import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomBooleanEditor;

import static org.junit.Assert.*;

public class CustomBooleanEditorTest {


    @Test
    public void testConvertStringToBoolean(){

        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        assertEquals(true, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("false");
        assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("on");
        assertEquals(true, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("off");
        assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("yes");
        assertEquals(true, ((Boolean) editor.getValue()).booleanValue());

        editor.setAsText("no");
        assertEquals(false, ((Boolean) editor.getValue()).booleanValue());


        editor.setAsText("1");
        assertEquals(true, ((Boolean)editor.getValue()).booleanValue());


        editor.setAsText("0");
        assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        try {

            editor.setAsText("foo");
        }catch (Exception e){
            return;
        }
        fail();




    }


}
