package ru.rusakov.testgame;

import sun.management.GarbageCollectionNotifInfoCompositeData;

import java.awt.*;
import java.util.Map;

public class Player {

    //Fields
    private double x, y;
    private int r;

    private double dx; //Move Coeff
    private double dy;


    private int speed;
    private Color color1;
    private Color color2;

    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean isFiring;

    //Constructor
    public Player (){
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
        r = 5;

        speed = 5;

        dx=0;
        dy=0;

        color1 = Color.WHITE;

        up = false;
        down = false;
        left = false;
        right = false;

        isFiring = false;
    }
    //Functions
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void update(){
        if (up && y > r) dy -= speed;

        if (down && y < GamePanel.HEIGHT - r) dy += speed;

        if (left && x > r)  dx -= speed;

        if (right && x < GamePanel.WIDTH - r) dx += speed;

        if (up && left || up && right || down && left || down && right ) {
           double angle = Math.toRadians(45);
            dy = dy * Math.sin(angle);
            dx = dx * Math.cos(angle);
        }

        y += dy;
        x += dx;
        dy=0;
        dx=0;

        if (isFiring) {
            GamePanel.bullets.add(new Bullet());
        }

    }

    public void draw(Graphics2D g2d){
        g2d.setColor(color1);
        g2d.fillOval((int) (x - r), (int) (y - r ), 2 * r, 2 * r);
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(color1.darker());
        g2d.drawOval((int) (x - r), (int) (y - r ), 2 * r, 2 * r);
        g2d.setStroke(new BasicStroke(1));

    }
}
