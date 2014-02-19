package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

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
        checkComponentExistence(LoginLocations.loginButton);
    }

    @Test
    public void usernameFieldExists() {
        checkComponentExistence(LoginLocations.userField);
    }

    @Test
    public void passwordFieldExists() {
        checkComponentExistence(LoginLocations.passField);
    }

    @Test
    public void handleLoginButton() {
        TextField userField = (TextField) layout
                .getComponent(LoginLocations.userField);
        userField.setValue("johnny");

        TextField pw = (TextField) layout
                .getComponent(LoginLocations.passField);
        pw.setValue("b");

        Button login = (Button) layout
                .getComponent(LoginLocations.loginButton);
        login.click();

        verify(view, times(1)).handleLogin("johnny", "b");
    }

    @Test
    public void loginFormCanBeRemoved() {
        layout.removeLoginForm();

        componentWasRemoved(LoginLocations.loginButton);
        componentWasRemoved(LoginLocations.userField);
        componentWasRemoved(LoginLocations.passField);
    }

    @Test
    public void userNameCanBeDisplayed() {
        layout.displayUserName("");

        checkComponentExistence(LoginLocations.userField);
    }
}
