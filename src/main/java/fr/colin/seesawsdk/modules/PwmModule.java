package fr.colin.seesawsdk.modules;

import fr.colin.seesawsdk.Module;
import fr.colin.seesawsdk.Seesaw;

import java.io.IOException;

public class PwmModule extends Module {
    public PwmModule(Seesaw seesaw) {
        super(0x08, seesaw);
    }

    public void writePwm(int pin, byte value) {
        byte[] buffer = new byte[]{0x01, (byte) pin, value};
        try {
            getSeesaw().getDevice().write(getRegister(), buffer);
        } catch (IOException e) {
        }
    }

}
