package world;

import entities.Enemy;
import entities.Skeleton;
import userInterface.Interface;
import entities.Creature;
import java.awt.*;
import java.util.ArrayList;

public class DungeonRenderer {
    private final Interface ui;

    public DungeonRenderer(Interface ui) {
        this.ui = ui;
    }

    public void render(Dungeon dungeon, Creature player) {
        ui.clear();
        char[][] map = dungeon.getMap();
        boolean[][] traversed = dungeon.getTraversedMap();


        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (traversed[x][y]) {
                    Color tileColor;

                    switch (map[x][y]) {
                        case '#':
                            tileColor = Color.ORANGE;
                            break; // Walls
                        case '.':
                            tileColor = Color.GRAY;
                            break;   // Floor
                        case '+':
                            tileColor = Color.RED;
                            break;    // Doors
                        default:
                            tileColor = Color.BLACK;
                            break;   // Hidden
                    }
                    ui.drawChar(map[x][y], x, y, tileColor);

                } else {
                    ui.drawChar(' ', x, y, Color.BLACK);
                }
            }
        }

        // Draw player
        ui.drawChar(player.getGlyph(), player.getX(), player.getY(), player.getColor());
        // Render Player Stats

        renderPlayerStats(player);
        ui.refresh();
    }
    private void renderPlayerStats(Creature player) {
        int statsX = 2; // X-coordinate to start displaying stats at the bottom
        int statsY = 22;  // Ensure this stays within screen bounds



        String stats = String.format(
                "Level:%d  Hits:%d(%d)  Str:%d(%d)  Gold:%d  Armor:%d  Exp:%d/%d",
                player.getLevel(), player.getHealth(), player.getMaxHealth(),
                player.getAttack(), player.getAttack(), player.getGold(),
                player.getDefense(), player.getExperience(), player.getExperienceToNextLevel()
        );

        ui.drawString(stats, statsX, statsY, Color.YELLOW);
    }

    public void renderEnemy(Enemy enemy) {
        ui.drawChar(enemy.getGlyph(), enemy.getX(), enemy.getY(), enemy.getColor());

    }
}

