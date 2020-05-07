package examples.analog;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.charlotte.seesawsdk.Seesaw;
import fr.charlotte.seesawsdk.modules.AnalogModule;
import fr.charlotte.seesawsdk.modules.PwmModule;

import java.io.IOException;

import static fr.charlotte.seesawsdk.utils.Pins.*;


public class AnalogReadPwmOutputExample {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {
        Seesaw s = new Seesaw(I2CBus.BUS_1);
        s.init();
        PwmModule pwmModule = new PwmModule(s);
        AnalogModule analog = new AnalogModule(s);

        pwmModule.writePwm(PWM2, (byte) 0);
        while (true) {
            int i = analog.readChannel(ADC0);
            int output = Math.round(i * 0.2490234375f);
            if(output < 1){
                output = 0;
            }
            if(output > 255){
                output = 255;
            }
            pwmModule.writePwm(PWM2, (byte) (output & (0xff)));
            Thread.sleep(1);
        }


    }


}
