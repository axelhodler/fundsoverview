package org.xorrr.fundsoverview.layouts;

import java.util.ResourceBundle;

import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.di.Module;
import org.xorrr.fundsoverview.eventbus.EventBus;
import org.xorrr.fundsoverview.eventbus.EventType;
import org.xorrr.fundsoverview.eventbus.events.WrongCredentialsHandler;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;
import org.xorrr.fundsoverview.login.User;
import org.xorrr.fundsoverview.login.UserService;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginLayout extends VerticalLayout {
    private static final long serialVersionUID = -6114361172208149300L;

    private Button loginButton;
    private TextField usernameField;
    private TextField passwordField;
    private Label userStatus;
    private ResourceBundle translation;

    private UserService userService;

    private EventBus bus;

    @Inject
    public LoginLayout(EventBus bus) {
        this.bus = bus;
        Injector injector = Guice.createInjector(new Module());
        bus.addHandler(EventType.WRONG_CREDENTIALS,
                injector.getInstance(WrongCredentialsHandler.class));
    }

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
            changeLayoutIfLoginSuccessful(user);
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

    public Label getUserStatus() {
        return userStatus;
    }

    private void setUpLocalization() {
        translation = Localization.getMessages();
    }

    private void createComponents() {
        loginButton = new Button(
                translation.getString(LocalizationStrings.LOGIN_BUTTON));
        usernameField = new TextField(LocalizationStrings.USERNAME);
        passwordField = new TextField(LocalizationStrings.PASSWORD);
        userStatus = new Label("logged in as "
                + System.getenv(EnvironmentVariables.USER));
    }

    private void changeLayoutIfLoginSuccessful(User user) {
        if (user != null) {
            removeAllComponents();
            addComponent(userStatus);
        } else
            bus.fireEvent(EventType.WRONG_CREDENTIALS);
    }
}
