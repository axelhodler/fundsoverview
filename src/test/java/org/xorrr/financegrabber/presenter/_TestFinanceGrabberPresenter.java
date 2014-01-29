package org.xorrr.financegrabber.presenter;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.financegrabber.model.BasicFinancialProduct;
import org.xorrr.financegrabber.model.ModelFacade;
import org.xorrr.financegrabber.retrieval.InvalidIsinException;
import org.xorrr.financegrabber.view.FinanceGrabberView;

public class _TestFinanceGrabberPresenter {

    private FinanceGrabberPresenter presenter;
    private ModelFacade model;
    private FinanceGrabberView view;

    @Before
    public void setUp() {
        this.model = mock(ModelFacade.class);
        this.view = mock(FinanceGrabberView.class);

        this.presenter = new FinanceGrabberPresenter(view, model);
    }

    @Test
    public void doesAddFundMethodWork() {
        presenter.addFund(any(BasicFinancialProduct.class));
        verify(model, times(1)).addFund(any(BasicFinancialProduct.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTheShowFundsMethod() throws IOException,
            InvalidIsinException {
        BasicFinancialProduct bfp = mock(BasicFinancialProduct.class);

        when(bfp.getWkn()).thenReturn("thewkn");

        List<BasicFinancialProduct> list = new ArrayList<BasicFinancialProduct>();
        list.add(bfp);

        when(model.getFunds()).thenReturn(list);

        assertNotNull(presenter);
        assertNotNull(view);
        assertNotNull(model);
        presenter.showFunds();
        verify(model, times(1)).getFunds();
        verify(model).getFundDocument(bfp.getWkn());
        verify(view, times(1)).displayFunds(anyList());
    }
}
