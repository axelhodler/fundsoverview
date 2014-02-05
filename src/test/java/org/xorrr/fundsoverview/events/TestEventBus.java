package org.xorrr.fundsoverview.events;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class TestEventBus {

    @Test
    public void testEventBus() {
        EventHandler handler = mock(FundAlreadyAddedEventHandler.class);
        EventHandler secondHandler = mock(SecondEventHandler.class);
        EventBus bus = EventBus.getInstance();
        bus.addHandler(EventType.FUND_ALREADY_ADDED, handler);
        bus.addHandler(EventType.SECOND, secondHandler);
        bus.addHandler(EventType.SECOND, handler);
        bus.fireEvent(EventType.FUND_ALREADY_ADDED);
        bus.fireEvent(EventType.SECOND);

        verify(handler, times(1)).handleEvent(EventType.FUND_ALREADY_ADDED);
        verify(handler, times(1)).handleEvent(EventType.SECOND);
        verify(secondHandler, times(1)).handleEvent(EventType.SECOND);
    }
}
