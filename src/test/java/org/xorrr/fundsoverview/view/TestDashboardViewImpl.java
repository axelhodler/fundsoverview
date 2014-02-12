package org.xorrr.fundsoverview.view;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;
import org.xorrr.fundsoverview.layouts.LoginLayout;
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.presenter.DashboardViewHandler;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;

public class TestDashboardViewImpl {

    private DashboardViewImpl view;
    private DashboardViewHandler handler;

    private final String EXPECTED_ISIN = "12345";
    private final String EXPECTED_NAME = "foo";
    private final String EXPECTED_PRICE = "23";
    private final String EXPECTED_CURRENT_GROWTH = "25%";
    private final String EXPECTED_ONE_YEAR_GROWTH = "-50%";
    private final String EXPECTED_THREE_YEAR_GROWTH = "100%";
    private final String EXPECTED_FIVE_YEAR_GROWTH = "-125%";

    private Fund f;
    private List<Fund> funds = new ArrayList<>();
    private ResourceBundle messages;
    private Item testItem;
    private LoginLayout loginLayout;

    private void createTestFundProduct() {
        Fund f = new Fund.Builder().isin(EXPECTED_ISIN).build();
        f.setName(EXPECTED_NAME);
        f.setCurrentPrice(EXPECTED_PRICE);
        f.setCurrentGrowth(EXPECTED_CURRENT_GROWTH);
        f.setOneYearGrowth(EXPECTED_ONE_YEAR_GROWTH);
        f.setThreeYearGrowth(EXPECTED_THREE_YEAR_GROWTH);
        f.setFiveYearGrowth(EXPECTED_FIVE_YEAR_GROWTH);

        this.f = f;
    }

    private void setLocalizationMessages() {
        messages = Localization.getMessages();
    }

    private void checkType(Object expected, String id) {
        assertEquals(expected, testItem.getItemProperty(messages.getString(id))
                .getType());
    }

    private Item setTestItem() {
        return testItem = view.getFundTable().getItem(0);
    }

    private void checkLabelContent(String expected, String contentVariable) {
        assertEquals(expected,
                testItem.getItemProperty(messages.getString(contentVariable))
                        .getValue().toString());
    }

    private Label getLabelFor(String l18nvar) {
        return (Label) testItem.getItemProperty(messages.getString(l18nvar))
                .getValue();
    }

    private void checkLabelContents() {
        checkLabelContent(EXPECTED_NAME, LocalizationStrings.FUND);
        checkLabelContent(EXPECTED_PRICE, LocalizationStrings.PRICE);
        checkLabelContent(EXPECTED_CURRENT_GROWTH,
                LocalizationStrings.CURRENT_YEAR);
        checkLabelContent(EXPECTED_ONE_YEAR_GROWTH,
                LocalizationStrings.ONE_YEAR);
        checkLabelContent(EXPECTED_THREE_YEAR_GROWTH,
                LocalizationStrings.THREE_YEARS);
        checkLabelContent(EXPECTED_FIVE_YEAR_GROWTH,
                LocalizationStrings.FIVE_YEARS);
    }

    private void checkLabelTypes() {
        checkType(Label.class, LocalizationStrings.FIVE_YEARS);
        checkType(Label.class, LocalizationStrings.CURRENT_YEAR);
        checkType(Label.class, LocalizationStrings.ONE_YEAR);
        checkType(Label.class, LocalizationStrings.THREE_YEARS);
        checkType(Label.class, LocalizationStrings.PRICE);
    }

    private void clickDeleteFundButton() {
        view.getDeleteFundButtons().get(0).click();
    }

    private void displayFundsWithDeleteButtonAndTestItem() {
        view.displayFundsWithDeleteButtons(funds);
        setTestItem();
    }

    @Before
    public void setUp() {
        handler = mock(DashboardViewHandler.class);
        loginLayout = mock(LoginLayout.class);

        view = new DashboardViewImpl(loginLayout);
        view.setHandler(handler);
        view.init();

        createTestFundProduct();
        funds.add(f);

        setLocalizationMessages();
    }

    @Ignore
    @Test
    public void testAddFundButton() throws IOException, InvalidIsinException {
        view.displayAddFundForm();
        view.getAddFundBtn().click();
        verify(handler, times(1)).addFund(Mockito.any(Fund.class));
        verify(handler, times(1)).removeFundTableItems();
        verify(handler, times(2)).showFunds();
    }

    @Test
    public void basicFinancialProductsAreShown() {
        view.displayFunds(funds);

        setTestItem();

        checkLabelContents();
        checkLabelTypes();
        assertNull(testItem.getItemProperty(
                messages.getString(LocalizationStrings.DELETE)).toString());
    }

    @Test
    public void assignCorrectStyle() {
        view.displayFunds(funds);

        setTestItem();

        Label curYearGrowth = getLabelFor(LocalizationStrings.CURRENT_YEAR);
        Label oneYearGrowth = getLabelFor(LocalizationStrings.ONE_YEAR);
        Label threeYearGrowth = getLabelFor(LocalizationStrings.THREE_YEARS);
        Label fiveYearGrowth = getLabelFor(LocalizationStrings.FIVE_YEARS);
        Label price = getLabelFor(LocalizationStrings.PRICE);
        assertEquals("posGrowth", threeYearGrowth.getStyleName());
        assertEquals("negGrowth", oneYearGrowth.getStyleName());
        assertEquals("posGrowth", curYearGrowth.getStyleName());
        assertEquals("negGrowth", fiveYearGrowth.getStyleName());
        assertEquals("price", price.getStyleName());
    }

    @Test
    public void deleteButtonExists() throws IOException, InvalidIsinException {
        displayFundsWithDeleteButtonAndTestItem();

        Button testButton = (Button) testItem.getItemProperty(
                messages.getString(LocalizationStrings.DELETE)).getValue();

        assertEquals(EXPECTED_ISIN, testButton.getData());
    }

    @Test
    public void deleteButtonDeletesFund() {
        displayFundsWithDeleteButtonAndTestItem();

        clickDeleteFundButton();

        verify(handler, times(1)).deleteFund(anyString());
    }


    @Test
    public void deleteButtonClearsTheTable() {
        displayFundsWithDeleteButtonAndTestItem();

        clickDeleteFundButton();

        verify(handler, times(1)).removeFundTableItems();
    }

    @Test
    public void deleteButtonRefreshesFunds() {
        displayFundsWithDeleteButtonAndTestItem();

        clickDeleteFundButton();

        verify(handler, times(1)).showFundsWithDeleteButton();
    }

    // TODO check necessity
    @Test
    public void loginLayoutCanBeAccessed() {
        assertNotNull(view.getLoginLayout());
    }

    @Test
    public void loginIsHandledInPresenter() {
        view.handleLogin(anyString(), anyString());

        verify(handler, times(1)).handleLogin(anyString(), anyString());
    }

    @Test
    public void loginFormCanBeRemoved() {
        view.removeLoginForm();

        verify(loginLayout, times(1)).removeLoginForm();
    }

    @Test
    public void fundsCanBeDisplayedWithDeleteButton() {
        displayFundsWithDeleteButtonAndTestItem();

        checkLabelContents();
        checkLabelTypes();
        checkType(Button.class, LocalizationStrings.DELETE);
    }

    @Test
    public void fundTableItemsCanBeRemoved() {
        view.displayFunds(funds);

        view.removeTableItems();

        assertEquals(0, view.getFundTable().getItemIds().size());
    }
}
