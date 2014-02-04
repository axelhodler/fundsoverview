package org.xorrr.fundsoverview.l18n;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

public class TestL18nVariables {

    private L18nHelper helper;

    @Before
    public void setUp() {
        helper = new L18nHelper();
    }

    @Test
    public void doDefinedVariablesExistForEn() {
        Locale en = new Locale("en", "US");
        ResourceBundle messages = helper.getMessages(en);

        assertNotNull(messages.getString(L18nVariables.FUND));
        assertNotNull(messages.getString(L18nVariables.PRICE));
        assertNotNull(messages.getString(L18nVariables.CURRENT_YEAR));
        assertNotNull(messages.getString(L18nVariables.ONE_YEAR));
        assertNotNull(messages.getString(L18nVariables.THREE_YEARS));
        assertNotNull(messages.getString(L18nVariables.FIVE_YEARS));
        assertNotNull(messages.getString(L18nVariables.ADD_FUND));
        assertNotNull(messages.getString(L18nVariables.DELETE));
    }

    @Test
    public void doDefinedVariablesExistForDe() {
        Locale ger = new Locale("de", "DE");
        ResourceBundle messages = helper.getMessages(ger);

        assertNotNull(messages.getString(L18nVariables.FUND));
        assertNotNull(messages.getString(L18nVariables.PRICE));
        assertNotNull(messages.getString(L18nVariables.CURRENT_YEAR));
        assertNotNull(messages.getString(L18nVariables.ONE_YEAR));
        assertNotNull(messages.getString(L18nVariables.THREE_YEARS));
        assertNotNull(messages.getString(L18nVariables.FIVE_YEARS));
        assertNotNull(messages.getString(L18nVariables.ADD_FUND));
        assertNotNull(messages.getString(L18nVariables.DELETE));
    }

}
