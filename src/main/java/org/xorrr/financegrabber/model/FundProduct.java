package org.xorrr.financegrabber.model;

public class FundProduct {

    private String isin;
    private String name;
    private String currentPrice;

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
}
