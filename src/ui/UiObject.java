package ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class UiObject {
    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;
    protected boolean hovering = false;
    public UiObject(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int)x, (int)y, width, height);
    }
    public abstract void update();

    public abstract void render(Graphics g);

    public abstract void onClick();

    public void onMouseMove(MouseEvent e){
        if(bounds.contains(e.getX(), e.getY())){
            hovering = true;
        }
        else{
            hovering = false;
        }

    }

    public void onMouseRelease(MouseEvent e){
        if (hovering){
            onClick();
        }
    }

    // GETTER AND SETTER

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }
}
