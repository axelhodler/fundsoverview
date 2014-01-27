package org.xorrr.financegrabber.model;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.xorrr.financegrabber.db.FinancialProductDatastore;
import org.xorrr.financegrabber.retrieval.FundDocumentAccessor;

public class TestModelFacade {

    @Test
    public void testModelFacade() {
        FinancialProductDatastore ds = mock(FinancialProductDatastore.class);
        FundDocumentAccessor docAccessor = mock(FundDocumentAccessor.class);

        IModelFacade facade = new ModelFacade(ds, docAccessor);
        facade.getFundDocument();
        facade.getFunds();
        facade.addFund();
    }

}
