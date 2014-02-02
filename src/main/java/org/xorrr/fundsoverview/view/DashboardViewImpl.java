package org.xorrr.fundsoverview.view;

import java.util.List;

import org.xorrr.fundsoverview.model.FundProduct;
import org.xorrr.fundsoverview.presenter.DashboardViewHandler;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class DashboardViewImpl extends VerticalLayout implements DashboardView {

    private TextField fundIdField;
    private Button addFundButton;
    private Table fundTable;
    private DashboardViewHandler handler;

    public void init() {
        initFormToAddFunds();
        initFundTable();
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    @Override
    public void setHandler(DashboardViewHandler handler) {
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
    public void displayFunds(List<FundProduct> funds) {
        for (int currentFund = 0; currentFund < funds.size(); currentFund++) {
            addFundToTable(funds, currentFund);
        }
    }

    private void addFundToTable(List<FundProduct> funds, int fundCounter) {
        fundTable.addItem(
                new Object[] { funds.get(fundCounter).getName(),
                        funds.get(fundCounter).getCurrentPrice(),
                        createCurrentYearGrowthLabel(funds, fundCounter),
                        createOneYearGrowthLabel(funds, fundCounter),
                        createThreeYearGrowthLabel(funds, fundCounter),
                        createFiveYearGrowthLabel(funds, fundCounter) },
                new Integer(fundCounter));
    }


    Button.ClickListener addFundListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            handler.addFund(new FundProduct.Builder().isin(
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
        fundTable.addContainerProperty("Value", String.class, null);
        fundTable.addContainerProperty("cur Year", Label.class, null);
        fundTable.addContainerProperty("1 Years", Label.class, null);
        fundTable.addContainerProperty("3 Years", Label.class, null);
        fundTable.addContainerProperty("5 Years", Label.class, null);
    }

    private Label createFiveYearGrowthLabel(List<FundProduct> funds,
            int currentFund) {
        Label fiveYearGrowth = new Label(funds.get(currentFund).getFiveYearGrowth());
        fiveYearGrowth.addStyleName("posGrowth");
        return fiveYearGrowth;
    }
    
    private Label createThreeYearGrowthLabel(List<FundProduct> funds,
            int currentFund) {
        Label threeYearGrowth = new Label(funds.get(currentFund).getThreeYearGrowth());
        threeYearGrowth.addStyleName("posGrowth");
        return threeYearGrowth;
    }
    
    private Label createOneYearGrowthLabel(List<FundProduct> funds,
            int currentFund) {
        Label oneYearGrowth = new Label(funds.get(currentFund).getOneYearGrowth());
        oneYearGrowth.addStyleName("posGrowth");
        return oneYearGrowth;
    }
    
    private Label createCurrentYearGrowthLabel(List<FundProduct> funds,
            int currentFund) {
        Label currentYear = new Label(funds.get(currentFund).getCurrentGrowth());
        currentYear.addStyleName("posGrowth");
        return currentYear;
    }
}
