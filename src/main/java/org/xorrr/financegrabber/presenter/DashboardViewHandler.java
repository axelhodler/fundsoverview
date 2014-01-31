package org.xorrr.financegrabber.presenter;

import org.xorrr.financegrabber.model.BasicFinancialProduct;

public interface DashboardViewHandler {
    public void addFund(BasicFinancialProduct bfp);

    public void showFunds();

    public void removeFundTableItems();
}
