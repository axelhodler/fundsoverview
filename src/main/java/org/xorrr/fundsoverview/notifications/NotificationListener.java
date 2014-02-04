package org.xorrr.fundsoverview.notifications;

public interface NotificationListener {

    void handleFundAlreadyAdded(FundAlreadyAddedEvent event);
}
