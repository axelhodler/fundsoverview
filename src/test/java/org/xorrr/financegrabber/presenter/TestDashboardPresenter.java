package org.xorrr.financegrabber.presenter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
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
import org.xorrr.financegrabber.view.DashboardView;

public class TestDashboardPresenter {

    private DashboardPresenter presenter;
    private ModelFacade model;
    private DashboardView view;

    @Before
    public void setUp() {
        this.model = mock(ModelFacade.class);
        this.view = mock(DashboardView.class);

        this.presenter = new DashboardPresenter(view, model);
    }

    @Test
    public void addsFundWithValidIsin() throws IOException,
            InvalidIsinException {
        String validIsin = "validIsin";
        BasicFinancialProduct bfp = new BasicFinancialProduct.Builder().isin(
                validIsin).build();
        when(model.getBasicFinancialProduct(validIsin)).thenReturn(bfp);

        presenter.addFund(bfp);

        verify(model, times(1)).addFund(any(BasicFinancialProduct.class));
    }

    @Test
    public void dontAddFundWithInvalidIsin() throws IOException,
            InvalidIsinException {
        String invalidIsin = "invalidIsin";
        when(model.getBasicFinancialProduct(invalidIsin)).thenThrow(
                new InvalidIsinException());
        BasicFinancialProduct bfp = new BasicFinancialProduct.Builder().isin(
                invalidIsin).build();

        presenter.addFund(bfp);

        verify(model, times(1)).getBasicFinancialProduct(invalidIsin);
        verify(model, times(0)).addFund(bfp);
    }

    @Test
    public void testTheShowFundsMethod() throws IOException,
            InvalidIsinException {
        BasicFinancialProduct bfp = mock(BasicFinancialProduct.class);
        BasicFinancialProduct bfp2 = mock(BasicFinancialProduct.class);

        BasicFinancialProduct bfpWithExtractedInfos = new BasicFinancialProduct.Builder()
                .build();
        bfpWithExtractedInfos.setName("name");

        List<BasicFinancialProduct> list = new ArrayList<BasicFinancialProduct>();
        list.add(bfp);
        list.add(bfp2);

        when(model.getBasicFinancialProduct(anyString())).thenReturn(
                bfpWithExtractedInfos);
        when(bfp.getIsin()).thenReturn("thewkn");
        when(bfp2.getIsin()).thenReturn("secondwkn");
        when(model.getFunds()).thenReturn(list);

        presenter.showFunds();
        verify(model, times(1)).getFunds();
        verify(model).getBasicFinancialProduct(bfp.getIsin());
        verify(model).getBasicFinancialProduct(bfp2.getIsin());

        verify(model, times(2)).getBasicFinancialProduct(anyString());
        verify(view, times(1)).displayFunds(
                anyListOf(BasicFinancialProduct.class));
    }
}
