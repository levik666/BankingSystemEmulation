package light.service;

import light.model.Light;

import java.util.concurrent.Exchanger;

public class NewLightOn implements Runnable {

    private Light light;
    private Exchanger<Light> exchanger;

    public NewLightOn(final Exchanger<Light> exchanger, final Light light) {
        this.light = light;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while (true) {
            if (!light.isLight()) {
                light.setOnLight();
                try {
                    exchanger.exchange(light);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
