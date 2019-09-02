package examples;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.modules.GPIOModule;

import java.io.IOException;

import static fr.colin.seesawsdk.utils.Pins.*;

public class CurrentTest {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {
        Seesaw s = new Seesaw(I2CBus.BUS_1);
        s.init();

        GPIOModule g = s.getGpioController();

        g.setMode(Modes.INPUT_PULLUP, GPIO_15, GPIO_9);
        g.setMode(Modes.OUTPUT, GPIO_10);
        g.setHigh(10);
        g.registerListener(GPIO_15, event -> g.setHigh(GPIO_10));
        g.registerListener(GPIO_9, event -> g.setLow(GPIO_10));


    }


}
