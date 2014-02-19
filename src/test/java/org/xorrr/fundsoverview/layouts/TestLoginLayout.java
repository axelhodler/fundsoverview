package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.vaadin.ui.Component;

public class TestLoginLayout {

    private LoginLayout layout;
    private DashboardViewImpl view;

    private void checkComponentExistence(String location) {
        Component component = layout.getComponent(location);
        assertNotNull(component);
    }

    private void componentWasRemoved(String location) {
        assertNull(layout.getComponent(location));
    }

    @Before
    public void setUp() {
        view = mock(DashboardViewImpl.class);
        layout = new LoginLayout();
        layout.init();
        layout.setView(view);
    }

    @Test
    public void loginButtonExists() {
        checkComponentExistence(LoginLayoutLocations.loginButton);
    }

    @Test
    public void usernameFieldExists() {
        checkComponentExistence(LoginLayoutLocations.userField);
    }

    @Test
    public void passwordFieldExists() {
        checkComponentExistence(LoginLayoutLocations.passField);
    }

    @Test
    public void handleLoginButton() {
        layout.getUsernameField().setValue("");
        layout.getPasswordField().setValue("");
        layout.getLoginButton().click();

        verify(view, times(1)).handleLogin("", "");
    }

    @Test
    public void loginFormCanBeRemoved() {
        layout.removeLoginForm();

        componentWasRemoved(LoginLayoutLocations.loginButton);
        componentWasRemoved(LoginLayoutLocations.userField);
        componentWasRemoved(LoginLayoutLocations.passField);
    }

    @Test
    public void userNameCanBeDisplayed() {
        layout.displayUserName("");

        checkComponentExistence(LoginLayoutLocations.userField);
    }
}
