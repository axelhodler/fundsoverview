package org.xorrr.fundsoverview.eventbus;

import java.util.ResourceBundle;

import org.xorrr.fundsoverview.l18n.Localization;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class Notificator {

    private Notification notification;
    private ResourceBundle bundle;

    public Notificator() {
        notification = new Notification("");
        bundle = Localization.getMessages();
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
        notification.setCaption(bundle.getString(translationVar));
        notification.show(Page.getCurrent());
    }
}
