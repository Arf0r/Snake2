package com.maartenwijstma.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import sun.rmi.runtime.Log;


public class Apple {
    private int Xposition;
    private int Yposition;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Apple(int blockSize, int width, int height) {
        Random randX = new Random();
        Random randY = new Random();
        int randomx = randX.nextInt(width/ blockSize + 1);
        int randomy = randY.nextInt(height/ blockSize + 1);

        this.Xposition = blockSize * randomx;
        this.Yposition = blockSize * randomy;
    }

    public void newApple(int blockSize){
        Random randX = new Random();
        Random randY = new Random();
        int randomx = randX.nextInt(1080/ blockSize + 1);
        int randomy = randY.nextInt(1920/ blockSize + 1);

        this.Xposition = blockSize * randomx;
        this.Yposition = blockSize * randomy;
    }

    public void drawApple(int blockSize){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,0,0,1);
        shapeRenderer.rect(this.Xposition,this.Yposition,blockSize-5,blockSize-5);
        shapeRenderer.end();
    }

    public int getXposition() {
        return Xposition;
    }

    public int getYposition() {
        return Yposition;
    }
}

