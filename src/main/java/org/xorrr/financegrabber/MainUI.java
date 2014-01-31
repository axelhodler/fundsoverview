package org.xorrr.financegrabber;

import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;

import org.xorrr.financegrabber.db.MongoFundsDatastore;
import org.xorrr.financegrabber.model.FundsDatastore;
import org.xorrr.financegrabber.model.ModelFacadeImpl;
import org.xorrr.financegrabber.presenter.DashboardPresenter;
import org.xorrr.financegrabber.retrieval.FidelityFundDocumentAccessor;
import org.xorrr.financegrabber.retrieval.FundScraper;
import org.xorrr.financegrabber.retrieval.FundValuesExtractor;
import org.xorrr.financegrabber.view.DashboardView;
import org.xorrr.financegrabber.view.DashboardViewImpl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("mytheme")
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
        DashboardPresenter handler = new DashboardPresenter(view,
                createModel());

        view.setHandler(handler);
        view.init();

        navigator.addView("", view);
        navigator.navigateTo("");
    }

    private ModelFacadeImpl createModel() {
        return new ModelFacadeImpl(createFundsDatastore(),
                createFundScraper());
    }

    private FundScraper createFundScraper() {
        return new FundScraper(new FidelityFundDocumentAccessor(),
                new FundValuesExtractor());
    }

    private FundsDatastore createFundsDatastore() {
        MongoClientURI uri = new MongoClientURI(System.getenv("MONGODB_URI"));
        FundsDatastore ds = null;

        try {
            ds = new MongoFundsDatastore(new MongoClient(uri));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ds;
    }
}
