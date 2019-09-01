package examples;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.modules.GPIOModule;

import java.io.IOException;

import static fr.colin.seesawsdk.utils.Pins.GPIO_15;

public class BlinkExample {

    public static void main(String... args) throws InterruptedException, I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException {
        /*
         * Initialize the device at the standard address ( 0x49 ) for the RPi platform
         */
        Seesaw seesaw = new Seesaw(I2CBus.BUS_1, Platform.RASPBERRYPI);

        /*
        Init the device ( and the modules )
         */
        seesaw.init();

        System.out.println("Connected to the device at address " + seesaw.getDevice().getAddress());

        /*
        Get the GPIOController of the seesaw, to manage the GPIOs
         */
        GPIOModule gpio = seesaw.getGpioController();
        gpio.setMode(Modes.OUTPUT, GPIO_15);


        /*
        Blinking
         */
        gpio.setHigh(GPIO_15);
        while (true) {
            Thread.sleep(1000);
            gpio.setLow(GPIO_15);
            Thread.sleep(1000);
            gpio.setHigh(GPIO_15);
        }
        //     gpio.setHigh(b);
        //   gpio.setLow(15);
        //  gpio.setLow(15);
    }

}
