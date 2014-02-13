package org.xorrr.fundsoverview;

import javax.servlet.annotation.WebServlet;

import org.xorrr.fundsoverview.di.Module;
import org.xorrr.fundsoverview.login.UserServiceImpl;
import org.xorrr.fundsoverview.presenter.DashboardPresenter;
import org.xorrr.fundsoverview.view.DashboardView;

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
        DashboardPresenter handler = injector
                .getInstance(DashboardPresenter.class);
        handler.setUserService(new UserServiceImpl());

        DashboardView view = handler.getView();

        view.setHandler(handler);
        view.init();

        navigator.addView("", view);
        navigator.navigateTo("");
    }
}
