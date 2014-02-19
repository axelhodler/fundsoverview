package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.TranslationVars;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.vaadin.ui.Component;

@RunWith(MockitoJUnitRunner.class)
public class TestAddFundLayout {

    @Mock
    DashboardViewImpl view;
    @Mock
    Localization translation;

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
        when(translation.getTranslationFor(TranslationVars.FUND)).thenReturn(
                anyString());
        when(translation.getTranslationFor(TranslationVars.ADD_FUND))
                .thenReturn(anyString());

        layout = new AddFundLayout(translation);
        layout.init();
        layout.setView(view);
    }

    @Test
    public void fundFieldCreated() {
        checkComponentExistence(AddFundLayoutLocations.FUND_FIELD);
    }

    @Test
    public void fundFieldCaptionTranslated() {
        checkComponentTranslation(AddFundLayoutLocations.FUND_FIELD,
                translation.getTranslationFor(TranslationVars.FUND));
    }

    @Test
    public void addFundButtonCreated() {
        checkComponentExistence(AddFundLayoutLocations.ADD_FUND);
    }

    @Test
    public void addFundButtonCaptionTranslated() {
        checkComponentTranslation(AddFundLayoutLocations.ADD_FUND,
                translation.getTranslationFor(TranslationVars.ADD_FUND));
    }
}
