package entities;

import org.junit.jupiter.api.Test;

import java.awt.*;

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

        // Move the creature by (+2, -3)
        creature.moveBy(2, -3);

        // Verify new position
        assertEquals(7, creature.getX());
        assertEquals(2, creature.getY());
    }

    @Test
    void testMoveByNegative() {
        // Create a creature at position (10, 10)
        Creature creature = new Creature("Enemy", 'E', Color.RED, 10, 10);

        // Move the creature by (-3, -4)
        creature.moveBy(-3, -4);

        // Verify new position
        assertEquals(7, creature.getX());
        assertEquals(6, creature.getY());
    }
}
