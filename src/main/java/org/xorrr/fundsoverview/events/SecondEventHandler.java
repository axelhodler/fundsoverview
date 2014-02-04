package org.xorrr.fundsoverview.events;

public class SecondEventHandler implements EventHandler {

    @Override
    public void handleEvent(EventType t) {
        System.out.println("event: " + t.toString());
    }

}
