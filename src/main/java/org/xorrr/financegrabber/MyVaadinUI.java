package org.xorrr.financegrabber;

import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;

import org.xorrr.financegrabber.db.FinancialProductDatastore;
import org.xorrr.financegrabber.model.BasicFinancialProduct;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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

        final TextField fundIdField = new TextField();
        fundIdField.setId("add_fund_id_field");

        Button addFundButton = new Button("Add fond");
        addFundButton.setId("add_fund_button");

        VerticalLayout addFundsForm = new VerticalLayout();

        addFundsForm.addComponent(fundIdField);
        addFundsForm.addComponent(addFundButton);

        addFundPanel.setContent(addFundsForm);

        mainLayout.addComponent(addFundPanel);

        ClickListener addFunds = new Button.ClickListener() {
            
            @Override
            public void buttonClick(ClickEvent event) {
                MongoClientURI uri = new MongoClientURI("mongodb://localhost:12345");
                FinancialProductDatastore ds = null;
                try {
                    ds = new FinancialProductDatastore(new MongoClient(uri));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                BasicFinancialProduct bfp = new BasicFinancialProduct.Builder().wkn(
                        fundIdField.getValue()).build();
                ds.saveProduct(bfp);
                
            }
        };

        addFundButton.addClickListener(addFunds);
    }
}
