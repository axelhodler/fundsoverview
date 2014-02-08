package org.xorrr.fundsoverview.events;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class TestEventBus {
    private EventBus bus;

    @Before
    public void setUp() {
        bus = EventBus.getInstance();
    }

    @Test
    public void fundAlreadyEventIsHandled() {
        EventHandler fundAlreadyAddedHandler = mock(FundAlreadyAddedEventHandler.class);
        bus.addHandler(EventType.FUND_ALREADY_ADDED, fundAlreadyAddedHandler);

        bus.fireEvent(EventType.FUND_ALREADY_ADDED);

        verify(fundAlreadyAddedHandler, times(1)).handleEvent(EventType.FUND_ALREADY_ADDED);
    }

    @Test
    public void invalidIsinIsHandled() {
        EventHandler invalidIsinHandler = mock(InvalidIsinEventHandler.class);
        bus.addHandler(EventType.INVALID_ISIN, invalidIsinHandler);

        bus.fireEvent(EventType.INVALID_ISIN);

        verify(invalidIsinHandler, times(1)).handleEvent(EventType.INVALID_ISIN);
    }

    @Test
    public void wrongCredentialsAreHandled() {
        EventHandler wrongCredentials = mock(WrongCredentialsHandler.class);
        bus.addHandler(EventType.WRONG_CREDENTIALS, wrongCredentials);

        bus.fireEvent(EventType.WRONG_CREDENTIALS);

        verify(wrongCredentials, times(1)).handleEvent(EventType.WRONG_CREDENTIALS);
    }
}
