package examples.gpio;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.modules.GPIOModule;

import java.io.IOException;

import static fr.colin.seesawsdk.utils.Pins.GPIO_15;

public class InterruptEventExample {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {
        Seesaw s = new Seesaw(I2CBus.BUS_1, Platform.RASPBERRYPI);
        s.init();
        GPIOModule g = s.getGpioController();

        g.setMode(Modes.INPUT_PULLUP, GPIO_15);

        //Register a listener with the default interrupt event manager, it use one thread per pin
        g.registerListener(GPIO_15, event -> {
            System.out.println("Event with interrupt YAY !");
        });

    }


}
