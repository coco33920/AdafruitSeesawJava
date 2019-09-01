package fr.colin.seesawsdk.events.listener;

import fr.colin.seesawsdk.events.PinDigitalStateChangeEvent;

public interface PinListenerDigital extends PinListener {

    void handle(PinDigitalStateChangeEvent event);

}
