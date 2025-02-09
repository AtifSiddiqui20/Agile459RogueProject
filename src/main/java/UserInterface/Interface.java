package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Interface extends JFrame implements KeyListener, MouseListener {
    public Interface(String title, int width, int height) {
        super(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public InputEvent getNextInput() {
        return null;
    }

    public void clear() {
    }

    public void drawChar(char glyph, int x, int y, Color color) {
    }

    public void refresh() {
    }
}
