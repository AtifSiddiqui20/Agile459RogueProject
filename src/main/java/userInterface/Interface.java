package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

import asciiPanel.AsciiPanel;


public class Interface extends JFrame implements KeyListener, MouseListener {

    private static final long serialVersionUID = 1L;

    private AsciiPanel terminal;
    private Queue<InputEvent> inputQueue;

    private int width;
    private int height;

    public Interface(String title, int width, int height) {
        super(title);
        this.height = height;
        this.width = width;
        terminal = new AsciiPanel(width, height);
        inputQueue = new LinkedList<>();
        super.add(terminal);
        super.pack();
        terminal.write("Welcome to Rogue!", 1, 1);
        super.setVisible(true);
        super.addKeyListener(this);
        super.addMouseListener(this);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        inputQueue.add(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        inputQueue.add(e);

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
        return inputQueue.poll();
    }

    public void clear() {
        terminal.clear();
    }




    public void drawChar(char glyph, int x, int y, Color color) {

        terminal.write(glyph, x, y, color);
    }

    public void refresh() {
        terminal.repaint();
    }

    public void drawString(String s, int x, int y, Color white) {
        terminal.write(s, x, y, white);
    }
}
