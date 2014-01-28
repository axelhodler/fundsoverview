package org.xorrr.financegrabber.presenter;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.financegrabber.db.MongoFundsDatastore;
import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.view.FinanceGrabberView;

public class _TestFinanceGrabberPresenter {

    private FinanceGrabberPresenter presenter;
    private MongoFundsDatastore model; 
    private FinanceGrabberView view;

    @Before
    public void setUp() {
        this.model = mock(MongoFundsDatastore.class);
        this.view = mock(FinanceGrabberView.class);

        this.presenter = new FinanceGrabberPresenter(view, model);
    }

    @Test
    public void doesAddFundMethodWork() {
        presenter.addFund(any(BasicFinancialProduct.class));
        verify(model, times(1)).saveProduct(any(BasicFinancialProduct.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTheShowFundsMethod() {
        assertNotNull(presenter);
        assertNotNull(view);
        assertNotNull(model);
        presenter.showFunds();
        verify(model, times(1)).getAllProducts();
        verify(view, times(1)).displayFunds(anyList());
    }
}
