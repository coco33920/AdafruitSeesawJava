package fr.colin.seesawsdk.modules;

import fr.colin.seesawsdk.Module;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.utils.ByteUtils;

/**
 * *Implementation of the analogs inputs on the seesaw, at the time in the documentation was written the window mode and interrupt do not work, so it's not implemented here
 **/

public class AnalogModule extends Module {
    public AnalogModule(Seesaw seesaw) {
        super(0x09, seesaw);
    }

    public short readChannel(int channel) {
        byte[] buffer = new byte[2];
        read(getRegister(), channel, buffer, 2);
        short i = ByteUtils.readShort(buffer, 0);
        return i;
    }


}
