package org.xorrr.fundsoverview.model;

import java.io.IOException;
import java.util.List;

import org.xorrr.fundsoverview.retrieval.FundScraper;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;

public class ModelFacadeImpl implements ModelFacade {

    private FundsDatastore ds;
    private FundScraper scraper;

    public ModelFacadeImpl(FundsDatastore ds, FundScraper scraper) {
        this.ds = ds;
        this.scraper = scraper;
    }

    @Override
    public FundProduct getBasicFinancialProduct(String isin) throws IOException,
            InvalidIsinException {
        return this.scraper.getBasicFinancialProductForIsin(isin);
    }

    @Override
    public void addFund(FundProduct fp) {
        this.ds.saveFund(fp);
    }

    @Override
    public List<FundProduct> getFunds() {
        return this.ds.getAllFunds();
    }

    @Override
    public void deleteFund(String isin) {
        this.ds.deleteFundByIsin(isin);
    }

}
