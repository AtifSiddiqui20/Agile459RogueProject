package world;

import userInterface.Interface;
import entities.Creature;
import java.awt.*;

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
        int statsX = 65;
        int statsY = 2;

        ui.drawString("Player Stats", statsX, statsY, Color.WHITE);
        ui.drawString("Health: " + player.getHealth() + "/" + player.getMaxHealth(), statsX, statsY + 2, Color.GREEN);
        ui.drawString("Attack: " + player.getAttack(), statsX, statsY + 3, Color.RED);
        ui.drawString("Defense: " + player.getDefense(), statsX, statsY + 4, Color.YELLOW);
    }
}

