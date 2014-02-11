package org.xorrr.fundsoverview.login;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.eventbus.EventBus;
import org.xorrr.fundsoverview.eventbus.events.WrongCredentialsEvent;
import org.xorrr.fundsoverview.events.LoggedInEvent;
import org.xorrr.fundsoverview.util.SessionAttributes;

import com.vaadin.server.VaadinSession;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ VaadinSession.class })
public class TestUserService {

    private UserService service;
    private VaadinSession session;
    private EventBus bus;

    private void failLogin() {
        service.login("wrong", "wrong");
    }

    @Before
    public void setUp() {
        bus = mock(EventBus.class);
        service = new UserServiceImpl(bus);

        session = mock(VaadinSession.class);
        PowerMockito.mockStatic(VaadinSession.class);
        PowerMockito.when(VaadinSession.getCurrent()).thenReturn(session);
        PowerMockito.when(
                VaadinSession.getCurrent().getAttribute(
                        SessionAttributes.USERNAME)).thenReturn("user");
    }

    @Test
    public void loginFailsWithWrongCredentials() {
        failLogin();

        verify(session, times(0)).setAttribute(SessionAttributes.USERNAME,
                EnvironmentVariables.USER);
    }

    @Test
    public void displayNotificationIfWrongCredentials() {
        failLogin();

        verify(bus, times(1)).fireEvent(any(WrongCredentialsEvent.class));
    }

    @Test
    public void loginWorksWithCorrectCredentials() {
        service.login(System.getenv(EnvironmentVariables.USER),
                System.getenv(EnvironmentVariables.PASS));

        verify(session, times(1)).setAttribute(SessionAttributes.USERNAME,
                EnvironmentVariables.USER);
    }

    @Test
    public void loginEventIsFiredWithCorrectCredentials() {
        service.login(System.getenv(EnvironmentVariables.USER),
                System.getenv(EnvironmentVariables.PASS));

        verify(bus, times(1)).fireEvent(any(LoggedInEvent.class));
    }
}
