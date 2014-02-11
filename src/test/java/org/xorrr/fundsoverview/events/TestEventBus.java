package org.xorrr.fundsoverview.events;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.eventbus.EventBus;
import org.xorrr.fundsoverview.eventbus.NotificationEventHandler;
import org.xorrr.fundsoverview.eventbus.events.FundAlreadyAddedEvent;
import org.xorrr.fundsoverview.eventbus.events.InvalidIsinEvent;
import org.xorrr.fundsoverview.eventbus.events.WrongCredentialsEvent;
import org.xorrr.fundsoverview.presenter.DashboardPresenter;

public class TestEventBus {

    private EventBus bus;
    private NotificationEventHandler handler;
    private DashboardPresenter presenter;

    @Before
    public void setUp() {
        bus = new EventBus();

        handler = mock(NotificationEventHandler.class);
        presenter = mock(DashboardPresenter.class);

        bus.addHandler(handler);
        bus.addHandler(presenter);
    }

    @Test
    public void fundAlreadyAddedEventIsHandled() {
        FundAlreadyAddedEvent alreadyAdded = mock(FundAlreadyAddedEvent.class);

        bus.fireEvent(alreadyAdded);

        verify(handler, times(1)).handleFundAlreadyAddedEvent(alreadyAdded);
    }

    @Test
    public void invalidIsinEventIsHandled() {
        InvalidIsinEvent invalidIsin = mock(InvalidIsinEvent.class);

        bus.fireEvent(invalidIsin);

        verify(handler, times(1)).handleInvalidIsinEvent(invalidIsin);
    }

    @Test
    public void wrongCredentialsEventIsHandled() {
        WrongCredentialsEvent wrongCreds = mock(WrongCredentialsEvent.class);

        bus.fireEvent(wrongCreds);

        verify(handler, times(1)).handleWrongCredentialsEvent(wrongCreds);
    }

    @Test
    public void loggedInEventIsHandled() {
        LoggedInEvent loggedIn = mock(LoggedInEvent.class);

        bus.fireEvent(loggedIn);

        verify(presenter, times(1)).handleUserLoggedIn(loggedIn);
    }
}
