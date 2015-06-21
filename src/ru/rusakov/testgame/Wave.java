package ru.rusakov.testgame;

import java.awt.*;

public class Wave {

    //Fields
    private int waveNumber;
    private int waveMultiplier;

    private String waveText;

    private long waveTimer;
    private  long waveDelay;
    private  long waveTimerDiff;


    //Constructor
    public Wave () {
        waveNumber = 1;
        waveMultiplier=5;

        waveTimer =0;
        waveDelay = 5000;
        waveTimerDiff = 0;
        waveText = "-  W A V E " + waveNumber + "  -";
    }
    //Functions
    public void createEnemies (){
        int enemyCount= waveNumber * waveMultiplier;
        if (waveNumber < 4) {
            int type = 1;
            int rank = 1;
            while (enemyCount > 0){
                GamePanel.enemies.add (new Enemy(type,rank));
                enemyCount -= type * rank;
            }

        }

    }

    public void update () {
        long systemTime = System.nanoTime();
        if (GamePanel.enemies.size() == 0 && waveTimer == 0){
            waveTimer = systemTime;
        }
        if (waveTimer > 0) {
            waveTimerDiff += (systemTime - waveTimer)/1000000;
            waveTimer = systemTime;
        }
        if (waveTimerDiff > waveDelay){
            createEnemies();
            waveTimer = 0;
            waveTimerDiff = 0;
            waveNumber++;

        }
    }

    public boolean showWave (){
        if (waveTimer != 0){
            return true;}

        else return false;
    }

    public void draw (Graphics2D g2d) {

        double divider = waveDelay/180;
        double alpha =  255 * Math.sin( Math.toRadians(waveTimerDiff/divider));
        if (alpha > 255 || alpha<0) alpha = 0;
        g2d.setFont(new Font("consolas", Font.PLAIN, 20));
        g2d.setColor(new Color(255, 255, 255, (int) alpha));
        int length = (int) g2d.getFontMetrics().getStringBounds(waveText,g2d).getWidth();

        waveText = "-  W A V E " + waveNumber + "  -";

        g2d.drawString(waveText, GamePanel.WIDTH/2 -  (length/2), GamePanel.HEIGHT/2);

    }
}
