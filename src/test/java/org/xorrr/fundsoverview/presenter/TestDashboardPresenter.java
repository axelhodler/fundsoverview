package org.xorrr.fundsoverview.presenter;

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
import org.xorrr.fundsoverview.events.EventBus;
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.model.ModelFacade;
import org.xorrr.fundsoverview.presenter.DashboardPresenter;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;
import org.xorrr.fundsoverview.view.DashboardView;

public class TestDashboardPresenter {

    private DashboardPresenter presenter;
    private ModelFacade model;
    private DashboardView view;
    private EventBus bus;

    private String validIsin = "validIsin";
    private String invalidIsin = "invalidIsin";

    @Before
    public void setUp() {
        this.model = mock(ModelFacade.class);
        this.view = mock(DashboardView.class);
        this.bus = mock(EventBus.class);

        this.presenter = new DashboardPresenter(view, model, bus);
    }

    @Test
    public void addsFundWithValidIsin() throws IOException,
            InvalidIsinException {
        Fund fp = new Fund.Builder().isin(
                validIsin).build();
        when(model.getBasicFinancialProduct(validIsin)).thenReturn(fp);

        presenter.addFund(fp);

        verify(model, times(1)).addFund(any(Fund.class));
    }

    @Test
    public void dontAddFundWithInvalidIsin() throws IOException,
            InvalidIsinException {
        when(model.getBasicFinancialProduct(invalidIsin)).thenThrow(
                new InvalidIsinException());
        Fund fp = new Fund.Builder().isin(
                invalidIsin).build();

        presenter.addFund(fp);

        verify(model, times(1)).getBasicFinancialProduct(invalidIsin);
        verify(model, times(0)).addFund(fp);
    }

    @Test
    public void testTheShowFundsMethod() throws IOException,
            InvalidIsinException {
        Fund fp = mock(Fund.class);
        Fund fp2 = mock(Fund.class);

        Fund fpWithExtractedInfos = new Fund.Builder()
                .build();
        fpWithExtractedInfos.setName("name");

        List<Fund> list = new ArrayList<Fund>();
        list.add(fp);
        list.add(fp2);

        when(model.getBasicFinancialProduct(anyString())).thenReturn(
                fpWithExtractedInfos);
        when(fp.getIsin()).thenReturn("thewkn");
        when(fp2.getIsin()).thenReturn("secondwkn");
        when(model.getFunds()).thenReturn(list);

        presenter.showFunds();
        verify(model, times(1)).getFunds();
        verify(model).getBasicFinancialProduct(fp.getIsin());
        verify(model).getBasicFinancialProduct(fp2.getIsin());

        verify(model, times(2)).getBasicFinancialProduct(anyString());
        verify(view, times(1)).displayFunds(
                anyListOf(Fund.class));
    }

    @Test
    public void testFundDeletion() {
        presenter.deleteFund(anyString());
        verify(model, times(1)).deleteFund(anyString());
    }
}
