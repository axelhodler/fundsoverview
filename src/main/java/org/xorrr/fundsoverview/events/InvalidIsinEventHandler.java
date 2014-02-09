package org.xorrr.fundsoverview.events;

import java.util.ResourceBundle;

import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class InvalidIsinEventHandler implements EventHandler {

    @Override
    public void handleEvent(EventType t) {
        ResourceBundle bundle = Localization.getMessages();
        Notification notif = new Notification(
                bundle.getString(LocalizationStrings.ISIN_INVALID));
        notif.show(Page.getCurrent());
    }

}
