package examples;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import fr.colin.seesawsdk.Modes;
import fr.colin.seesawsdk.Seesaw;
import fr.colin.seesawsdk.events.PinDigitalStateChangeEvent;
import fr.colin.seesawsdk.events.listener.PinListenerDigital;
import fr.colin.seesawsdk.modules.GPIOModule;
import fr.colin.seesawsdk.utils.PinUtils;

import static fr.colin.seesawsdk.utils.Pins.*;

import java.io.IOException;

public class CurrentTest {

    public static void main(String... args) throws I2CFactory.UnsupportedBusNumberException, IOException, PlatformAlreadyAssignedException, InterruptedException {
        Seesaw s = new Seesaw(I2CBus.BUS_1, Platform.RASPBERRYPI);
        s.init();
        GPIOModule g = s.getGpioController();

        g.setMode(Modes.INPUT_PULLUP, GPIO_15);
        g.setMode(Modes.OUTPUT, GPIO_10);
/*        g.setMode(Modes.OUTPUT, GPIO_9);
        while (true) {
            boolean state = g.readGpio(GPIO_15);
            if (!state) {
                g.setHigh(GPIO_10);
                g.setLow(GPIO_9);
            } else {
                g.setHigh(GPIO_9);
                g.setLow(GPIO_10);
            }
        }*/
        g.registerListener(GPIO_15, new PinListenerDigital() {
            @Override
            public void handle(PinDigitalStateChangeEvent event) {
                System.out.println("Yay pin " + event.getPin() + " changed to " + event.getState());
                g.setHigh(10);
            }
        });
        g.registerListener(GPIO_10, new PinListenerDigital() {
            @Override
            public void handle(PinDigitalStateChangeEvent event) {
                System.out.println("LED ON/OFF");
                g.setLow(10);
            }
        });
    }


}
