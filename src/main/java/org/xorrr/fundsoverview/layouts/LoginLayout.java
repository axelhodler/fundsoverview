package org.xorrr.fundsoverview.layouts;

import java.util.Locale;
import java.util.ResourceBundle;

import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;
import org.xorrr.fundsoverview.login.User;
import org.xorrr.fundsoverview.login.UserService;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginLayout extends VerticalLayout {
    private static final long serialVersionUID = -6114361172208149300L;

    private Button loginButton;
    private TextField usernameField;
    private TextField passwordField;
    private ResourceBundle translation;

    private UserService userService;

    public void init() {
        setUpLocalization();

        createComponents();

        addComponent(loginButton);
        addComponent(usernameField);
        addComponent(passwordField);
        loginButton.addClickListener(loginButtonListener);
    }

    Button.ClickListener loginButtonListener = new Button.ClickListener() {
        private static final long serialVersionUID = -8573483079166742117L;

        @Override
        public void buttonClick(ClickEvent event) {
            User user = userService.login(usernameField.getValue(),
                    passwordField.getValue());
            UI.getCurrent().setData(user);
        }
    };

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    private void setUpLocalization() {
        Localization local = new Localization();
        translation = local.getMessages(new Locale(System
                .getenv(EnvironmentVariables.LANG), System
                .getenv(EnvironmentVariables.PASS)));
    }

    private void createComponents() {
        loginButton = new Button(
                translation.getString(LocalizationStrings.LOGIN_BUTTON));
        usernameField = new TextField(LocalizationStrings.USERNAME);
        passwordField = new TextField(LocalizationStrings.PASSWORD);
    }
}
