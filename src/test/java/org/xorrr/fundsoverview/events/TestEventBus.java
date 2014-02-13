package org.xorrr.fundsoverview.events;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xorrr.fundsoverview.eventbus.EventBus;
import org.xorrr.fundsoverview.eventbus.NotificationEventHandler;
import org.xorrr.fundsoverview.eventbus.events.FundAlreadyAddedEvent;
import org.xorrr.fundsoverview.eventbus.events.InvalidIsinEvent;
import org.xorrr.fundsoverview.eventbus.events.WrongCredentialsEvent;
import org.xorrr.fundsoverview.presenter.DashboardPresenter;

@RunWith(MockitoJUnitRunner.class)
public class TestEventBus {

    private EventBus bus;

    @Mock
    private NotificationEventHandler handler;
    @Mock
    private DashboardPresenter presenter;
    @Mock
    private FundAlreadyAddedEvent alreadyAdded;
    @Mock
    private InvalidIsinEvent invalidIsin;
    @Mock
    private WrongCredentialsEvent wrongCreds;
    @Mock
    private LoggedInEvent loggedIn;

    @Before
    public void setUp() {
        bus = new EventBus();

        bus.addHandler(handler);
        bus.addHandler(presenter);
    }

    @Test
    public void fundAlreadyAddedEventIsHandled() {
        bus.fireEvent(alreadyAdded);

        verify(handler, times(1)).handleFundAlreadyAddedEvent(alreadyAdded);
    }

    @Test
    public void invalidIsinEventIsHandled() {
        bus.fireEvent(invalidIsin);

        verify(handler, times(1)).handleInvalidIsinEvent(invalidIsin);
    }

    @Test
    public void wrongCredentialsEventIsHandled() {
        bus.fireEvent(wrongCreds);

        verify(handler, times(1)).handleWrongCredentialsEvent(wrongCreds);
    }

    @Test
    public void loggedInEventIsHandled() {
        bus.fireEvent(loggedIn);

        verify(presenter, times(1)).handleUserLoggedIn(loggedIn);
    }
}
