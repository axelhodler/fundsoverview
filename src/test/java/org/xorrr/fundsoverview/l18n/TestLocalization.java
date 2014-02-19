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
        assertNotNull(l18n.getTranslationFor(TranslationVars.FUND));
        assertNotNull(l18n.getTranslationFor(TranslationVars.PRICE));
        assertNotNull(l18n.getTranslationFor(TranslationVars.CURRENT_YEAR));
        assertNotNull(l18n.getTranslationFor(TranslationVars.ONE_YEAR));
        assertNotNull(l18n.getTranslationFor(TranslationVars.THREE_YEARS));
        assertNotNull(l18n.getTranslationFor(TranslationVars.FIVE_YEARS));
        assertNotNull(l18n.getTranslationFor(TranslationVars.ADD_FUND));
        assertNotNull(l18n.getTranslationFor(TranslationVars.DELETE));
        assertNotNull(l18n
                .getTranslationFor(TranslationVars.FUND_ALREADY_ADDED));
        assertNotNull(l18n.getTranslationFor(TranslationVars.ISIN_INVALID));
        assertNotNull(l18n.getTranslationFor(TranslationVars.LOGIN_BUTTON));
        assertNotNull(l18n.getTranslationFor(TranslationVars.USERNAME));
        assertNotNull(l18n.getTranslationFor(TranslationVars.PASSWORD));
        assertNotNull(l18n
                .getTranslationFor(TranslationVars.WRONG_CREDENTIALS));
        assertNotNull(l18n
                .getTranslationFor(TranslationVars.LOGIN_TO_DELETE));
        assertNotNull(l18n.getTranslationFor(TranslationVars.LOGIN_TO_ADD));
    }
}
