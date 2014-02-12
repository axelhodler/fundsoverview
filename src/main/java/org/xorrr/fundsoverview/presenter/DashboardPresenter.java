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
        List<Fund> funds = model.getFunds();
        List<Fund> fundsWithInfos = new ArrayList<>();

        iterateSavedFunds(funds, fundsWithInfos);
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
        if (userService.login(username, password)) {
            VaadinSession.getCurrent().setAttribute(SessionAttributes.USERNAME,
                    username);
            bus.fireEvent(new LoggedInEvent());
        }
    }

    @Override
    public void setUserService(UserService service) {
        this.userService = service;
    }

    @Handler
    public void handleUserLoggedIn(LoggedInEvent loggedIn) {
        view.removeLoginForm();
        view.displayAddFundForm();
    }

    private boolean isinNotAlreadyAdded(Fund f) {
        return !model.checkIfIsinAlreadyAdded(f.getIsin());
    }

    private void setEventHandler(EventBus bus) {
        bus.addHandler(injector.getInstance(NotificationEventHandler.class));
        bus.addHandler(this);
    }

    private void iterateSavedFunds(List<Fund> funds, List<Fund> fundsWithInfos) {
        for (Fund fund : funds) {
            addExtractedToFunds(fundsWithInfos, fund);
        }
    }

    private void addExtractedToFunds(List<Fund> fundsWithInfos, Fund fund) {
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
