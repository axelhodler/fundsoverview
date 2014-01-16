package org.xorrr.financegrabber;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
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

        Panel addFundPanel = new Panel();
        addFundPanel.setId("add_fund_panel");

        TextField fundIdField = new TextField();
        fundIdField.setId("add_fund_id_field");

        Button addFundButton = new Button("Add fond");
        addFundButton.setId("add_fund_button");

        VerticalLayout addFundsForm = new VerticalLayout();

        addFundsForm.addComponent(fundIdField);
        addFundsForm.addComponent(addFundButton);

        addFundPanel.setContent(addFundsForm);

        mainLayout.addComponent(addFundPanel);
    }

}
