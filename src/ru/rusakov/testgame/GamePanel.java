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

    private int FPS;
    private int sleepTime;
    private double millisToFPS;
    private long timerFPS;

    public static GameBack background;
    public static Player player;
    public  static ArrayList<Bullet> bullets;
    public  static ArrayList<Enemy> enemies;
    public  static Wave wave;

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
        FPS = 30;
        millisToFPS = 1000/FPS;
        sleepTime = 0;

        image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB );
        g2d = (Graphics2D)image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING ,RenderingHints.VALUE_ANTIALIAS_ON);
        background = new GameBack();
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        wave = new Wave();


        while (true){
           //TODO States
            timerFPS = System.nanoTime();
            gameUpdate();
            gameRender();
            gameDraw();
            timerFPS = (System.nanoTime() - timerFPS)/1000000;
            if (millisToFPS > timerFPS) {
                sleepTime =  (int) (  millisToFPS - timerFPS  );
            }
            else sleepTime = 1;

            try {

              thread.sleep(sleepTime); //TODO FPS
                System.out.println(sleepTime);

            } catch (InterruptedException e){
              e.printStackTrace();
            }
            timerFPS = 0;
            sleepTime = 1;
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

        //Bullets-enemies-player collide
        double px = player.getX();
        double py = player.getY();
        double pr = player.getR();

        for (int i=0; i < enemies.size(); i++){
            Enemy e= enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            int er = e.getR();

           //Player-enemy collide
            double dx_ep = ex - px;
            double dy_ep = ey - py;
            double dist_ep = Math.sqrt(dx_ep * dx_ep + dy_ep * dy_ep);
            if ( (int) dist_ep <= er + pr) {
                //e.hit();
                enemies.remove(i);
                i--;
                player.hit();
                break;
            }

            //Bullets-enemies collide
            for (int j =0; j< bullets.size(); j ++){
                Bullet b = bullets.get(j);
                double bx = b.getX();
                double by = b.getY();

                double dx_eb = ex - bx;
                double dy_eb = ey - by;
                double dist_eb = Math.sqrt(dx_eb * dx_eb + dy_eb * dy_eb);
                if ( (int) dist_eb <= er + b.getR()) {
                    e.hit();
                    bullets.remove(j);
                    j--;
                    if (e.remove()){
                        enemies.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }

        //Wave udate
        wave.update();

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

        //Wave draw
        if (wave.showWave()) {
            wave.draw(g2d);
        }
    }

    private void gameDraw(){
        Graphics g= this.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

    }
}

