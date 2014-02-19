package org.xorrr.fundsoverview.layouts;

import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginLayout extends VerticalLayout {
    private static final long serialVersionUID = -6114361172208149300L;

    private Button loginButton;
    private TextField usernameField;
    private TextField passwordField;
    private Label userStatus;
    private Localization translation;

    private DashboardViewImpl view;

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
            view.handleLogin(usernameField.getValue(), passwordField.getValue());
        }

    };

    

    public Button getLoginButton() {
        return loginButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Label getUserStatus() {
        return userStatus;
    }

    private void setUpLocalization() {
        translation = new Localization();
    }

    private void createComponents() {
        loginButton = new Button(
                translation.getTranslationFor(LocalizationStrings.LOGIN_BUTTON));
        usernameField = new TextField(LocalizationStrings.USERNAME);
        passwordField = new TextField(LocalizationStrings.PASSWORD);
        userStatus = new Label("logged in as "
                + System.getenv(EnvironmentVariables.USER));
    }

    public void removeLoginForm() {
        removeComponent(loginButton);
        removeComponent(usernameField);
        removeComponent(passwordField);
    }

    public void displayUserName(String username) {
        userStatus = new Label(username);
        addComponent(userStatus);
    }

    public void setView(DashboardViewImpl view) {
        this.view = view;
    }
}
