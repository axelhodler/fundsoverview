package org.xorrr.fundsoverview.layouts;

import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.TranslationVars;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class LoginLayout extends CustomLayout {
    private static final long serialVersionUID = -6114361172208149300L;

    private Button loginButton;
    private TextField usernameField;
    private TextField passwordField;
    private Label userStatus;
    private Localization translation;

    private DashboardViewImpl view;

    public LoginLayout() {
        super(Layouts.LOGIN);
    }

    public void init() {
        setUpLocalization();

        createComponents();

        addComponent(loginButton, LoginLocations.loginButton);
        addComponent(usernameField, LoginLocations.userField);
        addComponent(passwordField, LoginLocations.passField);

        loginButton.addClickListener(loginButtonListener);
    }

    Button.ClickListener loginButtonListener = new Button.ClickListener() {
        private static final long serialVersionUID = -8573483079166742117L;

        @Override
        public void buttonClick(ClickEvent event) {
            view.handleLogin(usernameField.getValue(), passwordField.getValue());
        }

    };

    private void setUpLocalization() {
        translation = new Localization();
    }

    private void createComponents() {
        loginButton = new Button(
                translation.getTranslationFor(TranslationVars.LOGIN_BUTTON));
        usernameField = new TextField(TranslationVars.USERNAME);
        passwordField = new TextField(TranslationVars.PASSWORD);
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
