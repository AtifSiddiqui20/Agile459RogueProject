import entities.Creature;
import entities.Monster;
import userInterface.Interface;
import world.Dungeon;
import world.DungeonRenderer;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

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

        // Spawn player
        int[] spawn = dungeon.findSpawnLocation();
        this.player = new Creature("Player", 'O', Color.WHITE, spawn[0], spawn[1]);

        //  Spawn monsters
        dungeon.spawnRandomMonsters(10);

        ui.drawString("Welcome to Rogue!", 32, 12, Color.WHITE);
        ui.refresh();
        try {
            Thread.sleep(1500); // Show for 1.5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



    //  Process player input
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

            //  Prevent moving into walls
            if (dungeon.getMap()[newX][newY] == '.') {
                player.moveBy(newX - player.getX(), newY - player.getY());
                dungeon.markTraversed(newX, newY);

                //  Check if the player attacks a monster
                Monster monster = getMonsterAt(newX, newY);
                if (monster != null) {
                    attackMonster(monster);
                }
            }
        }
    }

    //  Process monster turns
    private void processMonsters() {
        List<Monster> monsters = dungeon.getMonsters();
        for (Monster monster : monsters) {
            if (monster.isAlive() && isAdjacent(monster, player)) {
                int damage = monster.attack();
                player.takeDamage(damage);
                if (!player.isAlive()) {
                    gameOver();
                    return;
                }
            }
        }
        dungeon.removeDeadMonsters(); //  Remove dead monsters after each turn
    }

    //  Attack a monster
    private void attackMonster(Monster monster) {
        int damage = player.getAttack();  // Player's attack damage
        monster.takeDamage(damage);
    }

    //  Check if two creatures are adjacent
    private boolean isAdjacent(Creature c1, Creature c2) {
        int dx = Math.abs(c1.getX() - c2.getX());
        int dy = Math.abs(c1.getY() - c2.getY());
        return dx <= 1 && dy <= 1;
    }

    //  Get monster at a specific tile
    private Monster getMonsterAt(int x, int y) {
        for (Monster monster : dungeon.getMonsters()) {
            if (monster.getX() == x && monster.getY() == y && monster.isAlive()) {
                return monster;
            }
        }
        return null;
    }

    //  Render everything
    public void render() {
        dungeon.removeDeadMonsters(); //  Remove dead monsters
        renderer.render(dungeon, player);
    }

    //  Main game loop
    public void run() {
        isRunning = true;
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            processInput();
            processMonsters();
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

    //  Game over
    private void gameOver() {
        isRunning = false;
        ui.drawString("Game Over!", 32, 12, Color.RED);
        ui.refresh();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



    //  Handle Player vs Monster Combat
    private void engageCombat(Monster monster) {
        boolean playerTurn = !monster.isMean(); // Player attacks first unless monster is Mean

        while (player.isAlive() && monster.isAlive()) {
            if (playerTurn) {
                //  Player attacks
                if (player.attackRoll(monster.getArmor())) {
                    int damage = player.dealDamage();
                    monster.takeDamage(damage);
                }
            } else {
                //  Monster attacks
                if (monster.attackRoll(player.getArmor())) {
                    int damage = monster.dealDamage();
                    player.takeDamage(damage);
                }
            }

            playerTurn = !playerTurn; //  Switch turns
        }

        if (!monster.isAlive()) {
            player.gainExperience(monster.getExp());
        }

        if (!player.isAlive()) {
            gameOver();
        }
    }




    //  Main method
    public static void main(String[] args) {
        Rogue game = new Rogue("Rogue");
        game.run();
    }
}
