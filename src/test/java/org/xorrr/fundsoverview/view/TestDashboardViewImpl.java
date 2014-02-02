package org.xorrr.fundsoverview.view;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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

    @Before
    public void setUp() {
        view = new DashboardViewImpl();
        handler = mock(DashboardViewHandler.class);
        view.setHandler(handler);
        view.init();
    }

    @Test
    public void testAddFundButton() throws IOException, InvalidIsinException {
        view.getAddFundBtn().click();
        verify(handler, times(1)).addFund(
                Mockito.any(FundProduct.class));
        verify(handler, times(1)).removeFundTableItems();
        verify(handler, times(2)).showFunds();
    }

    @Test
    public void basicFinancialProductsAreShown() {
        String expectedName = "foo";
        String expectedPrice = "23";
        String expectedCurrentGrowth = "25%";
        String expectedOneYearGrowth = "50%";
        String expectedThreeYearGrowth = "100%";
        String expectedFiveYearGrowth = "125%";

        FundProduct fp = new FundProduct.Builder().build();
        fp.setName(expectedName);
        fp.setCurrentPrice(expectedPrice);
        fp.setCurrentGrowth(expectedCurrentGrowth);
        fp.setOneYearGrowth(expectedOneYearGrowth);
        fp.setThreeYearGrowth(expectedThreeYearGrowth);
        fp.setFiveYearGrowth(expectedFiveYearGrowth);

        List<FundProduct> funds = new ArrayList<>();
        funds.add(fp);

        view.displayFunds(funds);

        Item itm = view.getFundTable().getItem(0);
        assertEquals(expectedName, itm.getItemProperty("Fund").toString());
        assertEquals(expectedPrice, itm.getItemProperty("Value").toString());
        assertEquals(Label.class, itm.getItemProperty("cur Year").getType());
        assertEquals(expectedCurrentGrowth, itm.getItemProperty("cur Year").getValue().toString());
        assertEquals(Label.class, itm.getItemProperty("1 Years").getType());
        assertEquals(expectedOneYearGrowth, itm.getItemProperty("1 Years").getValue().toString());
        assertEquals(Label.class, itm.getItemProperty("3 Years").getType());
        assertEquals(expectedThreeYearGrowth, itm.getItemProperty("3 Years").getValue().toString());
        assertEquals(Label.class, itm.getItemProperty("5 Years").getType());
        assertEquals(expectedFiveYearGrowth, itm.getItemProperty("5 Years").getValue().toString());
    }
}