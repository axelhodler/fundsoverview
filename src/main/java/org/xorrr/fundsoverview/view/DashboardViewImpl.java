package org.xorrr.fundsoverview.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;
import org.xorrr.fundsoverview.layouts.LoginLayout;
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.presenter.DashboardViewHandler;

import com.google.inject.Inject;
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
    private List<Button> buttons = new ArrayList<>();
    private LoginLayout loginLayout;

    @Inject
    public DashboardViewImpl(LoginLayout layout) {
        this.loginLayout = layout;
    }

    public void init() {
        res = Localization.getMessages();

        initFundTable();

        loginLayout.setView(this);
        initLoginForm();
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
    public List<Button> getDeleteFundButtons() {
        return this.buttons;
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

    @Override
    public LoginLayout getLoginLayout() {
        return this.loginLayout;
    }

    @Override
    public TextField getFundIdField() {
        return this.fundIdField;
    }

    @Override
    public void displayAddFundForm() {
        initFormToAddFunds();
    }

    @Override
    public void handleLogin(String username, String password) {
        handler.handleLogin(username, password);
    }

    @Override
    public void removeLoginForm() {
        loginLayout.removeLoginForm();
    }

    @Override
    public void displayFundsWithDeleteButtons(List<Fund> funds) {
        for (int currentFund = 0; currentFund < funds.size(); currentFund++) {
            addFundToTable(funds, currentFund);
        }
    }

    private void initLoginForm() {
        loginLayout.init();
        addComponent(loginLayout);
    }

    private void addFundToTable(List<Fund> funds, int fundCounter) {
        Button deleteButton = new Button(
                res.getString(LocalizationStrings.DELETE));
        deleteButton.setData(funds.get(fundCounter).getIsin());
        deleteButton.addClickListener(deleteFundListener);

        fundTable.addItem(new Object[] { funds.get(fundCounter).getName(),
                createPriceLabel(funds, fundCounter),
                createCurrentYearGrowthLabel(funds, fundCounter),
                createOneYearGrowthLabel(funds, fundCounter),
                createThreeYearGrowthLabel(funds, fundCounter),
                createFiveYearGrowthLabel(funds, fundCounter), deleteButton },
                new Integer(fundCounter));

        buttons.add(deleteButton);
    }

    Button.ClickListener addFundListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            handler.addFund(new Fund.Builder().isin(fundIdField.getValue())
                    .build());
            handler.removeFundTableItems();
            handler.showFunds();
        }
    };

    Button.ClickListener deleteFundListener = new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {
            handler.deleteFund(event.getButton().getData().toString());
            handler.removeFundTableItems();
            handler.showFunds();
        }
    };

    private void initFormToAddFunds() {
        fundIdField = new TextField();
        fundIdField.setId("add_fund_id_field");

        addFundButton = new Button(res.getString(LocalizationStrings.ADD_FUND));
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
        fundTable.addContainerProperty(res.getString(LocalizationStrings.FUND),
                String.class, null);
        fundTable.addContainerProperty(
                res.getString(LocalizationStrings.PRICE), Label.class, null);
        fundTable.addContainerProperty(
                res.getString(LocalizationStrings.CURRENT_YEAR), Label.class,
                null);
        fundTable.addContainerProperty(
                res.getString(LocalizationStrings.ONE_YEAR), Label.class, null);
        fundTable.addContainerProperty(
                res.getString(LocalizationStrings.THREE_YEARS), Label.class,
                null);
        fundTable.addContainerProperty(
                res.getString(LocalizationStrings.FIVE_YEARS), Label.class,
                null);
        fundTable.addContainerProperty(
                res.getString(LocalizationStrings.DELETE), Button.class, null);
    }

    private Label createFiveYearGrowthLabel(List<Fund> funds, int currentFund) {
        Label fiveYearGrowth = new Label(funds.get(currentFund)
                .getFiveYearGrowth());
        setGrowthStyle(fiveYearGrowth);
        return fiveYearGrowth;
    }

    private Label createThreeYearGrowthLabel(List<Fund> funds, int currentFund) {
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

    private Label createOneYearGrowthLabel(List<Fund> funds, int currentFund) {
        Label oneYearGrowth = new Label(funds.get(currentFund)
                .getOneYearGrowth());
        setGrowthStyle(oneYearGrowth);
        return oneYearGrowth;
    }

    private Label createCurrentYearGrowthLabel(List<Fund> funds, int currentFund) {
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
