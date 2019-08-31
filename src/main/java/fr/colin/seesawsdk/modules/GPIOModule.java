package fr.colin.seesawsdk.modules;

import fr.colin.seesawsdk.utils.ByteUtils;
import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Module;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.utils.PinUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class GPIOModule extends Module {


    public GPIOModule(Seesaw seesaw) {
        super(Seesaw.GPIO_MODULE, seesaw);
    }

    public void setMode(Modes modes, int... pins) {
        switch (modes) {
            case INPUT:
                setInput(pins);
                break;
            case OUTPUT:
                setOutput(pins);
                break;
            default:
                setOutput(pins);
                break;
        }
    }

    private byte[] pinToAdress(int register, int... pins) {
        PinUtils p = new PinUtils();
        for (int i : pins) {
            p.add(i);
        }
        int pin = p.build();

        byte[] b = new byte[5];
        b[0] = (byte) register;
        byte[] bs = ByteUtils.intToByteArray(pin);
        b[1] = bs[0];
        b[2] = bs[1];
        b[3] = bs[2];
        b[4] = bs[3];

        return b;
    }

    private void setOutput(int... pins) {
        byte[] b = pinToAdress(0x02, pins);
        try {
            getSeesaw().getDevice().write(Seesaw.GPIO_MODULE, b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHigh(int... pin) {

        byte[] b = pinToAdress(0x05, pin);
        try {
            getSeesaw().getDevice().write(Seesaw.GPIO_MODULE, b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLow(int... pin) {
        byte[] b = pinToAdress(0x06, pin);
        try {
            getSeesaw().getDevice().write(Seesaw.GPIO_MODULE, b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toggle(int pin) {
        byte[] b = pinToAdress(0x07, pin);
        try {
            getSeesaw().getDevice().write(Seesaw.GPIO_MODULE, b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAll() {

    }

    private void setInput(int... pins) {
        byte[] b = pinToAdress(0x03, pins);
        try {
            getSeesaw().getDevice().write(Seesaw.GPIO_MODULE, b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

}
