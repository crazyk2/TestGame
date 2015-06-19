package ru.rusakov.testgame;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    //Field
    public static int WIDTH = 400;
    public static int HEIGHT = 400;

    private Thread thread;

    private BufferedImage image;
    private Graphics2D g2d;

    public static GameBack background;
    public static Player player;
    public  static ArrayList<Bullet> bullets;
    public  static ArrayList<Enemy> enemies;

    //Constructor
    public GamePanel() {
        super();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();

        addKeyListener(new Listeners());

    }

    //Functions

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void run () {

        image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB );
        g2d = (Graphics2D)image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING ,RenderingHints.VALUE_ANTIALIAS_ON);
        background = new GameBack();
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();

        enemies.add(new Enemy(1,1));
        enemies.add(new Enemy(1,1));
        while (true){
           //TODO States
            gameUpdate();
            gameRender();
            gameDraw();

          try {

              thread.sleep(33); //TODO FPS

          } catch (InterruptedException e){
              e.printStackTrace();
          }

        }
    }

    public void gameUpdate(){
        //Background update
        background.update();

        //Player update
        player.update();

        //Bullets update
        for (int i=0; i < bullets.size(); i++) {
            bullets.get(i).update();
            boolean remove = bullets.get(i).remove();
            if (remove) {
                bullets.remove(i);
                i--;
            }
        }
        //Enemies update
        for (int i=0; i < enemies.size(); i++){
            enemies.get(i).update();
            //boolean remove =bullets.get(i).remove();
           //if (remove){
             //  bullets.remove(i);
               //i--;
           }

    }

    public void gameRender () {
        //Background draw
        background.draw(g2d);

        //Player draw
        player.draw(g2d);

        //Bullets draw
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g2d);
        }

        //Bullets draw
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g2d);
        }
    }

    private void gameDraw(){
        Graphics g= this.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

    }
}

