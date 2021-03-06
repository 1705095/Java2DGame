package entity;

import tileGame.Game;
import tileGame.Handler;
import tiles.Tile;

public abstract class Creature extends Entity{

    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 300;
    public static final int DEFAULT_CREATURE_HEIGHT = 300;



    protected float speed;
    protected float xMove, yMove;


    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);

        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;

    }

    public void move(){
        if(!checkEntityCollisions(xMove, 0)) {
            moveX();
        }
        if(!checkEntityCollisions(0, yMove)) {
            moveY();
        }
    }

    public void moveX(){
        if(xMove>0){
            // move right
            int tx = (int)(x+xMove+bounds.x+bounds.width)/ Tile.TILE_WIDTH;
            if(!collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILE_HEIGHT)
            && !collisionWithTile(tx, (int)(y+bounds.y+bounds.height)/Tile.TILE_HEIGHT)){
                x+=xMove;
            }
            else{
                x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width-1;
            }

        }
        else if(xMove<0){
            // move left
            int tx = (int)(x+ xMove+ bounds.x)/ Tile.TILE_WIDTH;
            if(!collisionWithTile(tx,(int)(y+bounds.y)/Tile.TILE_HEIGHT)
                    && !collisionWithTile(tx, (int)(y+bounds.y+bounds.height)/Tile.TILE_HEIGHT)){
                x+=xMove;
            }
            else{
                x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
            }
        }
    }
    public void moveY(){
        if (yMove<0){
            //up
            int tempY = (int) (y + yMove + bounds.y)/Tile.TILE_HEIGHT;
            if(!collisionWithTile((int)(x+bounds.x)/Tile.TILE_WIDTH, tempY)
            && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILE_WIDTH, tempY)){
                y += yMove;
            }
            else{
                y = tempY * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
            }

        }
        else if(yMove>0){
            // down
            int tempY = (int) (y + yMove + bounds.y + bounds.height)/Tile.TILE_HEIGHT;
            if(!collisionWithTile((int)(x+bounds.x)/Tile.TILE_WIDTH, tempY)
                    && !collisionWithTile((int)(x+bounds.x+bounds.width)/Tile.TILE_WIDTH, tempY)){
                y += yMove;
            }
            else {
                y = tempY * Tile.TILE_HEIGHT - bounds.y - bounds.height -1;
            }
        }


    }
    protected boolean collisionWithTile(int x, int y){
        return handler.getWorld().getTile(x,y).isSolid();
    }
    // getter and setters

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }
}
