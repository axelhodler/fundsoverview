package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.login.User;
import org.xorrr.fundsoverview.login.UserService;

import com.vaadin.ui.Component;

public class TestLoginLayout {

    private LoginLayout layout;

    private void checkComponentExistence(Component expectedComponent) {
        int index = layout.getComponentIndex(expectedComponent);
        Component component = layout.getComponent(index);
        assertEquals(expectedComponent, component);
    }

    private void fakeUserService() {
        layout.setUserService(new UserService() {
            @Override
            public User login(String username, String password) {
                return new User();
            }
        });
    }

    @Before
    public void setUp() {
        layout = new LoginLayout();
        layout.init();
        fakeUserService();
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
}
