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
import org.xorrr.financegrabber.model.FundProduct;
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
        FundProduct bfp = new FundProduct.Builder().isin(
                validIsin).build();
        when(model.getBasicFinancialProduct(validIsin)).thenReturn(bfp);

        presenter.addFund(bfp);

        verify(model, times(1)).addFund(any(FundProduct.class));
    }

    @Test
    public void dontAddFundWithInvalidIsin() throws IOException,
            InvalidIsinException {
        String invalidIsin = "invalidIsin";
        when(model.getBasicFinancialProduct(invalidIsin)).thenThrow(
                new InvalidIsinException());
        FundProduct bfp = new FundProduct.Builder().isin(
                invalidIsin).build();

        presenter.addFund(bfp);

        verify(model, times(1)).getBasicFinancialProduct(invalidIsin);
        verify(model, times(0)).addFund(bfp);
    }

    @Test
    public void testTheShowFundsMethod() throws IOException,
            InvalidIsinException {
        FundProduct bfp = mock(FundProduct.class);
        FundProduct bfp2 = mock(FundProduct.class);

        FundProduct bfpWithExtractedInfos = new FundProduct.Builder()
                .build();
        bfpWithExtractedInfos.setName("name");

        List<FundProduct> list = new ArrayList<FundProduct>();
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
                anyListOf(FundProduct.class));
    }
}
