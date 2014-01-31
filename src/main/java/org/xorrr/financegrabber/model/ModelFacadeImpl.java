package org.xorrr.financegrabber.model;

import java.io.IOException;
import java.util.List;

import org.xorrr.financegrabber.retrieval.FundScraper;
import org.xorrr.financegrabber.retrieval.InvalidIsinException;

public class ModelFacadeImpl implements ModelFacade {

    private FundsDatastore ds;
    private FundScraper scraper;

    public ModelFacadeImpl(FundsDatastore ds, FundScraper scraper) {
        this.ds = ds;
        this.scraper = scraper;
    }

    @Override
    public BasicFinancialProduct getBasicFinancialProduct(String isin) throws IOException,
            InvalidIsinException {
        return this.scraper.getBasicFinancialProductForIsin(isin);
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
