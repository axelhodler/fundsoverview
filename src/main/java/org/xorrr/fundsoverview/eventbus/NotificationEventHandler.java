package org.xorrr.fundsoverview.eventbus;

import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

import org.xorrr.fundsoverview.eventbus.events.FundAlreadyAddedEvent;
import org.xorrr.fundsoverview.eventbus.events.InvalidIsinEvent;
import org.xorrr.fundsoverview.eventbus.events.WrongCredentialsEvent;
import org.xorrr.fundsoverview.l18n.TranslationVars;

import com.google.inject.Inject;

@Listener(references = References.Strong)
public class NotificationEventHandler {

    private Notificator n;

    @Inject
    public NotificationEventHandler(Notificator notificator) {
        this.n = notificator;
    }

    @Handler
    public void handleFundAlreadyAddedEvent(FundAlreadyAddedEvent event) {
        n.notifyFundAlreadyAdded(TranslationVars.FUND_ALREADY_ADDED);
    }

    @Handler
    public void handleInvalidIsinEvent(InvalidIsinEvent event) {
        n.notifyInvalidIsin(TranslationVars.ISIN_INVALID);
    }

    @Handler
    public void handleWrongCredentialsEvent(WrongCredentialsEvent event) {
        n.notifyWrongCredentials(TranslationVars.WRONG_CREDENTIALS);
    }
}
