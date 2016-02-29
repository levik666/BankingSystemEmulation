package light.service;

import light.model.Light;

import java.util.concurrent.Exchanger;

public class NewLightOff implements Runnable {

    private Light light;
    private Exchanger<Light> exchanger;

    public NewLightOff(final Exchanger<Light> exchanger, final Light light) {
        this.light = light;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while (true) {
            if (light.isLight()) {
                light.setOffLight();
                try {
                    exchanger.exchange(light);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
