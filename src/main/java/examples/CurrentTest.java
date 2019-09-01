package examples;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.modules.GPIOModule;
import fr.colin.seesawsdk.utils.PinUtils;
import fr.colin.seesawsdk.utils.Pins;

import java.io.IOException;

public class CurrentTest {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {
        Seesaw s = new Seesaw(I2CBus.BUS_1, Platform.RASPBERRYPI);
        s.init();

        GPIOModule g = s.getGpioController();
        g.setMode(Modes.OUTPUT, 15);
        System.out.println(g.readGpio(15));
        g.setHigh(15);
        System.out.println(g.readGpio(15));
        System.out.println(g.readGpio(14));
    }


}
