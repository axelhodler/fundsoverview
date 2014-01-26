package org.xorrr.financegrabber.presenter;

import org.xorrr.financegrabber.model.BasicFinancialProduct;

public interface FinanceGrabberViewHandler {
    public void addFund(BasicFinancialProduct bfp);

    public void showFunds();

    public void removeFundTableItems();

    public void grabFundValues();
}
