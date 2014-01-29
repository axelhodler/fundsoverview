package org.xorrr.financegrabber.model;

public class BasicFinancialProduct {

    private String wkn;
    private String isin;
    private String name;
    private String value;

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

    public String getIsin() {
        return isin;
    }

    public String getWkn() {
        return wkn;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
