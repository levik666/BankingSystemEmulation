package light.service;

import light.model.Light;

public class LightOn implements Runnable {

    private Light light;

    public LightOn(final Light light) {
        this.light = light;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (light) {
                if (!light.isLight()) {
                    light.setOnLight();
                    light.notifyAll();
                } else {
                    try {
                        light.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
