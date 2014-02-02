package org.xorrr.financegrabber.model;

public class FundProduct {

    private String isin;
    private String name;
    private String currentPrice;
    private String currentGrowth;
    private String oneYearGrowth;
    private String threeYearGrowth;
    private String fiveYearGrowth;

    public static class Builder {
        private String isin;

        public Builder isin(String isin) {
            this.isin = isin;
            return this;
        }

        public FundProduct build() {
            return new FundProduct(this);
        }
    }

    private FundProduct(Builder builder) {
        this.isin = builder.isin;
    }

    public String getIsin() {
        return isin;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentGrowth() {
        return currentGrowth;
    }

    public void setCurrentGrowth(String currentGrowth) {
        this.currentGrowth = currentGrowth;
    }

    public String getOneYearGrowth() {
        return oneYearGrowth;
    }

    public void setOneYearGrowth(String oneYearGrowth) {
        this.oneYearGrowth = oneYearGrowth;
    }

    public String getThreeYearGrowth() {
        return threeYearGrowth;
    }

    public void setThreeYearGrowth(String threeYearGrowth) {
        this.threeYearGrowth = threeYearGrowth;
    }

    public String getFiveYearGrowth() {
        return fiveYearGrowth;
    }

    public void setFiveYearGrowth(String fiveYearGrowth) {
        this.fiveYearGrowth = fiveYearGrowth;
    }
}
