package org.xorrr.financegrabber;

import javax.servlet.annotation.WebServlet;

import org.xorrr.financegrabber.presenter.FinanceGrabberPresenter;
import org.xorrr.financegrabber.view.FinanceGrabberViewImpl;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "org.xorrr.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        UI.getCurrent().getPage().setTitle("financegrabber");
        final VerticalLayout mainLayout = new VerticalLayout();
        setContent(mainLayout);

        FinanceGrabberViewImpl view = new FinanceGrabberViewImpl();
        new FinanceGrabberPresenter(view);

        mainLayout.addComponent(view);
    }
}
