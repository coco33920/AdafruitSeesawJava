package fr.colin.seesawsdk;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.impl.I2CDeviceImpl;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import fr.colin.seesawsdk.utils.PinUtils;

import java.io.IOException;
import java.util.Arrays;

public class Seesaw {

    public int SEESAW_ADDR = 0x49;
    public int i2CBus;
    private Platform platform;

    public static final int GPIO_MODULE = 0x01;
    public static final int ANALOG_MODULE = 0x09;
    public static final int NEOPIXEL_MODULE = 0x0E;
    public static final int EEPROM_MODULE = 0x0D;
    public static final int PWM_MODULE = 0x08;
    public static final int UART_MODULE = 0x02;

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

    public void init() throws PlatformAlreadyAssignedException, IOException, I2CFactory.UnsupportedBusNumberException {
        PlatformManager.setPlatform(platform);
        I2CBus i2c = I2CFactory.getInstance(i2CBus);
        device = i2c.getDevice(SEESAW_ADDR);
        System.out.println(device == null);
    }


}
