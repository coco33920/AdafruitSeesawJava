package examples.analog;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.modules.PwmModule;
import fr.colin.seesawsdk.utils.Pins;

import java.io.IOException;

import static fr.colin.seesawsdk.utils.Pins.PWM2;
import static fr.colin.seesawsdk.utils.Pins.PWM3;

public class PwmWaveExample {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {
        Seesaw s = new Seesaw(I2CBus.BUS_1);
        s.init();
        PwmModule pwmModule = new PwmModule(s);

        byte i = 0;
        byte pas = 1;

        while (true) {
            pwmModule.writePwm(PWM2, i);
            i += pas;
            if (i == (byte) 255) {
                pas = -1;
            }
            if (i == (byte) 0) {
                pas = 1;
            }
            Thread.sleep(10);
        }


    }


}
