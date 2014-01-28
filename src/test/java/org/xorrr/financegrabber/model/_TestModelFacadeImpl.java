package org.xorrr.financegrabber.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.xorrr.financegrabber.retrieval.FundDocumentAccessor;
import org.xorrr.financegrabber.retrieval.InvalidIsinException;

public class _TestModelFacadeImpl {

    private ModelFacade facade;
    private FundsDatastore ds;
    private FundDocumentAccessor docAccessor;

    @Before
    public void setUp() {
        this.docAccessor = mock(FundDocumentAccessor.class);

        this.ds = mock(FundsDatastore.class);
        this.facade = new ModelFacadeImpl(ds, docAccessor);
    }

    @Test
    public void triggerGettingFundDocument() throws IOException,
            InvalidIsinException {
        facade.getFundDocument(anyString());
        verify(docAccessor).getDocumentForIsin(anyString());
    }

    @Test
    public void triggerGettingFunds() {
        facade.getFunds();
        verify(ds, times(1)).getAllProducts();
    }

    @Test
    public void triggerAddingFund() {
        facade.addFund(any(BasicFinancialProduct.class));
        verify(ds, times(1)).saveProduct(any(BasicFinancialProduct.class));
    }
}
