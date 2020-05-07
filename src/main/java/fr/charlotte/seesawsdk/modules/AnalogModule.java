package fr.charlotte.seesawsdk.modules;

import fr.charlotte.seesawsdk.Module;
import fr.charlotte.seesawsdk.Seesaw;
import fr.charlotte.seesawsdk.utils.ByteUtils;

/**
 * Analogs inputs on the seesaw, at the time in the documentation was written the window mode and interrupt do not work, so it's not implemented here
 **/

public class AnalogModule extends Module {
    public AnalogModule(Seesaw seesaw) {
        super(0x09, seesaw);
    }

    /**
     * Method to read the analog value in one of the 4 analog channel in the Seesaw
     * @param channel The channel ( pin ) to read
     * @return The 0 to 1024 value
     */
    public short readChannel(int channel) {
        byte[] buffer = new byte[2];
        read(getRegister(), channel, buffer, 2);
        short i = ByteUtils.readShort(buffer, 0);
        return i;
    }


}
