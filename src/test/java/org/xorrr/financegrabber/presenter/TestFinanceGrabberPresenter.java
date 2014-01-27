package org.xorrr.financegrabber.presenter;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xorrr.financegrabber.db.FinancialProductDatastore;
import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.view.FinanceGrabberView;

@RunWith(MockitoJUnitRunner.class)
public class TestFinanceGrabberPresenter {

    @Mock
    FinanceGrabberView view;
    @Mock
    FinancialProductDatastore ds;

    @InjectMocks
    FinanceGrabberPresenter presenter;

    @Test
    public void doesAddFundMethodWork() {
        presenter.addFund(any(BasicFinancialProduct.class));
        verify(ds, times(1)).saveProduct(any(BasicFinancialProduct.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTheShowFundsMethod() {
        assertNotNull(presenter);
        assertNotNull(view);
        assertNotNull(ds);
        presenter.showFunds();
        verify(ds, times(1)).getAllProducts();
        verify(view, times(1)).displayFunds(anyList());
    }
}
