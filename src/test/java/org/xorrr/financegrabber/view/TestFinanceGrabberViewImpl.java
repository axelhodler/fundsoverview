package org.xorrr.financegrabber.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.presenter.FinanceGrabberViewHandler;

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
    public void testIfAddFundMethodIsTriggeredWhenButtonIsClicked() {
        view.getAddFundBtn().click();

        verify(handler, times(1)).addFund(
                Mockito.any(BasicFinancialProduct.class));
    }

    @Test
    public void areItemsInTableRedrawnAfterAddingFund() {
        verify(handler, times(1)).showFunds();
        view.getAddFundBtn().click();
        verify(handler, times(1)).removeFundTableItems();
        verify(handler, times(1)).showFunds();
    }
}
