package org.xorrr.fundsoverview.layouts;

import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.TranslationVars;
import org.xorrr.fundsoverview.view.DashboardView;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.TextField;

public class AddFundLayout extends CustomLayout {

    private static final long serialVersionUID = -9041098835268681127L;
    private TextField fundField;
    private Localization translation;

    public void init() {
        translation = new Localization();

        fundField = new TextField(translation.getTranslationFor(TranslationVars.FUND));
        addComponent(fundField, AddFundLayoutLocations.FUND_FIELD);
    }

    public void setView(DashboardView view) {

    }

}
