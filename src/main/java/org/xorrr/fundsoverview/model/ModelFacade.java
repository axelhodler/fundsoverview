package org.xorrr.fundsoverview.model;

import java.io.IOException;
import java.util.List;

import org.xorrr.fundsoverview.retrieval.InvalidIsinException;

public interface ModelFacade {

    FundProduct getBasicFinancialProduct(String isin) throws IOException, InvalidIsinException;

    void addFund(FundProduct fp);

    List<FundProduct> getFunds();

}
