package org.xorrr.fundsoverview.notifications;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;

public class NotificationHandler {

    private MBassador<NotificationEvent> bus;

    public NotificationHandler(NotificationListener listener) {
        this.bus = new MBassador<NotificationEvent>(
                BusConfiguration.Default());
        bus.subscribe(listener);
    }

    public MBassador<NotificationEvent> getBus() {
        return bus;
    }
}
