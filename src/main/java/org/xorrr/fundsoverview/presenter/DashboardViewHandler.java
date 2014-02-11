package org.xorrr.fundsoverview.presenter;

import org.xorrr.fundsoverview.model.Fund;
import org.xorrr.fundsoverview.view.DashboardView;

public interface DashboardViewHandler {
    public void addFund(Fund fp);

    public void showFunds();

    public void removeFundTableItems();

    public void deleteFund(String isin);

    public DashboardView getView();

    public void handleLogin(String string, String string2);
}
