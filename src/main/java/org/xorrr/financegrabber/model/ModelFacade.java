package org.xorrr.financegrabber.model;

import org.xorrr.financegrabber.db.FinancialProductDatastore;
import org.xorrr.financegrabber.retrieval.FundDocumentAccessor;

public class ModelFacade implements IModelFacade {

    private FinancialProductDatastore ds;
    private FundDocumentAccessor docAccessor;

    public ModelFacade(FinancialProductDatastore ds,
            FundDocumentAccessor docAccessor) {
        this.ds = ds;
        this.docAccessor = docAccessor;
    }

    @Override
    public void getFundDocument() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addFund() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void getFunds() {
        // TODO Auto-generated method stub
        
    }

}
