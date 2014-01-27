package org.xorrr.financegrabber.model;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.xorrr.financegrabber.db.MongoFundsDatastore;
import org.xorrr.financegrabber.retrieval.FundDocumentAccessor;

public class _TestModelFacade {

    @Test
    public void testModelFacade() {
        MongoFundsDatastore ds = mock(MongoFundsDatastore.class);
        FundDocumentAccessor docAccessor = mock(FundDocumentAccessor.class);

        IModelFacade facade = new ModelFacade(ds, docAccessor);
        facade.getFundDocument();
        facade.getFunds();
        facade.addFund();
    }

}
