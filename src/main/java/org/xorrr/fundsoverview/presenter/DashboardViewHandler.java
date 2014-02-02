package org.xorrr.fundsoverview.presenter;

import org.xorrr.fundsoverview.model.FundProduct;

public interface DashboardViewHandler {
    public void addFund(FundProduct fp);

    public void showFunds();

    public void removeFundTableItems();
}
