import entities.Creature;
import userInterface.Interface;
import world.Dungeon;
import world.DungeonRenderer;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Rogue {

    private String name;
    private Creature player;
    private boolean isRunning = false;
    private final int frameRate = 60;
    private int timePerLoop = 1000 / frameRate;
    private final Interface ui;
    private final Dungeon dungeon;
    private final DungeonRenderer renderer;

    public Rogue(String name) {
        this.name = name;
        this.ui = new Interface(this.name, 80, 24);
        this.dungeon = new Dungeon(80, 24);
        this.renderer = new DungeonRenderer(ui);
        int[] spawn = dungeon.findSpawnLocation();
        this.player = new Creature("Player", 'O', Color.WHITE, spawn[0], spawn[1]);
        ui.drawString("Welcome to Rogue!", 32, 12, Color.WHITE);
        ui.refresh();
        try {
            Thread.sleep(1500); // Show for 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }


    public void processInput() {
        InputEvent event = ui.getNextInput();
        if (event == null) return;

        if (event instanceof KeyEvent keyPress) {
            int newX = player.getX();
            int newY = player.getY();
            switch (keyPress.getKeyCode()) {
                case KeyEvent.VK_ESCAPE -> isRunning = false;
                case KeyEvent.VK_UP -> newY--;
                case KeyEvent.VK_DOWN -> newY++;
                case KeyEvent.VK_LEFT -> newX--;
                case KeyEvent.VK_RIGHT -> newX++;
            }


            // Prevent moving into walls
            if (dungeon.getMap()[newX][newY] == '.') {
                player.moveBy(newX - player.getX(), newY - player.getY());
                dungeon.markTraversed(newX, newY);
            }
        }
    }

    public void render() {
        renderer.render(dungeon, player);

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
