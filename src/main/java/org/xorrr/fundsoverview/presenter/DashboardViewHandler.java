package org.xorrr.fundsoverview.presenter;

import org.xorrr.fundsoverview.model.Fund;

public interface DashboardViewHandler {
    public void addFund(Fund fp);

    public void showFunds();

    public void removeFundTableItems();

    public void deleteFund(String isin);
}
