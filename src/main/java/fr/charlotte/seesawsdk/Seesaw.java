package fr.charlotte.seesawsdk;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import fr.charlotte.seesawsdk.modules.GPIOModule;

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
     * @return The I2CDevice
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
     * Init the device, must be call before use it
     * @throws PlatformAlreadyAssignedException Error with the platform
     * @throws IOException Error with the reading of the bus
     * @throws I2CFactory.UnsupportedBusNumberException Error of the reading with the bus number
     */
    public void init() throws PlatformAlreadyAssignedException, IOException, I2CFactory.UnsupportedBusNumberException {
        PlatformManager.setPlatform(Platform.RASPBERRYPI);
        i = I2CFactory.getInstance(i2CBus);
        device = i.getDevice(SEESAW_ADDR);
        gpioController = new GPIOModule(this);
        gpioController.init();
    }


}
