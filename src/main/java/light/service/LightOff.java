package light.service;

import light.model.Light;

public class LightOff implements Runnable {

    private Light light;

    public LightOff(final Light light) {
        this.light = light;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (light) {
                if (light.isLight()) {
                    light.setOffLight();
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
