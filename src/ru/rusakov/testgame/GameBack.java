package ru.rusakov.testgame;
import java.awt.*;

public class GameBack {
    //Fields
    private Color color;

    //Constructor
    public GameBack() {
        color = Color.BLUE;
    }

    //Functions
    public void update() {

    }
    public void draw(Graphics2D g2d) {
            g2d.setColor(color);
            g2d.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }
}
