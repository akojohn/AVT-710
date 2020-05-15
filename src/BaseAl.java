//1.	Автомобили двигаются по оси X от одного края области симуляции до другого со скоростью V.
//2.	Мотоциклы двигаются по оси Y от одного края области симуляции до другого со скоростью V.
public abstract class BaseAl extends Thread {
    private boolean going = false;

    @Override
    public synchronized void run() {
        try {
            while (!isInterrupted()) {
                if (going) {
                    wait();
                } else {
                    Thread.sleep(30);
                    step();
                }
            }
        } catch (InterruptedException e) {
        }
    }

    public abstract void step();

    public void pause() {
        going = true;
    }

    public synchronized void play() {
        going = false;
        notify();
        }
}
