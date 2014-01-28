package org.xorrr.financegrabber;

import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;

import org.xorrr.financegrabber.db.MongoFundsDatastore;
import org.xorrr.financegrabber.model.FundsDatastore;
import org.xorrr.financegrabber.model.ModelFacade;
import org.xorrr.financegrabber.model.ModelFacadeImpl;
import org.xorrr.financegrabber.presenter.FinanceGrabberPresenter;
import org.xorrr.financegrabber.retrieval.FidelityFundDocumentAccessor;
import org.xorrr.financegrabber.view.FinanceGrabberView;
import org.xorrr.financegrabber.view.FinanceGrabberViewImpl;

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

        FinanceGrabberView view = new FinanceGrabberViewImpl();
        ModelFacade model = new ModelFacadeImpl(createFundsDatastore(),
                new FidelityFundDocumentAccessor());

        FinanceGrabberPresenter handler = new FinanceGrabberPresenter(view,
                model);

        view.setHandler(handler);
        view.init();

        navigator.addView("", view);
        navigator.navigateTo("");
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
