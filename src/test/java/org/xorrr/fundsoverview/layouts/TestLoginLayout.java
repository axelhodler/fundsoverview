package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.MainUI;
import org.xorrr.fundsoverview.login.User;
import org.xorrr.fundsoverview.login.UserServiceImpl;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class TestLoginLayout {

    private LoginLayout layout;

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

    @Before
    public void setUp() {
        layout = new LoginLayout();
        layout.init();
        layout.setUserService(new UserServiceImpl());
        UI.setCurrent(new MainUI());
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

        User user = (User) UI.getCurrent().getData();
        assertNotNull(user);
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
}
