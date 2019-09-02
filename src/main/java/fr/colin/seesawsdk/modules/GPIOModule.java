package fr.colin.seesawsdk.modules;

import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Module;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.events.PinDigitalStateChangeEvent;
import fr.colin.seesawsdk.events.listener.PinListenerDigital;
import fr.colin.seesawsdk.utils.ByteUtils;
import fr.colin.seesawsdk.utils.EventModes;
import fr.colin.seesawsdk.utils.PinUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main Module of the Seesaw
 */
public class GPIOModule extends Module {

    private final Map<Integer, List<PinListenerDigital>> listeners = new ConcurrentHashMap<>();

    private EventModes eventMode = EventModes.INTERRUPT_MODE;

    public GPIOModule(Seesaw seesaw) {
        super(0x01, seesaw);
    }

    /**
     * Method to change the event mode, the default is the interrupt manager
     * @param eventMode EventMode to change
     */
    public void setEventMode(EventModes eventMode) {
        this.eventMode = eventMode;
    }

    /**
     * Only public method to alter the mode of one or many pins
     * @param modes The Mode to change ( INPUT, OUTPUT and the INPUT PULLUP/DOWN )
     * @param pins pin(s) to set
     */
    public void setMode(Modes modes, int... pins) {
        switch (modes) {
            case INPUT:
                setInput(pins);
                break;
            case OUTPUT:
                setOutput(pins);
                break;
            case INPUT_PULLUP:
                setInputPullUp(pins);
                break;
            case INPUT_PULLDOWN:
                setInputPullDown(pins);
                break;
            default:
                setOutput(pins);
                break;
        }
    }

    /**
     * Method to set one or many pins to input pullup, call in setMode() method, private
     * @param pins Pin(s) to set
     */
    private void setInputPullUp(int... pins) {
        setInput(pins);
        setHigh(pins);
        pullenSet(pins);
    }

    /**
     * Method to set one or many pins to input pulldown, call in setMode() method, private
     * @param pins Pin(s) to set
     */
    private void setInputPullDown(int... pins) {
        setInput(pins);
        setLow(pins);
        pullenSet(pins);
    }

    public void init() {
        //   trackEvents();
    }

    /**
     * Register a new listener with an specified event mode
     * @param pin Pin to trigger the events
     * @param listenerDigital Listener of the events
     * @param eventMode Event Mode ( INTERRUPT/TEST )
     */
    public void registerListener(int pin, PinListenerDigital listenerDigital, EventModes eventMode) {
        synchronized (listeners) {
            if (!listeners.containsKey(pin)) {
                listeners.put(pin, new ArrayList<>());
                if (eventMode == EventModes.THREAD_TEST_MODE) {
                    trackEventsOptimized(pin);
                } else {
                    activateInterrupt(pin);
                    trackEventWithInterrupt(pin);
                }
            }
            List<PinListenerDigital> l = listeners.get(pin);
            if (!l.contains(listenerDigital)) {
                l.add(listenerDigital);
            }
        }
    }

    /**
     * Register a new listener with the default event mode
     * @param pin Pin to trigger the events
     * @param listenerDigital Listener of the events
     */
    public void registerListener(int pin, PinListenerDigital listenerDigital) {
        System.out.println(eventMode.toString());
        registerListener(pin, listenerDigital, eventMode);
    }

    /**
     * Remove an event listener
     * @param pin Pin
     * @param listener Listener to remove
     */
    public void removeListener(int pin, PinListenerDigital listener) {
        synchronized (listeners) {
            if (listeners.containsKey(pin)) {
                List<PinListenerDigital> l = listeners.get(pin);
                l.remove(listener);

                if (l.isEmpty()) {
                    listeners.remove(pin);
                }
            }
        }
    }

    /**
     * Dispatch the specified event, private
     * @param pin Pin to trigger
     * @param state State of the pin
     */
    private void dispatchEvent(int pin, boolean state) {
        if (listeners.containsKey(pin)) {
            for (PinListenerDigital pinListener : listeners.get(pin)) {
                pinListener.handle(new PinDigitalStateChangeEvent(this, pin, state));
            }
        }
    }

