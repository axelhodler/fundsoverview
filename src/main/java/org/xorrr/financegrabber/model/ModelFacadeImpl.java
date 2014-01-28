package org.xorrr.financegrabber.model;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.xorrr.financegrabber.retrieval.FundDocumentAccessor;
import org.xorrr.financegrabber.retrieval.InvalidIsinException;

public class ModelFacadeImpl implements ModelFacade {

    private FundsDatastore ds;
    private FundDocumentAccessor docAccessor;

    public ModelFacadeImpl(FundsDatastore ds, FundDocumentAccessor docAccessor) {
        this.ds = ds;
        this.docAccessor = docAccessor;
    }

    @Override
    public Document getFundDocument(String isin) throws IOException,
            InvalidIsinException {
        return this.docAccessor.getDocumentForIsin(isin);
    }

    @Override
    public void addFund(BasicFinancialProduct bfp) {
        this.ds.saveProduct(bfp);
    }

    @Override
    public List<BasicFinancialProduct> getFunds() {
        return this.ds.getAllProducts();
    }

}
