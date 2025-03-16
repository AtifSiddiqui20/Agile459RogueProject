package entities;

import org.junit.jupiter.api.Test;
import world.Dungeon;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class CreatureTest {

    @Test
    void testConstructor() {
        // Create a creature with specific attributes
        Creature creature = new Creature("Player", '@', Color.WHITE, 5, 5);

        // Verify initial attributes
        assertEquals("Player", creature.getName());
        assertEquals('@', creature.getGlyph());
        assertEquals(Color.WHITE, creature.getColor());
        assertEquals(5, creature.getX());
        assertEquals(5, creature.getY());
    }

    @Test
    void testMoveBy() {
        // Create a creature at position (5, 5)
        Creature creature = new Creature("Player", '@', Color.WHITE, 5, 5);
        Dungeon dungeon = new Dungeon(80, 24);

        // Move the creature by (+2, -3)
        creature.moveBy(2, -3, dungeon);

        // Verify new position
        assertEquals(7, creature.getX());
        assertEquals(2, creature.getY());
    }

    @Test
    void testMoveByNegative() {
        // Create a creature at position (10, 10)
        Creature creature = new Creature("Enemy", 'E', Color.RED, 10, 10);
        Dungeon dungeon = new Dungeon(80, 24);

        // Move the creature by (-3, -4)
        creature.moveBy(-3, -4, dungeon);

        // Verify new position
        assertEquals(7, creature.getX());
        assertEquals(6, creature.getY());
    }

    @Test
    void testDefaultHealth() {
        Creature creature = new Creature("Player", '@', Color.WHITE, 5, 5);
        assertEquals(5, creature.getX());
    }

    @Test
    void testDefaultExperience() {
        Creature creature = new Creature("Player", '@', Color.WHITE, 5, 5);
        assertEquals(5, creature.getY());
    }

    @Test
    void testDefaultGold() {
        Creature creature = new Creature("Player", '@', Color.WHITE, 5, 5);
        assertEquals('@', creature.getGlyph());
    }
}
