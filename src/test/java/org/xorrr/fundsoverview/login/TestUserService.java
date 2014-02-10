package org.xorrr.fundsoverview.login;

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
import org.xorrr.fundsoverview.util.SessionAttributes;

import com.vaadin.server.VaadinSession;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ VaadinSession.class })
public class TestUserService {

    private UserService service;
    private VaadinSession session;

    @Before
    public void setUp() {
        service = new UserServiceImpl();

        session = mock(VaadinSession.class);
        PowerMockito.mockStatic(VaadinSession.class);
        PowerMockito.when(VaadinSession.getCurrent()).thenReturn(session);
        PowerMockito.when(
                VaadinSession.getCurrent().getAttribute(
                        SessionAttributes.USERNAME)).thenReturn("user");
    }

    @Test
    public void loginFailsWithWrongCredentials() {
        service.login("wrong", "wrong");

        verify(session, times(0)).setAttribute(SessionAttributes.USERNAME,
                EnvironmentVariables.USER);
    }

    @Test
    public void loginWorksWithCorrectCredentials() {
        service.login(System.getenv(EnvironmentVariables.USER),
                System.getenv(EnvironmentVariables.PASS));

        verify(session, times(1)).setAttribute(SessionAttributes.USERNAME,
                EnvironmentVariables.USER);
    }

}
