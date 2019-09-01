package fr.colin.seesawsdk.modules;

import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Module;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.events.PinDigitalStateChangeEvent;
import fr.colin.seesawsdk.events.listener.PinListener;
import fr.colin.seesawsdk.events.listener.PinListenerDigital;
import fr.colin.seesawsdk.utils.ByteUtils;
import fr.colin.seesawsdk.utils.PinUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GPIOModule extends Module {


    private final Map<Integer, List<PinListenerDigital>> listeners = new ConcurrentHashMap<>();

    public GPIOModule(Seesaw seesaw) {
        super(0x01, seesaw);
    }

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

    private void setInputPullUp(int... pins) {
        setInput(pins);
        setHigh(pins);
        pullenSet(pins);
    }

    private void setInputPullDown(int... pins) {
        setInput(pins);
        setLow(pins);
        pullenSet(pins);
    }

    public void init() {
        //   trackEvents();
    }

    public void registerListener(int pin, PinListenerDigital listenerDigital) {
        synchronized (listeners) {
            if (!listeners.containsKey(pin)) {
                listeners.put(pin, new ArrayList<>());
                trackEventsOptimized(pin);
            }
            List<PinListenerDigital> l = listeners.get(pin);
            if (!l.contains(listenerDigital)) {
                l.add(listenerDigital);
            }
        }
    }

    public void removeListener(int pin, PinListenerDigital listener) {
        synchronized (listeners) {
            if (listeners.containsKey(pin)) {
                List<PinListenerDigital> l = listeners.get(pin);
                if (l.contains(listener)) {
                    l.remove(listener);
                }

                if (l.isEmpty()) {
                    listeners.remove(pin);
                }
            }
        }
    }

    private void dispatchEvent(int pin, boolean state) {
        if (listeners.containsKey(pin)) {
            for (PinListenerDigital pinListener : listeners.get(pin)) {
                pinListener.handle(new PinDigitalStateChangeEvent(this, pin, state));
            }
        }
    }

  /*  private void trackEvents() {
        Thread t = new Thread(() -> {
            System.out.println("Start thread");
            HashMap<Integer, Boolean> state = new HashMap<>();
            while (true) {
                for (Integer pin : listeners.keySet()) {
                    if (!state.containsKey(pin)) {
                        state.put(pin, readGpio(pin));
                        continue;
                    }
                    boolean status = readGpio(pin);
                    if (state.get(pin) != status) {
                        System.out.println("Fire events for pin : " + pin);
                        dispatchEvent(pin, status);
                        state.remove(pin);
                        state.put(pin, status);
                    }
                }
            }
        });
        t.start();
    }*/

    private void trackEventsOptimized(int pin) {
        Thread t = new Thread(() -> {
            System.out.println("Start Thread for events for pin " + pin);
            boolean currentState = false;
            boolean start = false;
            while (true) {
                boolean status = readGpio(pin);
                if (!start) {
                    currentState = status;
                    start = true;
                    continue;
                }
                if (currentState != status) {
                    System.out.println("Fire events for pin " + pin + " with new status " + status + " from " + currentState + " with ");
                    currentState = status;
                    dispatchEvent(pin, status);
                }
            }
        });
        t.start();
    }


    private void setOutput(int... pins) {
        write(0x02, pins);
    }

    public void setHigh(int... pin) {
        write(0x05, pin);
    }

    public void setLow(int... pin) {
        write(0x06, pin);
    }

    public void toggle(int... pin) {
        write(0x07, pin);
    }

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

    public boolean readGpio(int pin) {
        return gpioReadBulk(pin) != 0;
    }

    private void setInput(int... pins) {
        write(0x03, pins);
    }

    public void activateInterrupt(int... pins) {
        write(0x08, pins);
    }

    public void disableInterrupt(int... pins) {
        write(0x09, pins);
    }

    public void pullenSet(int... pins) {
        write(0x0B, pins);
    }


}
