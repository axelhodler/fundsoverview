package org.xorrr.fundsoverview.l18n;

import java.util.Locale;
import java.util.ResourceBundle;

import org.xorrr.fundsoverview.EnvironmentVariables;

public class Localization {
    private ResourceBundle messages;

    private static String bundleBasename = "MessagesBundle";

    public Localization() {
        Locale locale = new Locale(System.getenv(EnvironmentVariables.LANG),
                System.getenv(EnvironmentVariables.COUNTRY));
        messages = ResourceBundle.getBundle(bundleBasename, locale);
    }

    public String getTranslationFor(String translationVar) {
        return messages.getString(translationVar);
    }
}
