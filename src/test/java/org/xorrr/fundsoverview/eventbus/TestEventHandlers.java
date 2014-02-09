package org.xorrr.fundsoverview.eventbus;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.eventbus.events.FundAlreadyAddedHandler;

public class TestEventHandlers {

    private Notificator notificator;

    @Before
    public void setUp() {
        this.notificator = mock(Notificator.class);
    }

    @Test
    public void notifiesFundAlreadyAdded() {
        FundAlreadyAddedHandler handler = new FundAlreadyAddedHandler(notificator);

        handler.handleEvent(EventType.FUND_ALREADY_ADDED);

        verify(notificator, times(1)).notifyFundAlreadyAdded(anyString());
    }
}
