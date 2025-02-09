import Entities.Creature;
import UserInterface.Interface;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Rogue {

    private String name;
    private Creature player;


    private boolean isRunning;
    private final int frameRate = 60;
    private int timePerLoop = 100000 / frameRate;

    private final Interface ui;

    public Rogue(String name) {
        this.name = name;
        this.player = new Creature("Player", 'O', Color.WHITE, 10, 10);
        this.ui = new Interface(this.name, 80, 24);
    }


    public void processInput() {
        InputEvent event = ui.getNextInput();
        if (event instanceof KeyEvent) {
            KeyEvent keyPress = (KeyEvent) event;
            switch (keyPress.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    isRunning = false;
                    break;
                case KeyEvent.VK_UP:
                    player.moveBy(0, -1);
                    break;
                case KeyEvent.VK_DOWN:
                    player.moveBy(0, 1);
                    break;
                case KeyEvent.VK_LEFT:
                    player.moveBy(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.moveBy(1, 0);
                    break;
            }
        }
    }

    public void render() {
        ui.clear();
        ui.drawChar(player.getGlyph(), player.getX(), player.getY(), player.getColor());
        ui.refresh();
    }

    public void run() {
        isRunning = true;
        while(isRunning) {
            long startTime = System.currentTimeMillis();
            processInput();
            render();
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            long sleepTime = timePerLoop - elapsedTime;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public int getTimePerLoop() {
        return timePerLoop;
    }

    public void setTimePerLoop(int timePerLoop) {
        this.timePerLoop = timePerLoop;
    }

    public static void main(String[] args) {
        Rogue game = new Rogue("Rogue");
        game.run();
    }
}
