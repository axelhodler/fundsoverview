package org.xorrr.fundsoverview.retrieval;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.retrieval.FundDocumentAccessor;
import org.xorrr.fundsoverview.retrieval.FundScraper;
import org.xorrr.fundsoverview.retrieval.FundValuesExtractor;
import org.xorrr.fundsoverview.retrieval.InvalidIsinException;

public class TestFundScraper {

    private FundDocumentAccessor accessor;
    private FundValuesExtractor extractor;
    private FundScraper scraper;

    private void verifyExtractorBehaviour() {
        verify(extractor, times(1)).useDocument(any(Document.class));
        verify(extractor, times(1)).extractName();
        verify(extractor, times(1)).extractPrice();
        verify(extractor, times(1)).extractCurrentYearGrowth();
        verify(extractor, times(1)).extractOneYearGrowth();
        verify(extractor, times(1)).extractThreeYearGrowth();
        verify(extractor, times(1)).extractFiveYearGrowth();
    }

    @Before
    public void setUp() {
        accessor = mock(FundDocumentAccessor.class);
        extractor = mock(FundValuesExtractor.class);

        this.scraper = new FundScraper(accessor, extractor);
    }

    @Test
    public void canGetBasicFinancialProductForIsin() throws IOException,
            InvalidIsinException {
        String expectedName = "Name";
        String expectedValue = "23";
        String expectedCurrentGrowth = "1%";
        String expectedOneYearGrowth = "2%";
        String expectedThreeYearGrowth = "3%";
        String expectedFiveYearGrowth = "4%";
        String expectedIsin = "asdf";

        when(extractor.extractName()).thenReturn(expectedName);
        when(extractor.extractPrice()).thenReturn(expectedValue);
        when(extractor.extractCurrentYearGrowth()).thenReturn(
                expectedCurrentGrowth);
        when(extractor.extractOneYearGrowth())
                .thenReturn(expectedOneYearGrowth);
        when(extractor.extractThreeYearGrowth()).thenReturn(
                expectedThreeYearGrowth);
        when(extractor.extractFiveYearGrowth()).thenReturn(
                expectedFiveYearGrowth);

        Fund fp = scraper.getBasicFinancialProductForIsin(expectedIsin);

        verify(accessor, times(1)).getDocumentForIsin(expectedIsin);
        verifyExtractorBehaviour();

        assertEquals(expectedIsin, fp.getIsin());
        assertEquals(expectedName, fp.getName());
        assertEquals(expectedValue, fp.getCurrentPrice());
        assertEquals(expectedCurrentGrowth, fp.getCurrentGrowth());
        assertEquals(expectedOneYearGrowth, fp.getOneYearGrowth());
        assertEquals(expectedThreeYearGrowth, fp.getThreeYearGrowth());
        assertEquals(expectedFiveYearGrowth, fp.getFiveYearGrowth());
    }


}
