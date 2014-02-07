package org.xorrr.fundsoverview.events;

import java.util.Locale;
import java.util.ResourceBundle;

import org.xorrr.fundsoverview.l18n.L18nHelper;
import org.xorrr.fundsoverview.l18n.L18nVariables;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class FundAlreadyAddedEventHandler implements EventHandler {

    @Override
    public void handleEvent(EventType t) {
        L18nHelper helper = new L18nHelper();
        ResourceBundle bundle = helper.getMessages(new Locale(System
                .getenv("LANG"), System.getenv("COUNTRY")));
        Notification notif = new Notification(
                bundle.getString(L18nVariables.FUND_ALREADY_ADDED));
        notif.show(Page.getCurrent());
    }

}
