package fr.charlotte.seesawsdk.events;

public class PinDigitalStateChangeEvent extends PinEvent {

    private final boolean state;

    public PinDigitalStateChangeEvent(Object o, int pin, boolean state) {
        super(o, pin, PinEventType.GPIO_DIGITAL_CHANGE);
        this.state = state;
    }

    public boolean getState() {
        return state;
    }
}
