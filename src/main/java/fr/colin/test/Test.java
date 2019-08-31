package fr.colin.test;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.modules.GPIOModule;

import java.io.IOException;

import static fr.colin.seesawsdk.utils.Pins.GPIO_15;

public class Test {

    //0 0

    public static void main(String... args) throws InterruptedException, I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException {
        Seesaw seesaw = new Seesaw(I2CBus.BUS_1, Platform.RASPBERRYPI);
        seesaw.init();
        System.out.println("Connected to the device at address " + seesaw.getDevice().getAddress());
        GPIOModule gpio = new GPIOModule(seesaw);
        gpio.setMode(Modes.OUTPUT, GPIO_15);
        //gpio.setMode(Modes.OUTPUT,  10);
        System.out.println("Test High on pin " + GPIO_15);

        gpio.setHigh(GPIO_15);

        Thread.sleep(1000);
        System.out.println("Test low on pin 15");
        gpio.setLow(GPIO_15);
        Thread.sleep(1000);
        System.out.println("test toggle on pin 15");
        gpio.toggle(GPIO_15);

        //     gpio.setHigh(b);
        //   gpio.setLow(15);
        //  gpio.setLow(15);
    }

}
