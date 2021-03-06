package com.junjunguo.gameintroduction;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

import sheep.collision.CollisionListener;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

/**
 * Created by GuoJunjun on 22.01.15.
 */
public class TitleScreen extends State {

    private Image heliR = new Image(R.drawable.helicopterright);
    private Image heliL = new Image(R.drawable.helicopterleft);
    private Image wallVerticalImg = new Image(R.drawable.wall_vertical);
    private Image bgImage = new Image(R.drawable.bg800x1200);
    private Sprite asprite, westWall, backSprite, bsprite, csprite, dsprite, esprite, fsprite, gsprite;
    private Paint postext;
    private String string = "";
    private int x, y;
    //    private RandomSpeed brandomSpeed;

    public TitleScreen() {
        backSprite = new Sprite(bgImage);
        bsprite = new Sprite(heliR);
        asprite = new Sprite(heliL);
        westWall = new Sprite(wallVerticalImg);
        westWall.setPosition(4, 215);
        asprite.setPosition(350, 550);
        bsprite.setPosition(100, 100);
        bsprite.setSpeed(50, 50);
        // Animation right to left:
        addAnimationSprites();
        postext = new Paint();
        postext.setStyle(Paint.Style.FILL);
        postext.setTextSize(20);
        postext.setColor(Color.RED);

        //        brandomSpeed = new RandomSpeed();

        addTouchListener(new TouchListener() {
            @Override public boolean onTouchDown(MotionEvent event) {
                x = (int) event.getX();
                y = (int) event.getY();
                return false;
            }

            @Override public boolean onTouchUp(MotionEvent event) {
                return false;
            }

            @Override public boolean onTouchMove(MotionEvent event) {
                return false;
            }
        });

        startAnimateSprite(0, csprite);
        startAnimateSprite(100, dsprite);
        startAnimateSprite(200, esprite);

        System.out.println("stat");
        //        asprite.addCollisionListener(new CollisionListener() {
        //            @Override public void collided(Sprite sprite, Sprite sprite2) {
        //                //                collisionHandle(asprite);
        //                System.out.println("a sp collisions");
        //                asprite.setSpeed((float) -(Math.random() * 100), (float) -(Math.random() * 100));
        //            }
        //        });
        bsprite.addCollisionListener(new CollisionListener() {
            @Override public void collided(Sprite sprite, Sprite sprite2) {
                //                collisionHandle(bsprite);
                System.out.println("b sp collisions");

                //                bsprite.setSpeed((float) -(bsprite.getSpeed().getX() * (Math.random() * 2)),
                //                        (float) -(bsprite.getSpeed().getY() * (Math.random() * 2)));
            }
        });
        fsprite.addCollisionListener(new CollisionListener() {
            @Override public void collided(Sprite sprite, Sprite sprite2) {
                //                collisionHandle(fsprite);
                System.out.println("f sp collisions");

                //                fsprite.setSpeed((float) -(fsprite.getSpeed().getX() * (Math.random() * 2)),
                //                        (float) -(fsprite.getSpeed().getY() * (Math.random() * 2)));
            }
        });
    }

    /**
     * use Canvas to draw
     *
     * @param canvas
     */
    public void draw(Canvas canvas) {
        backSprite.draw(canvas);
        westWall.draw(canvas);
        asprite.draw(canvas);
        bsprite.draw(canvas);

        csprite.draw(canvas);
        dsprite.draw(canvas);
        esprite.draw(canvas);
        fsprite.draw(canvas);
        //        gsprite.draw(canvas);
        //        imageView.draw(canvas);

        canvas.drawText(string, 20, 20, postext);
    }

    /**
     * a loop method
     *
     * @param dt
     */
    public void update(float dt) {

        moveto();
        //Task 2: show position:
        string = String.format("%2$10.1f %1$10.1f", asprite.getX(), asprite.getY());
        //        brandomSpeed.setThesprite(bsprite);
        //        bsprite = brandomSpeed.getSprite();
        collisionHandle();
        randomMove(bsprite);
        randomMove(fsprite);
        direction(asprite);
        direction(bsprite);
        westWall.update(dt);
        asprite.update(dt);
        bsprite.update(dt);

        csprite.update(dt);
        dsprite.update(dt);
        esprite.update(dt);
        fsprite.update(dt);
        //        gsprite.update(dt);
    }

