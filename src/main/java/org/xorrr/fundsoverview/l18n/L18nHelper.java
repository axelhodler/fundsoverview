package org.xorrr.fundsoverview.l18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class L18nHelper {

    public ResourceBundle getMessages() {
        Locale en = new Locale("en", "US");
        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", en);
        return messages;
    }

}
