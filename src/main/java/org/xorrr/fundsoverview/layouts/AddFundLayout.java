package org.xorrr.fundsoverview.layouts;

import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.TranslationVars;
import org.xorrr.fundsoverview.view.DashboardView;

import com.google.inject.Inject;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class AddFundLayout extends CustomLayout {

    private static final long serialVersionUID = -9041098835268681127L;
    private TextField fundField;
    private Localization translation;
    private Button addFund;
    private DashboardView view;

    @Inject
    public AddFundLayout(Localization translation) {
        this.translation = translation;
    }

    public void init() {
        fundField = new TextField(translation.getTranslationFor(TranslationVars.FUND));
        addComponent(fundField, AddFundLocations.FUND_FIELD);

        addFund = new Button(translation.getTranslationFor(TranslationVars.ADD_FUND));
        addFund.addClickListener(loginButtonListener);
        addComponent(addFund, AddFundLocations.ADD_FUND);
    }

    public void setView(DashboardView view) {
        this.view = view;
    }

    Button.ClickListener loginButtonListener = new Button.ClickListener() {
        private static final long serialVersionUID = -8573483079166742117L;

        @Override
        public void buttonClick(ClickEvent event) {
            view.handleAddFund(fundField.getValue());
        }

    };

}