    /**
     * TASK 1: direction: move right or left
     *
     * @param sprite
     */
    private void direction(Sprite sprite) {
        if (sprite.getSpeed().getX() > 0) {
            sprite.setView(heliR);
        } else {
            sprite.setView(heliL);
        }
    }


    /**
     * TASK 2: control the movement
     */
    private void moveto() {
        int ax = (int) asprite.getX();
        int ay = (int) asprite.getY();
        float sx, sy;
        //        System.out.println("ax: " + ax + " x: " + x + " ay: " + ay);
        if (ax - x > 0) {
            sx = -80;
        } else if (ax - x < 0) {
            sx = 80;
        } else {
            sx = 0;
        }
        if (ay - y > 0) {
            sy = -90;
        } else if (ay - y < 0) {
            sy = 90;
        } else {
            sy = 0;
        }

        asprite.setSpeed(sx, sy);
    }

    /**
     * TASK 3 animation
     * <p/>
     * * * init animation sprite
     */
    private void addAnimationSprites() {
        csprite = new Sprite();
        dsprite = new Sprite();
        esprite = new Sprite();
        fsprite = new Sprite(heliL);
        //        gsprite = new Sprite(heliL);

        csprite.setPosition(28, 128);
        dsprite.setPosition(254, 128);
        esprite.setPosition(490, 128);
        fsprite.setPosition(512, 428);
        fsprite.setSpeed(((float) Math.random() * 10), ((float) Math.random() * 10));
        //        gsprite.setPosition(640, 528);
    }

    /**
     * TASK 3 start animation
     *
     * @param delayMillisecond
     * @param sprite
     */
    private void startAnimateSprite(int delayMillisecond, final Sprite sprite) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sprite.setView(heliL);
                try {
                    Thread.sleep(100);
                    sprite.setView(null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, delayMillisecond, 300);
    }

    private void collisionHandle() {
        //                sprite.setSpeed((float) -(sprite.getSpeed().getX() * Math.random() * 6),
        //                (float) -(sprite.getSpeed().getY() * Math.random() * 6));
        //        return sprite;

        float asx, asy, bsx, bsy, fsx, fsy;
        asx = asprite.getSpeed().getX();
        asy = asprite.getSpeed().getY();
        bsx = bsprite.getSpeed().getX();
        bsy = bsprite.getSpeed().getY();
        fsx = fsprite.getSpeed().getX();
        fsy = fsprite.getSpeed().getY();
        if (asprite.collides(bsprite)) {

            bsprite.setSpeed(asx * ((float) Math.random() * 2 + 1), asy * ((float) Math.random() * 3 + 1));
            asprite.setSpeed(bsx * ((float) Math.random() * 20 + 1), bsy * ((float) Math.random() * 20 + 1));
        }
        if (asprite.collides(fsprite)) {
            fsprite.setSpeed(asx * ((float) Math.random() * 2 + 1), asy * ((float) Math.random() * 2 + 1));
            asprite.setSpeed(fsx * ((float) Math.random() * 20 + 1), fsy * ((float) Math.random() * 20 + 1));
        }
        if (bsprite.collides(fsprite)) {
            bsprite.setSpeed(fsx * ((float) Math.random() * 2 + 1), fsy * ((float) Math.random() * 2 + 1));
            fsprite.setSpeed(bsx * ((float) Math.random() * 2 + 1), bsy * ((float) Math.random() * 3 + 1));
        }
    }


    /**
     * set in a new speed value, when meet the border
     *
     * @param thesprite
     */
    private void randomMove(Sprite thesprite) {
        float sx = thesprite.getSpeed().getX(), sy = thesprite.getSpeed().getY();
        float borderX = 600;
        float borderY = 1000;
        float ax = thesprite.getX();
        float ay = thesprite.getY();

        if (ax >= borderX) {
            sx = (float) -(Math.random() * 100 + 50);
        } else if (ax <= 50) {
            sx = (float) (Math.random() * 100 + 50);
        }
        if (ay >= borderY) {
            sy = (float) -(Math.random() * 100 + 100);
        } else if (ay <= 30) {
            sy = (float) (Math.random() * 100 + 100);
        }
        if (sx != 0 || sy != 0) {
            thesprite.setSpeed(sx, sy);
        }
    }

}
