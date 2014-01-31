package org.xorrr.financegrabber.presenter;

import org.xorrr.financegrabber.model.FundProduct;

public interface DashboardViewHandler {
    public void addFund(FundProduct fp);

    public void showFunds();

    public void removeFundTableItems();
}
