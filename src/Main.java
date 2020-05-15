//Вариант 8
//        Список транспортных средств на дороге состоит из двух категорий:
//        автомобили и мотоциклы. Автомобили генерируются каждые N1 секунд с вероятностью P1.
//        Мотоциклы генерируются каждые N2 секунд с вероятностью P
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

public final class Main {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Habitat theUniverse = new Habitat();
        theUniverse.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if  (key == KeyEvent.VK_B ) {
                    if (!theUniverse.isStartFlag && theUniverse.isEndFlag) {
                        theUniverse.isStartFlag = true;
                        theUniverse.simulationTimer.setVisible(true);
                        theUniverse.theUniverseStartTime = new Date().getTime();
                        theUniverse.helpTimer.start();
                        theUniverse.timerForAutomobile.start();
                        theUniverse.timerForMotorcycle.start();
                        theUniverse.isEndFlag = false;
                        theUniverse.userPanel.startSimulating.setEnabled(false);
                        theUniverse.userPanel.stopSimulating.setEnabled(true);
                        theUniverse.panelMenuBar.startSimulating.setEnabled(false);
                        theUniverse.panelMenuBar.stopSimulating.setEnabled(true);
                    }
                }
                if (key == KeyEvent.VK_E )  if (!theUniverse.isEndFlag && theUniverse.isStartFlag) {
                    theUniverse.isEndFlag = true;
                    theUniverse.theUniverseTime = new Date().getTime() - theUniverse.theUniverseStartTime + theUniverse.theUniverseTime;
                    theUniverse.timerForAutomobile.stop();
                    theUniverse.timerForMotorcycle.stop();
                    theUniverse.helpTimer.stop();
                    theUniverse.isStartFlag = false;
                    if (theUniverse.isShowResultTableFlag) theUniverse.statistics();
                    theUniverse.theUniverseStartTime = new Date().getTime();
                    theUniverse.userPanel.startSimulating.setEnabled(true);
                    theUniverse.userPanel.stopSimulating.setEnabled(false);
                    theUniverse.panelMenuBar.stopSimulating.setEnabled(false);
                    theUniverse.panelMenuBar.startSimulating.setEnabled(true);
                }
                if (key == KeyEvent.VK_T && theUniverse.isStartFlag && !theUniverse.isEndFlag) {
                    theUniverse.show = !theUniverse.show;
                    theUniverse.simulationTimer.setVisible(theUniverse.show);
                    if (theUniverse.show == true)
                    theUniverse.userPanel.visibleTimeSimulating.setSelected(theUniverse.userPanel.showTimeSimulating.getModel(), true);
               else theUniverse.userPanel.visibleTimeSimulating.setSelected(theUniverse.userPanel.hideTimeSimulating.getModel(), true);
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
