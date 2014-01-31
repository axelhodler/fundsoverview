package org.xorrr.financegrabber.presenter;

import org.xorrr.financegrabber.model.FundProduct;

public interface DashboardViewHandler {
    public void addFund(FundProduct bfp);

    public void showFunds();

    public void removeFundTableItems();
}
