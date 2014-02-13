package org.xorrr.fundsoverview.login;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.EnvironmentVariables;

public class TestUserService {

    private UserService service;

    private boolean failLogin() {
        return service.login("wrong", "wrong");
    }

    private boolean passLogin() {
        return service.login(System.getenv(EnvironmentVariables.USER),
                System.getenv(EnvironmentVariables.PASS));
    }

    @Before
    public void setUp() {
        service = new UserServiceImpl();
    }

    @Test
    public void loginFailsWithWrongCredentials() {
        assertFalse(failLogin());
    }

    @Test
    public void loginWorksWithCorrectCredentials() {
        assertTrue(passLogin());
    }
}
