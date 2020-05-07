package fr.charlotte.seesawsdk.events;

import java.util.EventObject;

public class PinEvent extends EventObject {

    private int pin;
    private PinEventType type;

    public PinEvent(Object o, int pin, PinEventType type) {
        super(o);
        this.pin = pin;
        this.type = type;
    }

    public int getPin() {
        return pin;
    }

    public PinEventType getType() {
        return type;
    }
}
