package org.xorrr.fundsoverview.login;

import org.xorrr.fundsoverview.EnvironmentVariables;

public class UserServiceImpl implements UserService {

    @Override
    public User login(String username, String password) {
        if (isUsernameCorrect(username) && isPasswordCorrect(password))
            return new User();
        else 
            return null;
    }

    private boolean isPasswordCorrect(String password) {
        return password.equals(System.getenv(EnvironmentVariables.PASS));
    }

    private boolean isUsernameCorrect(String username) {
        return username.equals(System.getenv("USER"));
    }

}
