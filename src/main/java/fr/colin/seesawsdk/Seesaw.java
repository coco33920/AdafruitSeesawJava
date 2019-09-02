package fr.colin.seesawsdk;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import fr.colin.seesawsdk.modules.GPIOModule;

import java.io.IOException;

/**
 * Main class
 */
public class Seesaw {

    public int SEESAW_ADDR = 0x49;
    public int i2CBus;
    private I2CBus i;

    private GPIOModule gpioController;


    private I2CDevice device;

    /**
     * Method to get the I2CDevice object from pi4j ( to read/write data )
     * @return
     */
    public I2CDevice getDevice() {
        return device;
    }

    /**
     * Main constructor of Seesaw, the default address of the seesaw is 0x49
     * @param i2CBus Number of the I2C Bus used ( I2CBus class contains all )
     */
    public Seesaw(int i2CBus) {
        this.i2CBus = i2CBus;
    }

    /**
     * Constructor of Seesaw to specify the address
     * @param addr Address used by the Seesaw ( default 0x49 )
     * @param i2CBus Number of the I2C Bus used ( I2CBus class contains all )
     */
    public Seesaw(int addr, int i2CBus) {
        this.SEESAW_ADDR = addr;
        this.i2CBus = i2CBus;
    }

    /**
     * GPIOModule
     * @return the build-in GPIOModule
     */
    public GPIOModule getGpioController() {
        return gpioController;
    }

    /**
     * Method to init the device, always use it before use the Seesaw object
     */
    public void init() throws PlatformAlreadyAssignedException, IOException, I2CFactory.UnsupportedBusNumberException {
        PlatformManager.setPlatform(Platform.RASPBERRYPI);
        i = I2CFactory.getInstance(i2CBus);
        device = i.getDevice(SEESAW_ADDR);
        gpioController.init();
    }


}
