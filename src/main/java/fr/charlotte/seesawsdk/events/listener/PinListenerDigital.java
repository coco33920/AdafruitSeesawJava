package fr.charlotte.seesawsdk.events.listener;

import fr.charlotte.seesawsdk.events.PinDigitalStateChangeEvent;

public interface PinListenerDigital extends PinListener {

    void handle(PinDigitalStateChangeEvent event);

}
