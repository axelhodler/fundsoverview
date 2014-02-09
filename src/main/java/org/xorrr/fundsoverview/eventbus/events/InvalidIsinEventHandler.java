package org.xorrr.fundsoverview.eventbus.events;

import org.xorrr.fundsoverview.eventbus.EventHandler;
import org.xorrr.fundsoverview.eventbus.EventType;
import org.xorrr.fundsoverview.eventbus.Notificator;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;

import com.google.inject.Inject;

public class InvalidIsinEventHandler implements EventHandler {

    private Notificator notificator;

    @Inject
    public InvalidIsinEventHandler(Notificator notificator) {
        this.notificator = notificator;
    }

    @Override
    public void handleEvent(EventType t) {
        notificator.notifyInvalidIsin(LocalizationStrings.ISIN_INVALID);
    }
}
