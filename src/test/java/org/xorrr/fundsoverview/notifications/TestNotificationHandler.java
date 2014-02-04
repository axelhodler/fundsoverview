package org.xorrr.fundsoverview.notifications;

import net.engio.mbassy.bus.MBassador;

import org.junit.Test;

public class TestNotificationHandler {

    @Test
    public void mbassadorSpike() {
        NotificationHandler handler = new NotificationHandler(new NotificationListenerImpl());
        MBassador<NotificationEvent> bus = handler.getBus();
        bus.publish(new FundAlreadyAddedEvent());
    }

}
