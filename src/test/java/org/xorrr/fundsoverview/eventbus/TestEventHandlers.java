package org.xorrr.fundsoverview.eventbus;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.eventbus.events.FundAlreadyAddedHandler;
import org.xorrr.fundsoverview.eventbus.events.InvalidIsinEventHandler;
import org.xorrr.fundsoverview.eventbus.events.WrongCredentialsHandler;

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

    @Test
    public void notifiesInvalidIsin() {
        InvalidIsinEventHandler handler = new InvalidIsinEventHandler(notificator);

        handler.handleEvent(EventType.INVALID_ISIN);
        
        verify(notificator, times(1)).notifyInvalidIsin(anyString());
    }

    @Test
    public void notifiesWrongCredentials() {
        WrongCredentialsHandler handler = new WrongCredentialsHandler(notificator);

        handler.handleEvent(EventType.INVALID_ISIN);
        
        verify(notificator, times(1)).notifyWrongCredentials(anyString());
    }
}
