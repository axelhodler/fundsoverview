package org.xorrr.fundsoverview.l18n;

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class TestLocalisation {

    @Test
    public void messagesCanBeAccessed() {
        Locale en = new Locale("en", "US");
        L18nHelper helper = new L18nHelper();
        ResourceBundle messages = helper.getMessages(en);

        assertEquals("Fund", messages.getString(L18nVariables.FUND));
    }

}