    /**
     * Legacy tracks of the event with the state watch, the THREAD_TEST_MODE
     * @param pin Pin to track events
     */
    private void trackEventsOptimized(int pin) {
        Thread t = new Thread(() -> {
            System.out.println("Start Thread for events for pin " + pin);
            boolean currentState = false;
            boolean start = false;
            while (true) {
                boolean status = readGpio(pin);
                System.out.println(status);
                if (!start) {
                    currentState = status;
                    start = true;
                    continue;
                }
                if (currentState != status) {
                    currentState = status;
                    dispatchEvent(pin, status);
                }
            }
        });
        t.start();
    }

    /**
     * Default event tracker, INTERRUPT_MODE, check at ~100Hz
     * @param pin Pin to track
     */
    private void trackEventWithInterrupt(int pin) {
        Thread t = new Thread(() -> {
            System.out.println("Start Thread for events for pin " + pin);
            while (true) {
                boolean b = isInterrupted(pin);
                if (b) {
                    dispatchEvent(pin, true);
                }
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * Method to set one or many pins to output, in setMode() method, private
     * @param pins Pin(s) to set
     */
    private void setOutput(int... pins) {
        write(0x02, pins);
    }

    /**
     * Method to set one or many pins to state HIGH ( TRUE )
     * @param pin Pin(s) to set
     */
    public void setHigh(int... pin) {
        write(0x05, pin);
    }

    /**
     * Method to set one or many pins to state LOW ( FALSE )
     * @param pin Pin(s) to set
     */
    public void setLow(int... pin) {
        write(0x06, pin);
    }

    /**
     * Method to toggle the state of a pin ( do not work )
     * @param pin Pin(s) to set
     */
    public void toggle(int... pin) {
        write(0x07, pin);
    }

    /**
     * Method to read the state of many pins at once ( private )
     * @param pins Pin(s) to read
     * @return The binary value
     */
    private int gpioReadBulk(int... pins) {
        PinUtils p = new PinUtils();
        for (int i : pins) {
            p.add(i);
        }
        int pin = p.build();
        byte[] buffer = new byte[4];
        read(0x01, 0x04, buffer);
        buffer[0] = (byte) (buffer[0] & 0x3F);
        int sd = ByteUtils.fromByteArray(buffer);
        return (sd & pin);
    }

    /**
     * Method to read the state of the interrupt field for many pins at once
     * @param pins Pin(s) to read
     * @return The binary value
     */
    private int readInterrupt(int... pins) {
        PinUtils p = new PinUtils();
        for (int i : pins) {
            p.add(i);
        }
        int pin = p.build();
        byte[] buffer = new byte[4];
        read(0x01, 0x0A, buffer);
        buffer[0] = (byte) (buffer[0] & 0x3F);
        int sd = ByteUtils.fromByteArray(buffer);
        return (sd & pin);
    }

    /**
     * Read the value of one pin in the interrupt field and return if it is interrupted
     * @param pin Pin to read
     * @return if the pin is interrupted
     */
    public boolean isInterrupted(int pin) {
        return readInterrupt(pin) != 0;
    }

    /**
     * Read the state of a pin
     * @param pin Pin to read
     * @return The state of the pin ( HIGH ( true ) / LOW ( false ) )
     */
    public boolean readGpio(int pin) {
        return gpioReadBulk(pin) != 0;
    }
    /**
     * Method to set one or many pins to input, call in setMode() method, private
     * @param pins Pin(s) to set
     */
    private void setInput(int... pins) {
        write(0x03, pins);
    }

    /**
     * Method to activate the triggering of the interrupt pin if the state of the pin change
     * @param pins Pin(s) to activate
     */
    public void activateInterrupt(int... pins) {
        write(0x08, pins);
    }

    /**
     * Method to deactivate the triggering of the interrupt pin
     * @param pins Pin(s) to deactivate
     */
    public void disableInterrupt(int... pins) {
        write(0x09, pins);
    }

    /**
     * Activate the inner pull resistance of pin(s), the state of the pin set if it's a pullup ( HIGH ) or a pulldown ( LOW )
     * @param pins Pin(s) to activate
     */
    private void pullenSet(int... pins) {
        write(0x0B, pins);
    }


}
