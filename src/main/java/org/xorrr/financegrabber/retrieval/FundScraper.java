package org.xorrr.financegrabber.retrieval;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.xorrr.financegrabber.model.BasicFinancialProduct;

public class FundScraper {

    private FundDocumentAccessor accessor;
    private FundValuesExtractor extractor;

    public FundScraper(FundDocumentAccessor accessor,
            FundValuesExtractor extractor) {
        this.accessor = accessor;
        this.extractor = extractor;
    }

    public BasicFinancialProduct getBasicFinancialProductForIsin(String isin)
            throws IOException, InvalidIsinException {
        Document doc = accessor.getDocumentForIsin(isin);
        extractor.useDocument(doc);

        BasicFinancialProduct bfp = new BasicFinancialProduct.Builder().build();
        bfp.setName(extractor.extractName());
        bfp.setValue(extractor.extractPrice());

        return bfp;
    }
}
