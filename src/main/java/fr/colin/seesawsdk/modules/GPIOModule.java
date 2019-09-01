package fr.colin.seesawsdk.modules;

import com.igormaznitsa.jbbp.io.JBBPByteOrder;
import com.igormaznitsa.jbbp.io.JBBPOut;
import com.pi4j.io.gpio.*;
import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Module;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.utils.ByteUtils;
import fr.colin.seesawsdk.utils.PinUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class GPIOModule extends Module {


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
        pullenSet(pins);
        setHigh(pins);
    }

    private void setInputPullDown(int... pins) {
        setInput(pins);
        pullenSet(pins);
        setLow(pins);
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

    public int gpioReadBulk(int... pins) {
        //0x01 0x04 bytes[]
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
