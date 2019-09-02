package fr.colin.seesawsdk;

import fr.colin.seesawsdk.utils.ByteUtils;

import java.io.IOException;

/**
 * Abstract class for the Modules ( GPIO, Analog and PWM )
 */
public abstract class Module {

    private int register;
    private Seesaw seesaw;

    /**
     * Main constructor of the abstract class Module
     * @param register Inner address of the Module
     * @param seesaw Device to attach the module to
     */
    public Module(int register, Seesaw seesaw) {
        this.register = register;
        this.seesaw = seesaw;
    }

    /**
     * Getter for the inner address of the Module
     * @return the inner address of the Module
     */
    public int getRegister() {
        return register;
    }

    /**
     * Getter for the device
     * @return the device
     */
    public Seesaw getSeesaw() {
        return seesaw;
    }

    /**
     * Standard common to modules write function (used mainly in GPIOModule )
     * @param function The inner address of the function
     * @param data All the data to write ( pins )
     */
    public void write(int function, int... data) {
        try {
            seesaw.getDevice().write(register, ByteUtils.pinToAdress(function, data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Standard common to modules write function used mainly in the read process
     * @param register The inner address of the module
     * @param function The inner address of the function
     * @throws IOException Error in write with the device
     */
    public void write(int register, int function) throws IOException {
        seesaw.getDevice().write(register, (byte) function);
    }

    /**
     * Standard read function ( used in Analog and GPIO Modules )
     * @param register The inner address of the module
     * @param function The inner address of the function
     * @param buffer Buffer of bytes to read into
     * @param delay Delay between the write of the register and function and the read
     * @return The number of bytes
     */
    public int read(int register, int function, byte[] buffer, int delay) {

        try {
            write(register, function);
            Thread.sleep(delay);
            return getSeesaw().getDevice().read(buffer, 0, buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Standard read function, this call the read method with a fix delay
     * @param register The inner address of the module
     * @param function The inner address of the function
     * @param buffer Buffer of bytes to read into
     * @return The number of bytes
     */
    public int read(int register, int function, byte[] buffer) {
        //TODO general low level read function
        return read(register, function, buffer, 5);
    }


}
