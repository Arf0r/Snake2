package com.maartenwijstma.snake;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class Wormholes {
    private int worm1X;
    private int worm1Y;
    private int worm2X;
    private int worm2Y;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Wormholes(int blockSize, int width, int height){
        // Constructor that places the wormholes randomly as they are created
        Random rand1X = new Random();
        Random rand1Y = new Random();
        int random1x = rand1X.nextInt(width/ blockSize);
        int random1y = rand1Y.nextInt(height/ blockSize);

        Random rand2X = new Random();
        Random rand2Y = new Random();
        int random2x = rand2X.nextInt(width/ blockSize);
        int random2y = rand2Y.nextInt(height/ blockSize);

        this.worm1X = blockSize * random1x;
        this.worm1Y = blockSize * random1y;
        this.worm2X = blockSize * random2x;
        this.worm2Y = blockSize * random2y;
    }

    public void drawWormhole(int blockSize){
        // Function that draws the wormholes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,1,1);
        shapeRenderer.rect(this.worm1X, this.worm1Y,blockSize,blockSize);
        shapeRenderer.rect(this.worm2X, this.worm2Y,blockSize,blockSize);
        shapeRenderer.setColor(0,0,0,1);
        shapeRenderer.rect(this.worm1X + 5, this.worm1Y + 5, blockSize - 10, blockSize-10);
        shapeRenderer.rect(this.worm2X + 5, this.worm2Y + 5, blockSize - 10, blockSize-10);
        shapeRenderer.end();

    }

    public int getWorm1X() {
        return worm1X;
    }

    public int getWorm1Y() {
        return worm1Y;
    }

    public int getWorm2X() {
        return worm2X;
    }

    public int getWorm2Y() {
        return worm2Y;
    }
}

