package org.xorrr.fundsoverview.events;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class FundAlreadyAddedEventHandler implements EventHandler{

    @Override
    public void handleEvent(EventType t) {
        Notification notif = new Notification("Isin already added");
        notif.show(Page.getCurrent());
    }

}
