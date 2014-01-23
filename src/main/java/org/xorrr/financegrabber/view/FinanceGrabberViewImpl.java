package org.xorrr.financegrabber.view;

import java.util.List;

import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.presenter.FinanceGrabberViewHandler;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class FinanceGrabberViewImpl extends VerticalLayout implements
        FinanceGrabberView {

    private TextField fundIdField;
    private Button addFundButton;
    private Table fundTable;
    private FinanceGrabberViewHandler handler;

    public void init() {
        initFormToAddFunds();
        initFundTable();
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

    @Override
    public Table getFundTable() {
        return this.fundTable;
    }

    @Override
    public void displayFunds(List<BasicFinancialProduct> funds) {
        for (int i = 0; i < funds.size(); i++) {
            fundTable.addItem(new Object[] { funds.get(i).getWkn() },
                    new Integer(i));
        }
    }

    Button.ClickListener addFundListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            handler.addFund(new BasicFinancialProduct.Builder().wkn(
                    fundIdField.getValue()).build());
            handler.removeFundTableItems();
            handler.showFunds();
        }
    };


    private void initFormToAddFunds() {
        fundIdField = new TextField();
        fundIdField.setId("add_fund_id_field");
        
        addFundButton = new Button("ADD_FUND");
        addFundButton.setId("add_fund_button");
        addFundButton.addClickListener(addFundListener);
        
        addComponent(fundIdField);
        addComponent(addFundButton);
    }

    private void initFundTable() {
        createFundTable();
        addComponent(fundTable);
        
        handler.showFunds();
    }

    private void createFundTable() {
        fundTable = new Table("Funds");
        fundTable.addContainerProperty("Fund", String.class, null);
    }

}
