package tileGame;

import graphics.Assets;
import graphics.GameCamera;
import input.KeyManager;
import input.MouseManager;
import states.GameState;
import states.MenuState;
import states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

import java.util.Random;

public class Game implements Runnable{
    private Display display;
    private int width, height;
    public String title;

    Random rand = new Random();
    private Thread thread;
    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    // States
    public State gameState;
    public State menuState;

    // INPUT
    private KeyManager keyManager;
    private MouseManager mouseManager;

    // CAMERA
    private GameCamera gameCamera;

    //HANDLER
    private Handler handler;

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    private void init(){
        display = new Display(title, width, height);

        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);

        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        Assets.init();

        handler = new Handler(this);
        gameCamera = new GameCamera(handler,0,0);



        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        State.setState(gameState);
       // State.setState(menuState);
    }

    private void update(){
        keyManager.update();
        if (State.getState()!=null){
            State.getState().update();
        }
    }
    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if (bs==null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        g.clearRect(0,0,width, height);
        if (State.getState()!=null){
            State.getState().render(g);
        }

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();
        int fps  = 60;
        double timePerUpdate = 1000000000/fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int tick = 0;

        while (running){
            now = System.nanoTime();
            delta = delta+(now-lastTime)/timePerUpdate;
            timer += now-lastTime;
            lastTime = now;

            if (delta>=1) {
                update();
                render();
                tick++;
                delta--;
            }
            /* fps counter
            if (timer>=1000000000){
                System.out.println("tick::: "+tick);
                tick = 0;
                timer = 0;
            }
             */
        }
        stop();
    }
    public KeyManager getKeyManager(){
        return keyManager;
    }
    public MouseManager getMouseManager(){
        return mouseManager;
    }
    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
