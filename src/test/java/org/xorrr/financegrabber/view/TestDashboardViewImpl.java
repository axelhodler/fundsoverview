package org.xorrr.financegrabber.view;

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
import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.presenter.DashboardViewHandler;
import org.xorrr.financegrabber.retrieval.InvalidIsinException;

import com.vaadin.data.Item;

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
                Mockito.any(BasicFinancialProduct.class));
        verify(handler, times(1)).removeFundTableItems();
        verify(handler, times(2)).showFunds();
    }

    @Test
    public void basicFinancialProductsAreShown() {
        String expectedName = "foo";
        String expectedPrice = "23";
        BasicFinancialProduct bfp = new BasicFinancialProduct.Builder().build();
        bfp.setName(expectedName);
        bfp.setCurrentPrice(expectedPrice);
        List<BasicFinancialProduct> funds = new ArrayList<>();
        funds.add(bfp);

        view.displayFunds(funds);

        Item itm = view.getFundTable().getItem(0);
        assertEquals(expectedName, itm.getItemProperty("Fund").toString());
        assertEquals(expectedPrice, itm.getItemProperty("Value").toString());
    }
}
