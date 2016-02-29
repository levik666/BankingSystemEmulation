package light.model;

public class Light {

    private volatile boolean light;

    public Light(boolean light) {
        this.light = light;
    }

    public void setOnLight(){
        System.out.println(Thread.currentThread().getName() + " -->> setOnLight !!!");
        if (light) {
            throw new RuntimeException("Bla !!!");
        }
        light = true;
        System.out.println(Thread.currentThread().getName() + " " + this);
    }

    public void setOffLight(){
        System.out.println(Thread.currentThread().getName() + " -->> setOffLight !!!");
        if (!light) {
            throw new RuntimeException("Bla !!!");
        }
        light = false;
        System.out.println(Thread.currentThread().getName() + " " + this);
    }

    public boolean isLight() {
        return light;
    }

    @Override
    public String toString() {
        return "Light{" +
                "light=" + light +
                '}';
    }
}
