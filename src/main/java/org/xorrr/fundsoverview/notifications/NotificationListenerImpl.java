package org.xorrr.fundsoverview.notifications;

import net.engio.mbassy.listener.Handler;


public class NotificationListenerImpl implements NotificationListener {

    public NotificationListenerImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    @Handler
    public void handleFundAlreadyAdded(FundAlreadyAddedEvent event) {
        System.out.println("FundAlreadyAddeDevent");
    }
}
