package org.xorrr.fundsoverview.l18n;

import java.util.Locale;
import java.util.ResourceBundle;

import org.xorrr.fundsoverview.EnvironmentVariables;

public abstract class Localization {

    private static String bundleBasename = "MessagesBundle";

    public static ResourceBundle getMessages(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle(bundleBasename,
                locale);
        return messages;
    }

    public static ResourceBundle getMessages() {
        Locale locale = new Locale(System.getenv(EnvironmentVariables.LANG),
                System.getenv(EnvironmentVariables.COUNTRY));
        return ResourceBundle.getBundle(bundleBasename,
                locale);
    }

}
