package org.xorrr.fundsoverview.l18n;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TestLocalization {

    private Localization l18n;

    @Before
    public void setUp() {
        l18n = new Localization();
    }

    @Test
    public void translationsExist() {
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.FUND));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.PRICE));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.CURRENT_YEAR));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.ONE_YEAR));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.THREE_YEARS));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.FIVE_YEARS));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.ADD_FUND));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.DELETE));
        assertNotNull(l18n
                .getTranslationFor(LocalizationStrings.FUND_ALREADY_ADDED));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.ISIN_INVALID));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.LOGIN_BUTTON));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.USERNAME));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.PASSWORD));
        assertNotNull(l18n
                .getTranslationFor(LocalizationStrings.WRONG_CREDENTIALS));
        assertNotNull(l18n
                .getTranslationFor(LocalizationStrings.LOGIN_TO_DELETE));
        assertNotNull(l18n.getTranslationFor(LocalizationStrings.LOGIN_TO_ADD));
    }
}
