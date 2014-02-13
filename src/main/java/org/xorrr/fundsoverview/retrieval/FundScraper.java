package org.xorrr.fundsoverview.retrieval;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.xorrr.fundsoverview.model.Fund;

import com.google.inject.Inject;

public class FundScraper {

    private FundDocumentAccessor accessor;
    private FundValuesExtractor extractor;

    @Inject
    public FundScraper(FundDocumentAccessor accessor,
            FundValuesExtractor extractor) {
        this.accessor = accessor;
        this.extractor = extractor;
    }

    public Fund getBasicFinancialProductForIsin(String isin)
            throws IOException, InvalidIsinException {
        Document doc = accessor.getDocumentForIsin(isin);
        extractor.useDocument(doc);

        Fund fp = new Fund.Builder().isin(isin).build();
        setFundProductFields(fp);

        return fp;
    }

    private void setFundProductFields(Fund fp) {
        fp.setName(extractor.extractName());
        fp.setCurrentPrice(extractor.extractPrice());
        fp.setCurrentGrowth(extractor.extractCurrentYearGrowth());
        fp.setOneYearGrowth(extractor.extractOneYearGrowth());
        fp.setThreeYearGrowth(extractor.extractThreeYearGrowth());
        fp.setFiveYearGrowth(extractor.extractFiveYearGrowth());
    }
}
