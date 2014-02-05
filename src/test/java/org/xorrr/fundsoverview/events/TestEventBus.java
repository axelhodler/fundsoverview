package org.xorrr.fundsoverview.events;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class TestEventBus {

    @Test
    public void testEventBus() {
        EventHandler handler = mock(FundAlreadyAddedEventHandler.class);
        EventHandler secondHandler = mock(InvalidIsinEventHandler.class);
        EventBus bus = EventBus.getInstance();
        bus.addHandler(EventType.FUND_ALREADY_ADDED, handler);
        bus.addHandler(EventType.INVALID_ISIN, secondHandler);
        bus.addHandler(EventType.INVALID_ISIN, handler);
        bus.fireEvent(EventType.FUND_ALREADY_ADDED);
        bus.fireEvent(EventType.INVALID_ISIN);

        verify(handler, times(1)).handleEvent(EventType.FUND_ALREADY_ADDED);
        verify(handler, times(1)).handleEvent(EventType.INVALID_ISIN);
        verify(secondHandler, times(1)).handleEvent(EventType.INVALID_ISIN);
    }
}
