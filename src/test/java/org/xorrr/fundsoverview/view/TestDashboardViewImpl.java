package org.xorrr.fundsoverview.view;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xorrr.fundsoverview.l18n.L18nHelper;
import org.xorrr.fundsoverview.l18n.L18nVariables;
import org.xorrr.fundsoverview.model.FundProduct;
import org.xorrr.fundsoverview.presenter.DashboardViewHandler;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;
import org.xorrr.fundsoverview.view.DashboardView;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.vaadin.data.Item;
import com.vaadin.ui.Label;

public class TestDashboardViewImpl {

    private DashboardView view;
    private DashboardViewHandler handler;

    String expectedName = "foo";
    String expectedPrice = "23";
    String expectedCurrentGrowth = "25%";
    String expectedOneYearGrowth = "-50%";
    String expectedThreeYearGrowth = "100%";
    String expectedFiveYearGrowth = "-125%";
    FundProduct fp;
    List<FundProduct> funds = new ArrayList<>();
    Locale en;
    ResourceBundle res;
    private Item testItem;

    private void createTestFundProduct() {
        FundProduct fp = new FundProduct.Builder().build();
        fp.setName(expectedName);
        fp.setCurrentPrice(expectedPrice);
        fp.setCurrentGrowth(expectedCurrentGrowth);
        fp.setOneYearGrowth(expectedOneYearGrowth);
        fp.setThreeYearGrowth(expectedThreeYearGrowth);
        fp.setFiveYearGrowth(expectedFiveYearGrowth);
        this.fp = fp;
    }

    private void setUpLocale() {
        Locale en = new Locale("en", "US");
        L18nHelper helper = new L18nHelper();
        res = helper.getMessages(en);
    }

    private void checkType(String id) {
        assertEquals(Label.class, testItem.getItemProperty(res.getString(id))
                .getType());
    }

    private Item setTestItem() {
        return testItem = view.getFundTable().getItem(0);
    }

    @Before
    public void setUp() {
        view = new DashboardViewImpl();
        handler = mock(DashboardViewHandler.class);
        view.setHandler(handler);
        view.init();
        createTestFundProduct();
        funds.add(fp);

        setUpLocale();
    }

    @Test
    public void testAddFundButton() throws IOException, InvalidIsinException {
        view.getAddFundBtn().click();
        verify(handler, times(1)).addFund(Mockito.any(FundProduct.class));
        verify(handler, times(1)).removeFundTableItems();
        verify(handler, times(2)).showFunds();
    }

    @Test
    public void basicFinancialProductsAreShown() {
        view.displayFunds(funds);

        setTestItem();
        assertEquals(expectedName, testItem.getItemProperty(res.getString(L18nVariables.FUND))
                .toString());
        assertEquals(expectedPrice, testItem.getItemProperty(res.getString(L18nVariables.PRICE))
                .toString());
        assertEquals(expectedCurrentGrowth,
                testItem.getItemProperty(res.getString(L18nVariables.CURRENT_YEAR)).getValue()
                        .toString());
        assertEquals(expectedOneYearGrowth,
                testItem.getItemProperty(res.getString(L18nVariables.ONE_YEAR)).getValue()
                        .toString());
        assertEquals(expectedThreeYearGrowth,
                testItem.getItemProperty(res.getString(L18nVariables.THREE_YEARS)).getValue()
                        .toString());
        assertEquals(expectedFiveYearGrowth,
                testItem.getItemProperty(res.getString(L18nVariables.FIVE_YEARS)).getValue()
                        .toString());

        checkType(L18nVariables.FIVE_YEARS);
        checkType(L18nVariables.CURRENT_YEAR);
        checkType(L18nVariables.ONE_YEAR);
        checkType(L18nVariables.THREE_YEARS);
    }

    @Test
    public void assignCorrectStyle() {
        view.displayFunds(funds);

        setTestItem();

        Label curYearGrowth = (Label) testItem.getItemProperty(
                res.getString(L18nVariables.CURRENT_YEAR)).getValue();
        Label oneYearGrowth = (Label) testItem.getItemProperty(
                res.getString(L18nVariables.ONE_YEAR)).getValue();
        Label threeYearGrowth = (Label) testItem.getItemProperty(
                res.getString(L18nVariables.THREE_YEARS)).getValue();
        Label fiveYearGrowth = (Label) testItem.getItemProperty(
                res.getString(L18nVariables.FIVE_YEARS)).getValue();

        assertEquals("posGrowth", threeYearGrowth.getStyleName());
        assertEquals("negGrowth", oneYearGrowth.getStyleName());
        assertEquals("posGrowth", curYearGrowth.getStyleName());
        assertEquals("negGrowth", fiveYearGrowth.getStyleName());
    }
}
