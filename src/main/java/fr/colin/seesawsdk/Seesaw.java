package fr.colin.seesawsdk;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.impl.I2CDeviceImpl;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import com.pi4j.wiringpi.I2C;
import fr.colin.seesawsdk.modules.GPIOModule;
import fr.colin.seesawsdk.utils.PinUtils;

import java.io.IOException;
import java.util.Arrays;

public class Seesaw {

    public int SEESAW_ADDR = 0x49;
    public int i2CBus;
    private I2CBus i;
    private Platform platform;

    private GPIOModule gpioController;


    private I2CDevice device;

    public I2CDevice getDevice() {
        return device;
    }

    public Seesaw(int i2CBus, Platform platform) {
        this.i2CBus = i2CBus;
        this.platform = platform;
    }


    public Seesaw(int addr, int i2CBus, Platform platform) {
        this.SEESAW_ADDR = addr;
        this.i2CBus = i2CBus;
        this.platform = platform;
    }

    public GPIOModule getGpioController() {
        return gpioController;
    }

    public void init() throws PlatformAlreadyAssignedException, IOException, I2CFactory.UnsupportedBusNumberException {
        PlatformManager.setPlatform(platform);
        i = I2CFactory.getInstance(i2CBus);
        device = i.getDevice(SEESAW_ADDR);
        gpioController = new GPIOModule(this);
        gpioController.init();
    }


}
