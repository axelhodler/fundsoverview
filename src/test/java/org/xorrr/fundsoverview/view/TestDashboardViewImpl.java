package org.xorrr.fundsoverview.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    String expectedIsin = "12345";
    String expectedName = "foo";
    String expectedPrice = "23";
    String expectedCurrentGrowth = "25%";
    String expectedOneYearGrowth = "-50%";
    String expectedThreeYearGrowth = "100%";
    String expectedFiveYearGrowth = "-125%";
    Fund fp;
    List<Fund> funds = new ArrayList<>();
    ResourceBundle res;
    Item testItem;

    private void createTestFundProduct() {
        Fund fp = new Fund.Builder().isin(expectedIsin).build();
        fp.setName(expectedName);
        fp.setCurrentPrice(expectedPrice);
        fp.setCurrentGrowth(expectedCurrentGrowth);
        fp.setOneYearGrowth(expectedOneYearGrowth);
        fp.setThreeYearGrowth(expectedThreeYearGrowth);
        fp.setFiveYearGrowth(expectedFiveYearGrowth);
        this.fp = fp;
    }

    private void setL18nMessages() {
        res = Localization.getMessages();
    }

    private void checkType(Object expected, String id) {
        assertEquals(expected, testItem.getItemProperty(res.getString(id))
                .getType());
    }

    private Item setTestItem() {
        return testItem = view.getFundTable().getItem(0);
    }

    private void checkLabelContent(String expected, String contentVariable) {
        assertEquals(expected,
                testItem.getItemProperty(res.getString(contentVariable))
                        .getValue().toString());
    }

    private Label getLabelFor(String l18nvar) {
        return (Label) testItem.getItemProperty(res.getString(l18nvar))
                .getValue();
    }

    @Before
    public void setUp() {
        LoginLayout layout = mock(LoginLayout.class);

        view = new DashboardViewImpl(layout);
        handler = mock(DashboardViewHandler.class);
        view.setHandler(handler);
        view.init();

        createTestFundProduct();
        funds.add(fp);

        setL18nMessages();
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

        checkLabelContent(expectedName, LocalizationStrings.FUND);
        checkLabelContent(expectedPrice, LocalizationStrings.PRICE);
        checkLabelContent(expectedCurrentGrowth,
                LocalizationStrings.CURRENT_YEAR);
        checkLabelContent(expectedOneYearGrowth, LocalizationStrings.ONE_YEAR);
        checkLabelContent(expectedThreeYearGrowth,
                LocalizationStrings.THREE_YEARS);
        checkLabelContent(expectedFiveYearGrowth,
                LocalizationStrings.FIVE_YEARS);

        checkType(Label.class, LocalizationStrings.FIVE_YEARS);
        checkType(Label.class, LocalizationStrings.CURRENT_YEAR);
        checkType(Label.class, LocalizationStrings.ONE_YEAR);
        checkType(Label.class, LocalizationStrings.THREE_YEARS);
        checkType(Label.class, LocalizationStrings.PRICE);
        checkType(Button.class, LocalizationStrings.DELETE);
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
    public void testDeleteFundButton() throws IOException, InvalidIsinException {
        view.displayFunds(funds);
        setTestItem();
        Button testButton = (Button) testItem.getItemProperty(
           res.getString(LocalizationStrings.DELETE)).getValue();

        assertEquals(expectedIsin, testButton.getData());

        view.getDeleteFundButtons().get(0).click();

        verify(handler, times(1)).deleteFund(anyString());
        verify(handler, times(1)).removeFundTableItems();
        verify(handler, times(2)).showFunds();
    }

    //TODO check necessity
    @Test
    public void loginLayoutCanBeAccessed() {
        assertNotNull(view.getLoginLayout());
    }

    @Test
    public void loginIsHandledInPresenter() {
        view.handleLogin("", "");

        verify(handler, times(1)).handleLogin("", "");
    }
}
