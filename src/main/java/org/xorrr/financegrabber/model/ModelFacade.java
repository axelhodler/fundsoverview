package org.xorrr.financegrabber.model;

import java.io.IOException;
import java.util.List;

import org.xorrr.financegrabber.retrieval.InvalidIsinException;

public interface ModelFacade {

    BasicFinancialProduct getBasicFinancialProduct(String isin) throws IOException, InvalidIsinException;

    void addFund(BasicFinancialProduct bfp);

    List<BasicFinancialProduct> getFunds();

}
