package fr.charlotte.seesawsdk.modules;

import fr.charlotte.seesawsdk.Module;
import fr.charlotte.seesawsdk.Seesaw;

import java.io.IOException;

public class PwmModule extends Module {
    public PwmModule(Seesaw seesaw) {
        super(0x08, seesaw);
    }

    /**
     * Method to write a pin with value in one byte
     * @param pin The PWM Pin
     * @param value The byte of the value
     */
    public void writePwm(int pin, byte value) {
        byte[] buffer = new byte[]{0x01, (byte) pin, value};
        try {
            getSeesaw().getDevice().write(getRegister(), buffer);
        } catch (IOException e) {
        }
    }

}
