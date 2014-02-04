package org.xorrr.fundsoverview.view;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.xorrr.fundsoverview.l18n.L18nHelper;
import org.xorrr.fundsoverview.l18n.L18nVariables;
import org.xorrr.fundsoverview.model.Fund;
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
    private ResourceBundle res;

    public void init() {
        L18nHelper helper = new L18nHelper();
        res = helper.getMessages(new Locale(System.getenv("LANG"), System
                .getenv("COUNTRY")));

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
    public void displayFunds(List<Fund> funds) {
        for (int currentFund = 0; currentFund < funds.size(); currentFund++) {
            addFundToTable(funds, currentFund);
        }
    }

    private void addFundToTable(List<Fund> funds, int fundCounter) {
        Button deleteButton = new Button();

        fundTable.addItem(new Object[] { funds.get(fundCounter).getName(),
                createPriceLabel(funds, fundCounter),
                createCurrentYearGrowthLabel(funds, fundCounter),
                createOneYearGrowthLabel(funds, fundCounter),
                createThreeYearGrowthLabel(funds, fundCounter),
                createFiveYearGrowthLabel(funds, fundCounter),
                deleteButton}, new Integer(
                fundCounter));
    }

    Button.ClickListener addFundListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            handler.addFund(new Fund.Builder().isin(
                    fundIdField.getValue()).build());
            handler.removeFundTableItems();
            handler.showFunds();
        }
    };

    private void initFormToAddFunds() {
        fundIdField = new TextField();
        fundIdField.setId("add_fund_id_field");

        addFundButton = new Button(res.getString(L18nVariables.ADD_FUND));
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
        fundTable.addContainerProperty(res.getString(L18nVariables.FUND),
                String.class, null);
        fundTable.addContainerProperty(res.getString(L18nVariables.PRICE),
                Label.class, null);
        fundTable.addContainerProperty(
                res.getString(L18nVariables.CURRENT_YEAR), Label.class, null);
        fundTable.addContainerProperty(res.getString(L18nVariables.ONE_YEAR),
                Label.class, null);
        fundTable.addContainerProperty(
                res.getString(L18nVariables.THREE_YEARS), Label.class, null);
        fundTable.addContainerProperty(res.getString(L18nVariables.FIVE_YEARS),
                Label.class, null);
        fundTable.addContainerProperty(res.getString(L18nVariables.DELETE),
                Button.class, null);
    }

    private Label createFiveYearGrowthLabel(List<Fund> funds,
            int currentFund) {
        Label fiveYearGrowth = new Label(funds.get(currentFund)
                .getFiveYearGrowth());
        setGrowthStyle(fiveYearGrowth);
        return fiveYearGrowth;
    }

    private Label createThreeYearGrowthLabel(List<Fund> funds,
            int currentFund) {
        Label threeYearGrowth = new Label(funds.get(currentFund)
                .getThreeYearGrowth());
        setGrowthStyle(threeYearGrowth);
        return threeYearGrowth;
    }

    private Label createPriceLabel(List<Fund> funds, int fundCounter) {
        Label priceField = new Label(funds.get(fundCounter).getCurrentPrice());
        priceField.setStyleName("price");
        return priceField;
    }

    private Label createOneYearGrowthLabel(List<Fund> funds,
            int currentFund) {
        Label oneYearGrowth = new Label(funds.get(currentFund)
                .getOneYearGrowth());
        setGrowthStyle(oneYearGrowth);
        return oneYearGrowth;
    }

    private Label createCurrentYearGrowthLabel(List<Fund> funds,
            int currentFund) {
        Label currentYear = new Label(funds.get(currentFund).getCurrentGrowth());
        setGrowthStyle(currentYear);
        return currentYear;
    }

    private void setGrowthStyle(Label growthLabel) {
        if (growthLabel.getValue().subSequence(0, 1).equals("-")) {
            growthLabel.addStyleName("negGrowth");
        } else {
            growthLabel.addStyleName("posGrowth");
        }
    }
}
