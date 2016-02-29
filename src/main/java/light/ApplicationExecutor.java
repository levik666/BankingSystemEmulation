package light;

import light.model.Light;
import light.service.NewLightOff;
import light.service.NewLightOn;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationExecutor {

    private static final boolean LIGHT_ON = true;

    public static void main(String[] args) {
        final Light light = new Light(LIGHT_ON);
        final Exchanger<Light> exchanger = new Exchanger<>();
        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        //executorService.submit(new LightOn(light));
        //executorService.submit(new LightOff(light));

        executorService.submit(new NewLightOn(exchanger, light));
        executorService.submit(new NewLightOff(exchanger, light));
    }
}
