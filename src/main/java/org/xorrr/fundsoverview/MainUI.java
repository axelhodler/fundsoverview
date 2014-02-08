package org.xorrr.fundsoverview;

import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;

import org.xorrr.fundsoverview.db.MongoFundsDatastore;
import org.xorrr.fundsoverview.events.EventBus;
import org.xorrr.fundsoverview.events.EventType;
import org.xorrr.fundsoverview.events.FundAlreadyAddedEventHandler;
import org.xorrr.fundsoverview.events.InvalidIsinEventHandler;
import org.xorrr.fundsoverview.model.FundsDatastore;
import org.xorrr.fundsoverview.model.ModelFacadeImpl;
import org.xorrr.fundsoverview.presenter.DashboardPresenter;
import org.xorrr.fundsoverview.retrieval.FidelityFundDocumentAccessor;
import org.xorrr.fundsoverview.retrieval.FundScraper;
import org.xorrr.fundsoverview.retrieval.FundValuesExtractor;
import org.xorrr.fundsoverview.view.DashboardView;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("fundsoverview")
@SuppressWarnings("serial")
public class MainUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class, widgetset = "org.xorrr.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        UI.getCurrent().getPage().setTitle("financegrabber");
        DashboardView view = new DashboardViewImpl();
        EventBus bus = initEventBus();
        DashboardPresenter handler = new DashboardPresenter(view,
                createModel(), bus);

        view.setHandler(handler);
        view.init();

        navigator.addView("", view);
        navigator.navigateTo("");
    }

    private EventBus initEventBus() {
        EventBus bus = EventBus.getInstance();
        bus.addHandler(EventType.FUND_ALREADY_ADDED,
                new FundAlreadyAddedEventHandler());
        bus.addHandler(EventType.INVALID_ISIN, new InvalidIsinEventHandler());
        return bus;
    }

    private ModelFacadeImpl createModel() {
        return new ModelFacadeImpl(createFundsDatastore(), createFundScraper());
    }

    private FundScraper createFundScraper() {
        return new FundScraper(new FidelityFundDocumentAccessor(),
                new FundValuesExtractor());
    }

    private FundsDatastore createFundsDatastore() {
        MongoClientURI uri = new MongoClientURI(
                System.getenv(EnvironmentVariables.MONGODB_URI));
        FundsDatastore ds = null;

        try {
            ds = new MongoFundsDatastore(new MongoClient(uri));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ds;
    }
}
