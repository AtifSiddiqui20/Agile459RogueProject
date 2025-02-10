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

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                Color tileColor;

                switch (map[x][y]) {
                    case '#': tileColor = Color.ORANGE; break; // Walls
                    case '.': tileColor = Color.GRAY; break;   // Floor
                    case '+': tileColor = Color.RED; break;    // Doors
                    default: tileColor = Color.BLACK; break;   // Hidden
                }

                ui.drawChar(map[x][y], x, y, tileColor);
            }
        }

        // Draw player
        ui.drawChar(player.getGlyph(), player.getX(), player.getY(), player.getColor());

        ui.refresh();
    }
}
