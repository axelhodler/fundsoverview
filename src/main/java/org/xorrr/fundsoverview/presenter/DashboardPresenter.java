package org.xorrr.fundsoverview.presenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.engio.mbassy.listener.Handler;

import org.xorrr.fundsoverview.di.Module;
import org.xorrr.fundsoverview.eventbus.EventBus;
import org.xorrr.fundsoverview.eventbus.NotificationEventHandler;
import org.xorrr.fundsoverview.eventbus.events.FundAlreadyAddedEvent;
import org.xorrr.fundsoverview.eventbus.events.InvalidIsinEvent;
import org.xorrr.fundsoverview.eventbus.events.WrongCredentialsEvent;
import org.xorrr.fundsoverview.events.LoggedInEvent;
import org.xorrr.fundsoverview.login.UserService;
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.model.ModelFacade;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;
import org.xorrr.fundsoverview.util.SessionAttributes;
import org.xorrr.fundsoverview.view.DashboardView;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.vaadin.server.VaadinSession;

public class DashboardPresenter implements DashboardViewHandler {

    private DashboardView view;
    private ModelFacade model;
    private EventBus bus;
    private Injector injector;
    private UserService userService;

    @Inject
    public DashboardPresenter(DashboardView view, ModelFacade model,
            EventBus bus) {
        this.view = view;
        this.model = model;
        this.bus = bus;

        this.injector = Guice.createInjector(new Module());
        setEventHandler(bus);
    }

    @Override
    public void addFund(Fund f) {
        try {
            model.getFund(f.getIsin());
            if (isinNotAlreadyAdded(f))
                model.addFund(f);
            else
                bus.fireEvent(new FundAlreadyAddedEvent());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidIsinException e) {
            bus.fireEvent(new InvalidIsinEvent());
        }
    }

    @Override
    public void showFunds() {
        List<Fund> fundsWithInfos = getDataForEachFund();
        view.displayFunds(fundsWithInfos);
    }

    @Override
    public void removeFundTableItems() {
        view.getFundTable().removeAllItems();
    }

    @Override
    public void deleteFund(String isin) {
        model.deleteFund(isin);
    }

    @Override
    public DashboardView getView() {
        return this.view;
    }

    @Override
    public void handleLogin(String username, String password) {
        if (credentialsAreCorrect(username, password)) {
            setLoggedIn(username);
            bus.fireEvent(new LoggedInEvent());
        } else 
            bus.fireEvent(new WrongCredentialsEvent());
    }

    @Override
    public void setUserService(UserService service) {
        this.userService = service;
    }

    @Override
    public void showFundsWithDeleteButton() {
        List<Fund> fundsWithInfos = getDataForEachFund();

        view.displayFundsWithDeleteButtons(fundsWithInfos);
    }

    @Handler
    public void handleUserLoggedIn(LoggedInEvent loggedIn) {
        List<Fund> fundsWithInfos = getDataForEachFund();

        view.removeLoginForm();
        view.removeTableItems();
        view.displayFundsWithDeleteButtons(fundsWithInfos);
        view.displayAddFundForm();
    }

    private List<Fund> getDataForEachFund() {
        List<Fund> funds = model.getFunds();
        List<Fund> fundsWithInfos = new ArrayList<>();
        extractFurtherInfosForEachFund(funds, fundsWithInfos);
        return fundsWithInfos;
    }

    private void setLoggedIn(String username) {
        VaadinSession.getCurrent().setAttribute(SessionAttributes.USERNAME,
                username);
    }

    private boolean credentialsAreCorrect(String username, String password) {
        return userService.login(username, password);
    }

    private boolean isinNotAlreadyAdded(Fund f) {
        return !model.checkIfIsinAlreadyAdded(f.getIsin());
    }

    private void setEventHandler(EventBus bus) {
        bus.addHandler(injector.getInstance(NotificationEventHandler.class));
        bus.addHandler(this);
    }

    private void extractFurtherInfosForEachFund(List<Fund> funds, List<Fund> fundsWithInfos) {
        for (Fund fund : funds) {
            addExtractedInfosToFunds(fundsWithInfos, fund);
        }
    }

    private void addExtractedInfosToFunds(List<Fund> fundsWithInfos, Fund fund) {
        try {
            Fund f = model.getFund(fund.getIsin());
            fundsWithInfos.add(f);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidIsinException e) {
            e.printStackTrace();
        }
    }


}
