package org.xorrr.financegrabber;

import javax.servlet.annotation.WebServlet;

import org.xorrr.financegrabber.presenter.FinanceGrabberPresenter;
import org.xorrr.financegrabber.view.FinanceGrabberView;
import org.xorrr.financegrabber.view.FinanceGrabberViewImpl;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "org.xorrr.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);

        UI.getCurrent().getPage().setTitle("financegrabber");

        FinanceGrabberView view = new FinanceGrabberViewImpl();
        FinanceGrabberPresenter handler = new FinanceGrabberPresenter(view);
        view.addHandler(handler);
        view.init();

        navigator.addView("", view);
        navigator.navigateTo("");
    }
}
