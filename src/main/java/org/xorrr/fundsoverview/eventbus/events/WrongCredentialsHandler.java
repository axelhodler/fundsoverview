package org.xorrr.fundsoverview.eventbus.events;

import java.util.ResourceBundle;

import org.xorrr.fundsoverview.eventbus.EventHandler;
import org.xorrr.fundsoverview.eventbus.EventType;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class WrongCredentialsHandler implements EventHandler {

    @Override
    public void handleEvent(EventType t) {
        ResourceBundle bundle = Localization.getMessages();
        Notification notif = new Notification(
                bundle.getString(LocalizationStrings.ISIN_INVALID));
        notif.show(Page.getCurrent());
    }

}
