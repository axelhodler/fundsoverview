package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.xorrr.fundsoverview.eventbus.EventType;
import org.xorrr.fundsoverview.login.UserServiceImpl;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ VaadinSession.class })
public class TestLoginLayout {

    private LoginLayout layout;
    private EventBus bus;
    private VaadinSession session;

    private void checkComponentExistence(Component expectedComponent) {
        int index = layout.getComponentIndex(expectedComponent);
        Component component = layout.getComponent(index);
        assertEquals(expectedComponent, component);
    }

    private void loginUser() {
        TextField username = layout.getUsernameField();
        username.setValue(System.getenv(EnvironmentVariables.USER));
        TextField password = layout.getPasswordField();
        password.setValue(System.getenv(EnvironmentVariables.PASS));
        Button loginButton = layout.getLoginButton();
        loginButton.click();
    }

    private void componentWasRemoved(Component component) {
        assertEquals(-1, layout.getComponentIndex(component));
    }

    private void loginWithWrongCredentials() {
        TextField username = layout.getUsernameField();
        username.setValue("wrongUser");
        TextField password = layout.getPasswordField();
        password.setValue("wrongPassword");
        Button loginButton = layout.getLoginButton();
        loginButton.click();
    }

    private void userNameIsNotDisplayed() {
        assertEquals(-1, layout.getComponentIndex(layout.getUserStatus()));
    }

    @Before
    public void setUp() {
        bus = mock(EventBus.class);

        layout = new LoginLayout(bus);
        layout.init();
        layout.setUserService(new UserServiceImpl());

        session = mock(VaadinSession.class);
        PowerMockito.mockStatic(VaadinSession.class);
        PowerMockito.when(VaadinSession.getCurrent()).thenReturn(session);
        PowerMockito.when(VaadinSession.getCurrent().getAttribute("username"))
                .thenReturn("user");
    }

    @Test
    public void loginButtonExists() {
        checkComponentExistence(layout.getLoginButton());
    }

    @Test
    public void usernameFieldExists() {
        checkComponentExistence(layout.getUsernameField());
    }

    @Test
    public void passwordFieldExists() {
        checkComponentExistence(layout.getPasswordField());
    }

    @Test
    public void loginWorks() {
        loginUser();

        String userName = (String) session.getAttribute("username");
        assertNotNull(userName);
    }

    @Test
    public void loginFormIsRemovedAfterLogin() {
        loginUser();

        componentWasRemoved(layout.getLoginButton());
        componentWasRemoved(layout.getUsernameField());
        componentWasRemoved(layout.getPasswordField());
    }

    @Test
    public void displayUserNameAfterLogin() {
        loginUser();

        checkComponentExistence(layout.getUserStatus());
    }

    @Test
    public void loginFormIsNotRemovedAfterFailedLogin() {
        loginWithWrongCredentials();

        checkComponentExistence(layout.getPasswordField());
        checkComponentExistence(layout.getUsernameField());
        checkComponentExistence(layout.getLoginButton());
    }

    @Test
    public void userNameIsNotDisplayedAfterFailedLogin() {
        loginWithWrongCredentials();

        userNameIsNotDisplayed();
    }

    @Test
    public void notifyWhenWrongCredentials() {
        loginWithWrongCredentials();

        verify(bus, times(1)).fireEvent(EventType.WRONG_CREDENTIALS);
    }
}
