package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.TranslationVars;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

@RunWith(MockitoJUnitRunner.class)
public class TestAddFundLayout {

    @Mock
    DashboardViewImpl view;
    @Mock
    Localization l;

    private AddFundLayout layout;

    private void checkComponentExistence(String location) {
        Component component = layout.getComponent(location);
        assertNotNull(component);
    }

    private void checkComponentTranslation(String location, String expected) {
        Component component = layout.getComponent(location);
        assertEquals(expected, component.getCaption());
    }

    @Before
    public void setUp() {
        when(l.getTranslationFor(TranslationVars.FUND)).thenReturn(anyString());
        when(l.getTranslationFor(TranslationVars.ADD_FUND)).thenReturn(
                anyString());

        layout = new AddFundLayout(l);
        layout.init();
        layout.setView(view);
    }

    @Test
    public void fundFieldCreated() {
        checkComponentExistence(AddFundLocations.FUND_FIELD);
    }

    @Test
    public void fundFieldCaptionTranslated() {
        checkComponentTranslation(AddFundLocations.FUND_FIELD,
                l.getTranslationFor(TranslationVars.FUND));
    }

    @Test
    public void addFundButtonCreated() {
        checkComponentExistence(AddFundLocations.ADD_FUND);
    }

    @Test
    public void addFundButtonCaptionTranslated() {
        checkComponentTranslation(AddFundLocations.ADD_FUND,
                l.getTranslationFor(TranslationVars.ADD_FUND));
    }

    @Test
    public void fundButtonWorks() {
        TextField fundField = (TextField) layout.getComponent(AddFundLocations.FUND_FIELD);
        fundField.setValue("g");

        Button addFundButton = (Button) layout
                .getComponent(AddFundLocations.ADD_FUND);

        addFundButton.click();
        verify(view, times(1)).handleAddFund(fundField.getValue());
    }

    @Test
    public void isCorrectTemplateSet() {
        assertEquals(Layouts.FUND_ADD, layout.getTemplateName());
    }
}
