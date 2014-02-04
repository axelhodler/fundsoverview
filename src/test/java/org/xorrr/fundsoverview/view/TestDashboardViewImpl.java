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
        Locale en = new Locale(System.getenv("LANG"), System.getenv("COUNTRY"));
        L18nHelper helper = new L18nHelper();
        res = helper.getMessages(en);
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

        checkLabelContent(expectedName, L18nVariables.FUND);
        checkLabelContent(expectedPrice, L18nVariables.PRICE);
        checkLabelContent(expectedCurrentGrowth, L18nVariables.CURRENT_YEAR);
        checkLabelContent(expectedOneYearGrowth, L18nVariables.ONE_YEAR);
        checkLabelContent(expectedThreeYearGrowth, L18nVariables.THREE_YEARS);
        checkLabelContent(expectedFiveYearGrowth, L18nVariables.FIVE_YEARS);

        checkType(Label.class, L18nVariables.FIVE_YEARS);
        checkType(Label.class, L18nVariables.CURRENT_YEAR);
        checkType(Label.class, L18nVariables.ONE_YEAR);
        checkType(Label.class, L18nVariables.THREE_YEARS);
        checkType(Label.class, L18nVariables.PRICE);
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
