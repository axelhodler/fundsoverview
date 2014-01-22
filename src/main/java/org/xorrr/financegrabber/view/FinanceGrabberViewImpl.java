package org.xorrr.financegrabber.view;

import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.presenter.FinanceGrabberViewHandler;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class FinanceGrabberViewImpl extends VerticalLayout implements
        FinanceGrabberView {

    private TextField fundIdField;
    private Button addFundButton;

    private FinanceGrabberViewHandler handler;

    public void init() {
        initFormToAddFunds();
    }

    private void initFormToAddFunds() {
        fundIdField = new TextField();
        fundIdField.setId("add_fund_id_field");

        addFundButton = new Button("ADD_FUND");
        addFundButton.setId("add_fund_button");
        addFundButton.addClickListener(addFundListener);

        addComponent(fundIdField);
        addComponent(addFundButton);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    @Override
    public void setHandler(FinanceGrabberViewHandler handler) {
        this.handler = handler;
    }

    @Override
    public Button getAddFundBtn() {
        return this.addFundButton;
    }

    Button.ClickListener addFundListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            handler.addFund(new BasicFinancialProduct.Builder()
            .wkn(fundIdField.getValue()).build());
        }
    };
}
