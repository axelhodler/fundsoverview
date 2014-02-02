package org.xorrr.fundsoverview.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.model.FundProduct;
import org.xorrr.fundsoverview.model.FundsDatastore;
import org.xorrr.fundsoverview.model.ModelFacade;
import org.xorrr.fundsoverview.model.ModelFacadeImpl;
import org.xorrr.fundsoverview.retrieval.FundScraper;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;

public class TestModelFacadeImpl {

    private ModelFacade facade;
    private FundsDatastore ds;
    private FundScraper scraper;

    @Before
    public void setUp() {
        this.scraper = mock(FundScraper.class);

        this.ds = mock(FundsDatastore.class);
        this.facade = new ModelFacadeImpl(ds, scraper);
    }

    @Test
    public void triggerGettingFundDocument() throws IOException,
            InvalidIsinException {
        facade.getBasicFinancialProduct(anyString());
        verify(scraper).getBasicFinancialProductForIsin(anyString());
    }

    @Test
    public void triggerGettingFunds() {
        facade.getFunds();
        verify(ds, times(1)).getAllProducts();
    }

    @Test
    public void triggerAddingFund() {
        facade.addFund(any(FundProduct.class));
        verify(ds, times(1)).saveProduct(any(FundProduct.class));
    }
}
