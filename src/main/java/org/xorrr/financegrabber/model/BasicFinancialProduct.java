package org.xorrr.financegrabber.model;

public class BasicFinancialProduct {

    private String isin;
    private String name;
    private String currentPrice;

    public static class Builder {
        private String isin;

        public Builder isin(String isin) {
            this.isin = isin;
            return this;
        }

        public BasicFinancialProduct build() {
            return new BasicFinancialProduct(this);
        }
    }

    private BasicFinancialProduct(Builder builder) {
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
