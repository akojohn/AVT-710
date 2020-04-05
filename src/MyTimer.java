import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

class MyTimer {
    public static void main(String args[]) {
        Timer timer = new Timer();
    }

}

class GoldenTimer extends TimerTask {
    int P;

    GoldenTimer(int P) {
        this.P = P;
    }

    public void run() {
        double p = Math.random();
        if (p >= P)
            System.out.println("Golden fish was created");
    }
}

class GuppyTimer extends TimerTask {
    int P;

    GuppyTimer(int P) {
        this.P = P;
    }

    public void run() {
        double p = Math.random();
        if (p >= P)
            System.out.println("Guppy was created");
    }
}
