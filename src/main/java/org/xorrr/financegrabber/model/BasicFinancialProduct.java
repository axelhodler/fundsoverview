package org.xorrr.financegrabber.model;

public class BasicFinancialProduct {

    private String wkn;
    private String isin;

    public static class Builder {
        private String wkn;
        private String isin;

        public Builder wkn(String wkn) {
            this.wkn = wkn;
            return this;
        }

        public Builder isin(String isin) {
            this.isin = isin;
            return this;
        }

        public BasicFinancialProduct build() {
            return new BasicFinancialProduct(this);
        }
    }

    private BasicFinancialProduct(Builder builder) {
        this.wkn = builder.wkn;
        this.isin = builder.isin;
    }

    public void setWkn(String wkn) {
        this.wkn = wkn;
    }

    public String getWkn() {
        return this.wkn;
    }

}
