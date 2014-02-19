package org.xorrr.fundsoverview.layouts;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xorrr.fundsoverview.l18n.Localization;
import org.xorrr.fundsoverview.view.DashboardViewImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Localization.class })
public class TestAddFundLayout {

    @Mock
    DashboardViewImpl view;
    @Mock
    ResourceBundle translation;

    private AddFundLayout layout;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Localization.class);
        PowerMockito.when(Localization.getMessages()).thenReturn(translation);

        layout = new AddFundLayout();
        layout.init();
        layout.setView(view);
    }

    @Test
    public void localizationIsSet() {
        PowerMockito.verifyStatic();
        Localization.getMessages();
    }
}
