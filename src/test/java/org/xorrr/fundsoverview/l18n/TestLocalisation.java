package org.xorrr.fundsoverview.l18n;

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class TestLocalisation {

    @Test
    public void test() {
        Locale en = new Locale("en", "US");

        ResourceBundle messages = ResourceBundle
                .getBundle("MessagesBundle", en);

        assertEquals("Fund", messages.getString("fund"));
    }

}
