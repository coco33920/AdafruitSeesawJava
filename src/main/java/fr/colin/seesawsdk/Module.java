package fr.colin.seesawsdk;

import com.pi4j.io.i2c.impl.I2CDeviceImpl;
import fr.colin.seesawsdk.utils.ByteUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;

public abstract class Module {

    private int register;
    private Seesaw seesaw;

    public Module(int register, Seesaw seesaw) {
        this.register = register;
        this.seesaw = seesaw;
    }

    public int getRegister() {
        return register;
    }

    public Seesaw getSeesaw() {
        return seesaw;
    }

    public void write(int function, int... data) {
        //TODO general low level write function
        try {
            seesaw.getDevice().write(register, ByteUtils.pinToAdress(function, data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int function) {
        try {
            seesaw.getDevice().write(register, ByteUtils.pinToAdress(function, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int register, int function) throws IOException {
        seesaw.getDevice().write(register, (byte) function);
    }

    public int read(int register, int function, byte[] buffer, int delay) {

        try {
            write(register, function);
            Thread.sleep(100);
            getSeesaw().getDevice().read(buffer, 0, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int read(int register, int function, byte[] buffer) {
        //TODO general low level read function
        return read(register, function, buffer, 100);
    }


}
