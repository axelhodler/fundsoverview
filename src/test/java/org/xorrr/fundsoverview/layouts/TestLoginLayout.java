package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.login.UserService;
import org.xorrr.fundsoverview.login.UserServiceImpl;
import org.xorrr.fundsoverview.util.SessionAttributes;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ VaadinSession.class })
public class TestLoginLayout {

    private LoginLayout layout;
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

    private OngoingStubbing<Object> mockLoggedIn() {
        return PowerMockito.when(
                VaadinSession.getCurrent().getAttribute(
                        SessionAttributes.USERNAME)).thenReturn("user");
    }

    private OngoingStubbing<Object> mockLoginFailed() {
        return PowerMockito.when(
                VaadinSession.getCurrent().getAttribute(
                        SessionAttributes.USERNAME)).thenReturn(null);
    }

    @Before
    public void setUp() {
        UserService service = mock(UserServiceImpl.class);

        layout = new LoginLayout(service);
        layout.init();

        session = mock(VaadinSession.class);
        PowerMockito.mockStatic(VaadinSession.class);
        PowerMockito.when(VaadinSession.getCurrent()).thenReturn(session);
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
        mockLoggedIn();
        loginUser();

        String userName = (String) session
                .getAttribute(SessionAttributes.USERNAME);
        assertNotNull(userName);
    }

    @Test
    public void loginFormIsRemovedAfterLogin() {
        mockLoggedIn();
        loginUser();

        componentWasRemoved(layout.getLoginButton());
        componentWasRemoved(layout.getUsernameField());
        componentWasRemoved(layout.getPasswordField());
    }

    @Test
    public void displayUserNameAfterLogin() {
        mockLoggedIn();
        loginUser();

        checkComponentExistence(layout.getUserStatus());
    }

    @Test
    public void loginFormIsNotRemovedAfterFailedLogin() {
        mockLoginFailed();
        loginWithWrongCredentials();

        checkComponentExistence(layout.getPasswordField());
        checkComponentExistence(layout.getUsernameField());
        checkComponentExistence(layout.getLoginButton());
    }


    @Test
    public void userNameIsNotDisplayedAfterFailedLogin() {
        mockLoginFailed();
        loginWithWrongCredentials();

        userNameIsNotDisplayed();
    }
}
