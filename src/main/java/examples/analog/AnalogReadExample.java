package examples.analog;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.charlotte.seesawsdk.Seesaw;
import fr.charlotte.seesawsdk.modules.AnalogModule;

import java.io.IOException;

import static fr.charlotte.seesawsdk.utils.Pins.*;


public class AnalogReadExample {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {
        Seesaw s = new Seesaw(I2CBus.BUS_1);
        s.init();
        AnalogModule analog = new AnalogModule(s);

        while (true) {
            System.out.println(analog.readChannel(ADC0));
            Thread.sleep(100);
        }


    }


}
