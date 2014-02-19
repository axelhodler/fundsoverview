package org.xorrr.fundsoverview.eventbus;

import org.xorrr.fundsoverview.l18n.Localization;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class Notificator {

    private Notification notification;
    private Localization translation;

    public Notificator() {
        notification = new Notification("");
        translation = new Localization();
    }

    public void notifyFundAlreadyAdded(String translationVar) {
        setCaptionAndDisplay(translationVar);
    }

    public void notifyInvalidIsin(String translationVar) {
        setCaptionAndDisplay(translationVar);
    }

    public void notifyWrongCredentials(String wrongCredentials) {
        setCaptionAndDisplay(wrongCredentials);
    }

    private void setCaptionAndDisplay(String translationVar) {
        notification.setCaption(translation.getTranslationFor(translationVar));
        notification.show(Page.getCurrent());
    }
}
