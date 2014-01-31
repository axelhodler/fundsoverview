package org.xorrr.financegrabber.retrieval;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.xorrr.financegrabber.model.FundProduct;

public class FundScraper {

    private FundDocumentAccessor accessor;
    private FundValuesExtractor extractor;

    public FundScraper(FundDocumentAccessor accessor,
            FundValuesExtractor extractor) {
        this.accessor = accessor;
        this.extractor = extractor;
    }

    public FundProduct getBasicFinancialProductForIsin(String isin)
            throws IOException, InvalidIsinException {
        Document doc = accessor.getDocumentForIsin(isin);
        extractor.useDocument(doc);

        FundProduct fp = new FundProduct.Builder().build();
        fp.setName(extractor.extractName());
        fp.setCurrentPrice(extractor.extractPrice());

        return fp;
    }
}
