package utilities;

import entities.Hero;

/*
 * @author : Alexandros KOSSYFIDIS - Nordine LAOUEDJ
 * @mailto : alexkossyfidis1@gmail.com - nordine.laouedj@ensea.fr
 * @date : 23/04/2022
 */

public class Camera {

    /**
     * Camera's height property.
     */
    private final double HEIGHT;

    /**
     * Camera's width property.
     */
    private final double WIDTH;

    /**
     * Camera's attached Hero object.
     */
    private final Hero hero;

    /**
     * X-axis coordinate.
     */
    private double worldX;

    /**
     * Y-axis coordinate.
     */
    private double worldY;

    /**
     * Creates a camera with specified dimensions attached to a Hero.
     * @param WIDTH  WIDTH of the camera
     * @param HEIGHT HEIGHT of the camera
     * @param hero   Defines the entity on which this camera is attached to.
     */

    public Camera(int WIDTH, int HEIGHT, Hero hero) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.worldX = hero.getX() + hero.getHitBox().getWidth() / 2 - this.WIDTH / 2;
        this.worldY = hero.getY() + hero.getHitBox().getHeight() / 2 - this.HEIGHT / 2;
        this.hero = hero;

    }

    /**
     * Update camera's position based on the Hero's movements.
     * @param time elapsed time since last call.
     */

    public void update(double time) {
        double velocityX = hero.getVelocityX();
        double velocityY = hero.getVelocityY();
        worldX += velocityX * time;
        worldY += velocityY * time;
    }

    /**
     * Recenter the Camera on the attached Hero.
     */
    public void recenterOnHero() {
        worldX = hero.getX() + hero.getHitBox().getWidth() / 2 - WIDTH / 2;
        worldY = hero.getY() + hero.getHitBox().getHeight() / 2 - HEIGHT / 2;
    }

    /**
     * @return the worldX's property value.
     */
    public double getWorldX() {
        return worldX;
    }

    /**
     * @return the worldY's property value.
     */
    public double getWorldY() {
        return worldY;
    }

    @Override
    public String toString() {
        return ("worldX=" + worldX + "," + "worldY=" + worldY);
    }

}