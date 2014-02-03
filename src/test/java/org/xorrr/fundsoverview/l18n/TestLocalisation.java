package org.xorrr.fundsoverview.l18n;

import static org.junit.Assert.assertEquals;

import java.util.ResourceBundle;

import org.junit.Test;

public class TestLocalisation {

    @Test
    public void messagesCanBeAccessed() {
        L18nHelper helper = new L18nHelper();
        ResourceBundle messages = helper.getMessages();

        assertEquals("Fund", messages.getString(L18nVariables.FUND));
    }

}
