package org.xorrr.fundsoverview.events;

public class FundAlreadyAddedEventHandler implements EventHandler{

    @Override
    public void handleEvent(EventType t) {
        System.out.println("deal with :" + t.toString());
    }

}
