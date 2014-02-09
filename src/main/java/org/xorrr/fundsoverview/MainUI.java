package org.xorrr.fundsoverview;

import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;

import org.xorrr.fundsoverview.db.MongoFundsDatastore;
import org.xorrr.fundsoverview.di.Module;
import org.xorrr.fundsoverview.eventbus.EventBus;
import org.xorrr.fundsoverview.eventbus.EventType;
import org.xorrr.fundsoverview.eventbus.events.FundAlreadyAddedHandler;
import org.xorrr.fundsoverview.eventbus.events.InvalidIsinEventHandler;
import org.xorrr.fundsoverview.model.ModelFacadeImpl;
import org.xorrr.fundsoverview.presenter.DashboardPresenter;
import org.xorrr.fundsoverview.retrieval.FundScraper;
import org.xorrr.fundsoverview.view.DashboardView;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("fundsoverview")
@SuppressWarnings("serial")
public class MainUI extends UI {

    private Injector injector;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class, widgetset = "org.xorrr.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        this.injector = Guice.createInjector(new Module());

        Navigator navigator = new Navigator(this, this);
        UI.getCurrent().getPage().setTitle("financegrabber");
        DashboardView view = new DashboardViewImpl();
        EventBus bus = initEventBus();
        DashboardPresenter handler = null;
        try {
            handler = new DashboardPresenter(view,
                    createModel(), bus);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        view.setHandler(handler);
        view.init();

        navigator.addView("", view);
        navigator.navigateTo("");
    }

    private EventBus initEventBus() {
        EventBus bus = EventBus.getInstance();
        bus.addHandler(EventType.FUND_ALREADY_ADDED,
                new FundAlreadyAddedHandler());
        bus.addHandler(EventType.INVALID_ISIN, new InvalidIsinEventHandler());
        return bus;
    }

    private ModelFacadeImpl createModel() throws UnknownHostException {
        return new ModelFacadeImpl(new MongoFundsDatastore(), createFundScraper());
    }

    private FundScraper createFundScraper() {
        return injector.getInstance(FundScraper.class);
    }
}
