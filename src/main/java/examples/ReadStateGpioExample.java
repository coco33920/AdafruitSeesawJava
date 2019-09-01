package examples;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.modules.GPIOModule;
import static fr.colin.seesawsdk.utils.Pins.*;

import java.io.IOException;

public class ReadStateGpioExample {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {

        //Init the seesaw ( like the BlinkExample )
        Seesaw s = new Seesaw(I2CBus.BUS_1, Platform.RASPBERRYPI);
        s.init();

        //Get the gpio controller
        GPIOModule g = s.getGpioController();

        //Set the pin 15 to output
        g.setMode(Modes.OUTPUT, GPIO_15);

        //Read the state of pin 15 should be : false
        System.out.println(g.readGpio(GPIO_15));

        //Set the pin 15 to high
        g.setHigh(15);

        //Read the state of pin 15 should be : true
        System.out.println(g.readGpio(GPIO_15));
    }


}
