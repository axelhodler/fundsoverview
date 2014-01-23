package org.xorrr.financegrabber.presenter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.financegrabber.db.FinancialProductDatastore;
import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.view.FinanceGrabberView;

public class TestFinanceGrabberPresenter {

    private FinanceGrabberView view;
    private FinanceGrabberPresenter presenter;
    private FinancialProductDatastore ds;

    @Before
    public void setUp() throws Exception {
        view = mock(FinanceGrabberView.class);
        ds = mock(FinancialProductDatastore.class);

        presenter = new FinanceGrabberPresenter(view, ds);
    }

    @Test
    public void doesAddFundMethodWork() {
        BasicFinancialProduct anyBfp = any(BasicFinancialProduct.class);

        presenter.addFund(anyBfp);
        verify(ds, times(1)).saveProduct(anyBfp);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTheShowFundsMethod() {
        presenter.showFunds();
        verify(ds, times(1)).getAllProducts();
        verify(view, times(1)).showFunds(anyList());
    }
}
