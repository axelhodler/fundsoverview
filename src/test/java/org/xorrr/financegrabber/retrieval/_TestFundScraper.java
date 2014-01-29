package org.xorrr.financegrabber.retrieval;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Test;

public class _TestFundScraper {

    private FundDocumentAccessor accessor;
    private FundValuesExtractor extractor;

    @Test
    public void testScraperCreation() throws IOException, InvalidIsinException {
        accessor = mock(FundDocumentAccessor.class);
        extractor = mock(FundValuesExtractor.class);

        FundScraper scraper = new FundScraper(accessor, extractor);
        String isin = "asdf";
        scraper.getBasicFinancialProductForIsin(isin);
        verify(accessor, times(1)).getDocumentForIsin(isin);
        verify(extractor, times(1)).extractName();
    }

}
