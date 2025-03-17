package world;

import entities.Gold;
import entities.Item;
import userInterface.Interface;
import entities.Creature;
import entities.Monster;

import java.awt.*;
import java.util.List;

public class DungeonRenderer {
    private final Interface ui;
    private final int visibilityRadius = 5; // Monsters are visible within 3 tiles

    public DungeonRenderer(Interface ui) {
        this.ui = ui;
    }

    public void render(Dungeon dungeon, Creature player) {
        ui.clear();
        char[][] map = dungeon.getMap();
        boolean[][] traversed = dungeon.getTraversedMap();
        List<Monster> monsters = dungeon.getMonsters();


        int playerX = player.getX();
        int playerY = player.getY();

        //  Render dungeon tiles
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (traversed[x][y]) {
                    Color tileColor;

                    switch (map[x][y]) {
                        case '#':
                            tileColor = Color.ORANGE; // Walls
                            break;
                        case '.':
                            tileColor = Color.GRAY;   // Floor
                            break;
                        case '+':
                            tileColor = Color.RED;    // Doors
                            break;
                        case '$' :
                            tileColor = Color.YELLOW;
                            break;
                        case '*':
                            tileColor = Color.GREEN;
                            break;
                        default:
                            tileColor = Color.BLACK;  // Hidden
                            break;
                    }
                    ui.drawChar(map[x][y], x, y, tileColor);
                } else {
                    ui.drawChar(' ', x, y, Color.BLACK);
                }
            }
        }

        //  Update visibility for monsters
        for (Monster monster : monsters) {
            if (monster.isAlive() && isWithinRadius(playerX, playerY, monster.getX(), monster.getY())) {
                monster.discover(); //  Mark monster as discovered when within radius
            }
        }



        //  Render all discovered monsters
        for (Monster monster : monsters) {
            if (monster.isAlive() && monster.isDiscovered()) {
                ui.drawChar(monster.getLetter(), monster.getX(), monster.getY(), monster.getColor());
            }
        }

        //  Render player on top of everything
        ui.drawChar(player.getGlyph(), player.getX(), player.getY(), player.getColor());

        //  Render player stats
        renderPlayerStats(player);

        List<String> messageLog = dungeon.getMessageLog();
        if (!messageLog.isEmpty()) {
            int y = 20; // Bottom-most line for messages

            for (int i = messageLog.size() - 1; i >= 0; i--) {
                ui.drawString(messageLog.get(i), 58, y--, Color.WHITE);
            }
        }

        ui.refresh();
    }

    //  Check if monster is within visibility radius
    private boolean isWithinRadius(int playerX, int playerY, int monsterX, int monsterY) {
        int dx = Math.abs(playerX - monsterX);
        int dy = Math.abs(playerY - monsterY);
        return dx <= visibilityRadius && dy <= visibilityRadius;
    }

    private void renderPlayerStats(Creature player) {
        int statsX = 2; // X-coordinate to start displaying stats at the bottom
        int statsY = 22; // Ensure this stays within screen bounds

        String stats = String.format(
                "Level:%d  Hits:%d(%d)  Str:%d(%d)  Gold:%d  Armor:%d  Exp:%d/%d",
                player.getLevel(), player.getHealth(), player.getMaxHealth(),
                player.getAttack(), player.getAttack(), player.getGold(),
                player.getDefense(), player.getExperience(), player.getExperienceToNextLevel()
        );

        ui.drawString(stats, statsX, statsY, Color.YELLOW);
    }

    public void renderInventory(Creature player) {

        List<Item> inventory = player.getInventory();
        int startX = 65;
        int startY = 2;
        ui.drawString("Inventory:", startX, startY, Color.YELLOW);

        startY++;

        if (inventory.isEmpty()) {
            ui.drawString(" - Empty -", startX, startY, Color.GRAY);

        } else {
            for (Item item : inventory) {
                ui.drawString("- " + item.getName(), startX, startY++, Color.WHITE);

            }
        }
        ui.refresh();
    }


    public void drawString(String message, int x, int y, Color color) {
        ui.drawString(message, x, y, color);

    }
}
