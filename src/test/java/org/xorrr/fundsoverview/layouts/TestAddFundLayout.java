package org.xorrr.fundsoverview.layouts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.l18n.LocalizationStrings;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

import com.vaadin.ui.Component;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Localization.class })
public class TestAddFundLayout {

    @Mock
    DashboardViewImpl view;
    @Mock
    ResourceBundle translation;

    private AddFundLayout layout;

    private void checkComponentExistence(String location) {
        Component component = layout.getComponent(location);
        assertNotNull(component);
    }

    private void checkComponentTranslation(String location,
            String expectedTranslation) {
        Component component = layout.getComponent(location);
        assertEquals(expectedTranslation, component.getCaption());
    }

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Localization.class);
        PowerMockito.when(Localization.getMessages()).thenReturn(translation);

        layout = new AddFundLayout();
        layout.init();
        layout.setView(view);
    }

    @Test
    public void localizationIsAccessed() {
        PowerMockito.verifyStatic();
        Localization.getMessages();
    }

    @Test
    public void fundFieldCreated() {
        checkComponentExistence(AddFundLayoutLocations.FUND_FIELD);
    }

    @Test
    public void fundFieldCaptionTranslated() {
        checkComponentTranslation(AddFundLayoutLocations.FUND_FIELD,
                LocalizationStrings.FUND);
    }
}
