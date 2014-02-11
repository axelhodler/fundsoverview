package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertEquals;
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

    private void checkComponentExistence(Component expectedComponent) {
        int index = layout.getComponentIndex(expectedComponent);
        Component component = layout.getComponent(index);
        assertEquals(expectedComponent, component);
    }

    private void componentWasRemoved(Component component) {
        assertEquals(-1, layout.getComponentIndex(component));
    }

    @Before
    public void setUp() {
        view = mock(DashboardViewImpl.class);
        layout = new LoginLayout(view);
        layout.init();
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
    public void handleLoginButton() {
        layout.getUsernameField().setValue("");
        layout.getPasswordField().setValue("");
        layout.getLoginButton().click();

        verify(view, times(1)).handleLogin("", "");
    }

    @Test
    public void loginFormCanBeRemoved() {
        layout.removeLoginForm();

        componentWasRemoved(layout.getLoginButton());
        componentWasRemoved(layout.getUsernameField());
        componentWasRemoved(layout.getPasswordField());
    }

    @Test
    public void userNameCanBeDisplayed() {
        layout.displayUserName("");

        checkComponentExistence(layout.getUserStatus());
    }
}
