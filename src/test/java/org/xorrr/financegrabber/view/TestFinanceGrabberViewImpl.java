package org.xorrr.financegrabber.view;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.presenter.FinanceGrabberViewHandler;

import com.vaadin.data.Container;

public class TestFinanceGrabberViewImpl {

    private FinanceGrabberView view;
    private FinanceGrabberViewHandler handler;

    @Before
    public void setUp() {
        view = new FinanceGrabberViewImpl();
        handler = mock(FinanceGrabberViewHandler.class);
        view.setHandler(handler);
        view.init();
    }

    @Test
    public void testAddFundButton() {
        view.getAddFundBtn().click();
        verify(handler, times(1)).addFund(
                Mockito.any(BasicFinancialProduct.class));
        verify(handler, times(1)).removeFundTableItems();
        verify(handler, times(2)).showFunds();
    }

    @Test
    public void testFundsValuesAreRequestedAfterInit() {
        verify(handler, times(1)).grabFundValues();
    }

    @Test
    public void testFundTableStructure() {
        Container cont = view.getFundTable().getContainerDataSource();

        assertEquals(String.class, cont.getType("Fund"));
        assertEquals(String.class, cont.getType("Value"));
    }
}
