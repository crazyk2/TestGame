package ru.rusakov.testgame;

import java.awt.*;

public class Bullet {
    //fields

    private double x, y;
    private int r;

    private int speed;

    private Color color1;


    //Constructor

    public Bullet (){
        x = GamePanel.player.getX();
        y = GamePanel.player.getY();
        r = 2;

        speed = 10;



        color1 = Color.WHITE;
    }
    //Fuctions

    public boolean remove (){
        if (y < 0)  return true;

        return false;
    }
    public void update (){
        y -= speed;
    }

    public void draw (Graphics2D g2d){
        g2d.setColor(color1);
        g2d.fillOval((int) x, (int) y,r, 2 * r);

    }
}
