package org.xorrr.fundsoverview.eventbus;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;

public class EventBus {

    private MBassador<Event> bus;

    public EventBus() {
        bus = new MBassador<Event>(BusConfiguration.Default());
    }

    public void addHandler(Object handler) {
        bus.subscribe(handler);
    }

    public void fireEvent(Event event) {
        bus.publish(event);
    }
}
