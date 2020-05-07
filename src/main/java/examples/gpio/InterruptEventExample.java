package examples.gpio;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.charlotte.seesawsdk.Modes;
import fr.charlotte.seesawsdk.Seesaw;
import fr.charlotte.seesawsdk.modules.GPIOModule;

import java.io.IOException;

import static fr.charlotte.seesawsdk.utils.Pins.*;

public class InterruptEventExample {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {
        Seesaw s = new Seesaw(I2CBus.BUS_1);
        s.init();
        GPIOModule g = s.getGpioController();

        g.setMode(Modes.INPUT_PULLUP, GPIO_15, GPIO_9);
        g.setMode(Modes.OUTPUT, GPIO_10);
        g.registerListener(GPIO_15, event -> g.setHigh(GPIO_10));
        g.registerListener(GPIO_9, event -> g.setLow(GPIO_10));

        //Register a listener with the default interrupt event manager, it use one thread per pin


    }


}
