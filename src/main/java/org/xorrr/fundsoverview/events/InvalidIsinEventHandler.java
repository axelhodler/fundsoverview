package org.xorrr.fundsoverview.events;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class InvalidIsinEventHandler implements EventHandler {

    @Override
    public void handleEvent(EventType t) {
        Notification notif = new Notification("Isin is invalid");
        notif.show(Page.getCurrent());
    }

}
