package org.xorrr.fundsoverview.presenter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xorrr.fundsoverview.EnvironmentVariables;
import org.xorrr.fundsoverview.eventbus.EventBus;
import org.xorrr.fundsoverview.eventbus.NotificationEventHandler;
import org.xorrr.fundsoverview.eventbus.events.FundAlreadyAddedEvent;
import org.xorrr.fundsoverview.eventbus.events.InvalidIsinEvent;
import org.xorrr.fundsoverview.eventbus.events.WrongCredentialsEvent;
import org.xorrr.fundsoverview.events.LoggedInEvent;
import org.xorrr.fundsoverview.login.UserService;
import org.xorrr.fundsoverview.login.UserServiceImpl;
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.model.ModelFacade;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;
import org.xorrr.fundsoverview.util.SessionAttributes;
import org.xorrr.fundsoverview.view.DashboardView;

import com.vaadin.server.VaadinSession;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ VaadinSession.class })
public class TestDashboardPresenter {

    private DashboardPresenter presenter;
    private ModelFacade model;
    private DashboardView view;
    private EventBus bus;

    private String validIsin = "validIsin";
    private String invalidIsin = "invalidIsin";
    private Fund testFund;
    private UserService service;
    private VaadinSession session;
    private LoggedInEvent loggedInEvent;

    private boolean loginWithCorrectCredentials() {
        return service.login(System.getenv(EnvironmentVariables.USER),
                System.getenv(EnvironmentVariables.PASS));
    }

    private void handleCorrectLogin() {
        presenter.handleLogin(System.getenv(EnvironmentVariables.USER),
                System.getenv(EnvironmentVariables.PASS));
    }

    @Before
    public void setUp() {
        this.model = mock(ModelFacade.class);
        this.view = mock(DashboardView.class);
        this.bus = mock(EventBus.class);

        this.presenter = new DashboardPresenter(view, model, bus);

        service = mock(UserServiceImpl.class);
        presenter.setUserService(service);

        session = mock(VaadinSession.class);
        PowerMockito.mockStatic(VaadinSession.class);
        PowerMockito.when(VaadinSession.getCurrent()).thenReturn(session);
        when(loginWithCorrectCredentials()).thenReturn(true);

        this.testFund = new Fund.Builder().isin(validIsin).build();
    }

    @Test
    public void notificationEventHandlerSubscribesToBus() {
        // TODO issue concerning verification with any(Object)
        verify(bus, times(2)).addHandler(any(NotificationEventHandler.class));
    }

    @Test
    public void presenterSubscribesToBus() {
        verify(bus, times(1)).addHandler(presenter);
    }

    @Test
    public void addsFundWithValidIsin() throws IOException,
            InvalidIsinException {
        when(model.getFund(validIsin)).thenReturn(testFund);

        presenter.addFund(testFund);

        verify(model, times(1)).addFund(any(Fund.class));
    }

    @Test
    public void dontAddFundWithInvalidIsin() throws IOException,
            InvalidIsinException {
        Fund invalidFund = new Fund.Builder().isin(invalidIsin).build();

        when(model.getFund(invalidIsin)).thenThrow(new InvalidIsinException());

        presenter.addFund(invalidFund);

        verify(model, times(1)).getFund(invalidIsin);
        verify(model, times(0)).addFund(testFund);
    }

    @Test
    public void testTheShowFundsMethod() throws IOException,
            InvalidIsinException {
        Fund fp = mock(Fund.class);
        Fund fp2 = mock(Fund.class);

        Fund fpWithExtractedInfos = new Fund.Builder().build();
        fpWithExtractedInfos.setName("name");

        List<Fund> list = new ArrayList<Fund>();
        list.add(fp);
        list.add(fp2);

        when(model.getFund(anyString())).thenReturn(fpWithExtractedInfos);
        when(fp.getIsin()).thenReturn("thewkn");
        when(fp2.getIsin()).thenReturn("secondwkn");
        when(model.getFunds()).thenReturn(list);

        presenter.showFunds();
        verify(model, times(1)).getFunds();
        verify(model).getFund(fp.getIsin());
        verify(model).getFund(fp2.getIsin());

        verify(model, times(2)).getFund(anyString());
        verify(view, times(1)).displayFunds(anyListOf(Fund.class));
    }

    @Test
    public void testFundDeletion() {
        presenter.deleteFund(anyString());
        verify(model, times(1)).deleteFund(anyString());
    }

    @Test
    public void cantAddSameIsinTwice() {
        when(model.checkIfIsinAlreadyAdded(anyString())).thenReturn(true);
        presenter.addFund(testFund);

        verify(model, times(1)).checkIfIsinAlreadyAdded(validIsin);
        verify(model, times(0)).addFund(testFund);
    }

    @Test
    public void fireFundAlreadyAddedEventWhenAddedAgain() {
        when(model.checkIfIsinAlreadyAdded(anyString())).thenReturn(true);
        presenter.addFund(testFund);

        verify(bus, times(1)).fireEvent(any(FundAlreadyAddedEvent.class));
    }

    @Test
    public void fireInvalidIsinEvent() throws IOException, InvalidIsinException {
        Fund invalidFund = new Fund.Builder().isin(invalidIsin).build();

        when(model.getFund(invalidIsin)).thenThrow(new InvalidIsinException());

        presenter.addFund(invalidFund);

        verify(model, times(1)).getFund(invalidIsin);
        verify(bus, times(1)).fireEvent(any(InvalidIsinEvent.class));
    }

    @Test
    public void addFundFormIsShownWhenLoggedInEventHandled() {
        loggedInEvent = mock(LoggedInEvent.class);
        presenter.handleUserLoggedIn(loggedInEvent);

        verify(view, times(1)).displayAddFundForm();
    }

    @Test
    public void userCanLogin() {
        handleCorrectLogin();
        verify(service, times(1)).login(anyString(), anyString());
    }

    @Test
    public void dealWithSession() {
        handleCorrectLogin();

        verify(session, times(1)).setAttribute(SessionAttributes.USERNAME,
                System.getenv(EnvironmentVariables.USER));
    }

    @Test
    public void fireLoggedInEventAfterSuccessfulLogin() {
        handleCorrectLogin();

        verify(bus, times(1)).fireEvent(any(LoggedInEvent.class));
    }

    @Test
    public void removeLoginFormAfterLoggedInEvent() {
        presenter.handleUserLoggedIn(loggedInEvent);

        verify(view, times(1)).removeLoginForm();
    }

    @Test
    public void fireWrongCredentialsIfLoginFails() {
        presenter.handleLogin("", "");

        verify(bus, times(1)).fireEvent(any(WrongCredentialsEvent.class));
    }

    @Test
    public void showFundsWithDeleteButtonsAfterLoggedInEvent() {
        presenter.handleUserLoggedIn(loggedInEvent);

        verify(view, times(1)).displayFundsWithDeleteButtons(
                anyListOf(Fund.class));
    }
}
