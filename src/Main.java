//Вариант 8
//        Список транспортных средств на дороге состоит из двух категорий:
//        автомобили и мотоциклы. Автомобили генерируются каждые N1 секунд с вероятностью P1.
//        Мотоциклы генерируются каждые N2 секунд с вероятностью P
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

public final class Main {

    public static void main(String[] args) throws Exception {
        Habitat theUniverse = new Habitat();
        theUniverse.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if  (key == KeyEvent.VK_B && !theUniverse.isStartFlag && theUniverse.isEndFlag) {
                    theUniverse.theUniverseStartTime = new Date().getTime();
                    theUniverse.timerForAutomobile.start();
                    theUniverse.timerForMotorcycle.start();
                }
                if (key == KeyEvent.VK_E && !theUniverse.isEndFlag && theUniverse.isStartFlag) {
                    theUniverse.theUniverseTime = new Date().getTime() - theUniverse.theUniverseStartTime;
                    theUniverse.timerForAutomobile.stop();
                    theUniverse.timerForMotorcycle.stop();
                    theUniverse.frameClearing();
                    theUniverse.Statistics();
                    theUniverse.dateClearing();
                }
                if (key == KeyEvent.VK_T && theUniverse.isStartFlag && !theUniverse.isEndFlag) {
                    theUniverse.simulationTimer.setVisible(theUniverse.isShow());
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if  (key == KeyEvent.VK_B){
                    theUniverse.isStartFlag = true;
                }
                if (key == KeyEvent.VK_E) {
                    theUniverse.isEndFlag = true;
                }
            }
        });
    }
}
