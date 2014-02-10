package org.xorrr.fundsoverview.login;

import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.util.SessionAttributes;

import com.vaadin.server.VaadinSession;

public class UserServiceImpl implements UserService {

    @Override
    public void login(String username, String password) {
        if (isUsernameCorrect(username) && isPasswordCorrect(password))
            VaadinSession.getCurrent().setAttribute(SessionAttributes.USERNAME,
                    EnvironmentVariables.USER); // works currently because only
                                                // one user exists!
    }

    private boolean isPasswordCorrect(String password) {
        return password.equals(System.getenv(EnvironmentVariables.PASS));
    }

    private boolean isUsernameCorrect(String username) {
        return username.equals(System.getenv("USER"));
    }
}
