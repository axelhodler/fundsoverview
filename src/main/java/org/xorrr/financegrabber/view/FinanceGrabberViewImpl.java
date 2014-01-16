package org.xorrr.financegrabber.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class FinanceGrabberViewImpl extends CustomComponent implements
        FinanceGrabberView, ClickListener {

    private TextField fundIdField;

    public FinanceGrabberViewImpl() {
        Panel addFundPanel = new Panel();
        addFundPanel.setId("add_fund_panel");

        fundIdField = new TextField();
        fundIdField.setId("add_fund_id_field");

        Button addFundButton = new Button("ADD_FUND", this);
        addFundButton.setId("add_fund_button");

        VerticalLayout addFundsForm = new VerticalLayout();

        addFundsForm.addComponent(fundIdField);
        addFundsForm.addComponent(addFundButton);

        addFundPanel.setContent(addFundsForm);

        setCompositionRoot(addFundPanel);
    }

    /* Only the presenter registers one listener... */
    List<FinanceGrabberViewListener> listeners =
            new ArrayList<FinanceGrabberViewListener>();

    @Override
    public void addListener(FinanceGrabberViewListener listener) {
        listeners.add(listener);
    }

    /** Relay button clicks to the presenter with an
     *  implementation-independent event */
    @Override
    public void buttonClick(ClickEvent event) {
        for (FinanceGrabberViewListener listener: listeners) {
            listener.buttonClick(event.getButton().getCaption());
        }
    }

    @Override
    public String getFundId() {
        return fundIdField.getValue();
    }
}
