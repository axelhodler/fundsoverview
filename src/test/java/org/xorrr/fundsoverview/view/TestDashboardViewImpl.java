package org.xorrr.fundsoverview.view;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
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
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.presenter.DashboardViewHandler;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;

public class TestDashboardViewImpl {

    private DashboardView view;
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
    Locale en;
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

    private Label getLabelFor(String l18nvar) {
        return (Label) testItem.getItemProperty(
                res.getString(l18nvar)).getValue();
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
        verify(handler, times(1)).addFund(Mockito.any(Fund.class));
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
        checkType(Button.class, L18nVariables.DELETE);
    }

    @Test
    public void assignCorrectStyle() {
        view.displayFunds(funds);

        setTestItem();

        Label curYearGrowth = getLabelFor(L18nVariables.CURRENT_YEAR);
        Label oneYearGrowth = getLabelFor(L18nVariables.ONE_YEAR);
        Label threeYearGrowth = getLabelFor(L18nVariables.THREE_YEARS);
        Label fiveYearGrowth = getLabelFor(L18nVariables.FIVE_YEARS);
        Label price = getLabelFor(L18nVariables.PRICE);

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
        Button testButton = (Button) testItem.getItemProperty(res.getString(L18nVariables.DELETE)).getValue();

        assertEquals(expectedIsin, testButton.getData());

        view.getDeleteFundButtons().get(0).click();

        verify(handler, times(1)).deleteFund(anyString());
    }
}
