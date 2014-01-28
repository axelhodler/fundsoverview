package org.xorrr.financegrabber.model;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.xorrr.financegrabber.retrieval.InvalidIsinException;

public interface IModelFacade {

    Document getFundDocument(String isin) throws IOException, InvalidIsinException;

    void addFund(BasicFinancialProduct bfp);

    List<BasicFinancialProduct> getFunds();

}
