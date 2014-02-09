package org.xorrr.fundsoverview.eventbus.events;

import org.xorrr.fundsoverview.eventbus.EventHandler;
import org.xorrr.fundsoverview.eventbus.EventType;
import org.xorrr.fundsoverview.eventbus.Notificator;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;

import com.google.inject.Inject;

public class FundAlreadyAddedHandler implements EventHandler {

    private Notificator notificator;

    @Inject
    public FundAlreadyAddedHandler(Notificator notificator) {
        this.notificator = notificator;
    }

    @Override
    public void handleEvent(EventType t) {
        notificator
                .notifyFundAlreadyAdded
                (LocalizationStrings.FUND_ALREADY_ADDED);
    }

}
