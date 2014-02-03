package org.xorrr.fundsoverview.l18n;

import static org.junit.Assert.assertNotNull;

import java.util.ResourceBundle;

import org.junit.Test;

public class TestL18nVariables {

    @Test
    public void doDefinedVariablesExistForEn() {
        L18nHelper helper = new L18nHelper();
        ResourceBundle messages = helper.getMessages();

        assertNotNull(messages.getString(L18nVariables.FUND));
        assertNotNull(messages.getString(L18nVariables.PRICE));
        assertNotNull(messages.getString(L18nVariables.CURRENT_YEAR));
        assertNotNull(messages.getString(L18nVariables.ONE_YEAR));
        assertNotNull(messages.getString(L18nVariables.THREE_YEARS));
        assertNotNull(messages.getString(L18nVariables.FIVE_YEARS));
        assertNotNull(messages.getString(L18nVariables.ADD_FUND));
    }

}
