package org.xorrr.fundsoverview.l18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

    public ResourceBundle getMessages(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);
        return messages;
    }

}
