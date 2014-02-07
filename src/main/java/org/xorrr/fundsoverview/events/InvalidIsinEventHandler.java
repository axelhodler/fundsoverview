package org.xorrr.fundsoverview.events;

import java.util.Locale;
import java.util.ResourceBundle;

import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class InvalidIsinEventHandler implements EventHandler {

    @Override
    public void handleEvent(EventType t) {
        Localization helper = new Localization();
        ResourceBundle bundle = helper.getMessages(new Locale(System
                .getenv("LANG"), System.getenv("COUNTRY")));
        Notification notif = new Notification(
                bundle.getString(LocalizationStrings.ISIN_INVALID));
        notif.show(Page.getCurrent());
    }

}
