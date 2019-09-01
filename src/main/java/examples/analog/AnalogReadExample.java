package examples.analog;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.modules.AnalogModule;

import java.io.IOException;

import static fr.colin.seesawsdk.utils.Pins.ADC0;

public class AnalogReadExample {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {
        Seesaw s = new Seesaw(I2CBus.BUS_1, Platform.RASPBERRYPI);
        s.init();
        AnalogModule analog = new AnalogModule(s);

        while (true){
            System.out.println(analog.readChannel(ADC0));
            Thread.sleep(100);
        }


    }


}
