package org.xorrr.fundsoverview.l18n;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

public class TestL18nVariables {
    private L18nHelper l18n;

    private Locale createLocale(String language, String country) {
        return new Locale(language, country);
    }

    private void translationExists(ResourceBundle messages) {
        assertNotNull(messages.getString(L18nVariables.FUND));
        assertNotNull(messages.getString(L18nVariables.PRICE));
        assertNotNull(messages.getString(L18nVariables.CURRENT_YEAR));
        assertNotNull(messages.getString(L18nVariables.ONE_YEAR));
        assertNotNull(messages.getString(L18nVariables.THREE_YEARS));
        assertNotNull(messages.getString(L18nVariables.FIVE_YEARS));
        assertNotNull(messages.getString(L18nVariables.ADD_FUND));
        assertNotNull(messages.getString(L18nVariables.DELETE));
        assertNotNull(messages.getString(L18nVariables.FUND_ALREADY_ADDED));
        assertNotNull(messages.getString(L18nVariables.ISIN_INVALID));
    }

    @Before
    public void setUp() {
        l18n = new L18nHelper();
    }

    @Test
    public void englishTranslationsExist() {
        translationExists(l18n.getMessages(createLocale("en", "US")));
    }

    @Test
    public void germanTranslationsExist() {
        translationExists(l18n.getMessages(createLocale("de", "DE")));
    }
}
