package org.xorrr.fundsoverview.l18n;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

public class TestLocalization {

    private Locale createLocale(String language, String country) {
        return new Locale(language, country);
    }

    private void translationExists(ResourceBundle messages) {
        assertNotNull(messages.getString(LocalizationStrings.FUND));
        assertNotNull(messages.getString(LocalizationStrings.PRICE));
        assertNotNull(messages.getString(LocalizationStrings.CURRENT_YEAR));
        assertNotNull(messages.getString(LocalizationStrings.ONE_YEAR));
        assertNotNull(messages.getString(LocalizationStrings.THREE_YEARS));
        assertNotNull(messages.getString(LocalizationStrings.FIVE_YEARS));
        assertNotNull(messages.getString(LocalizationStrings.ADD_FUND));
        assertNotNull(messages.getString(LocalizationStrings.DELETE));
        assertNotNull(messages.getString(LocalizationStrings.FUND_ALREADY_ADDED));
        assertNotNull(messages.getString(LocalizationStrings.ISIN_INVALID));
        assertNotNull(messages.getString(LocalizationStrings.LOGIN_BUTTON));
        assertNotNull(messages.getString(LocalizationStrings.USERNAME));
        assertNotNull(messages.getString(LocalizationStrings.PASSWORD));
        assertNotNull(messages.getString(LocalizationStrings.WRONG_CREDENTIALS));
    }

    @Test
    public void englishTranslationsExist() {
        translationExists(Localization.getMessages(createLocale("en", "US")));
    }

    @Test
    public void germanTranslationsExist() {
        translationExists(Localization.getMessages(createLocale("de", "DE")));
    }
}
