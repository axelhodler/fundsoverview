package org.xorrr.fundsoverview.login;

import org.xorrr.fundsoverview.EnvironmentVariables;

public class UserServiceImpl implements UserService {

    @Override
    public boolean login(String username, String password) {
        if (credentialsAreCorrect(username, password))
            return true;
        else 
            return false;
    }

    private boolean credentialsAreCorrect(String username, String password) {
        return isUsernameCorrect(username) && isPasswordCorrect(password);
    }

    private boolean isPasswordCorrect(String password) {
        return password.equals(System.getenv(EnvironmentVariables.PASS));
    }

    private boolean isUsernameCorrect(String username) {
        return username.equals(System.getenv("USER"));
    }
}
