package com.maartenwijstma.snake;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class GoldenApple {
    private int Xposition = -100;
    private int Yposition = -100;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private static final float timeVisible = 5F;
    private float timer;

    public GoldenApple() {
    }

    public void newGoldenApple(int blockSize, int width, int height, ArrayList<SnakeBodySegment> segmentList, float delta){
        // Constructor
        chooseGoldenApplePosition(blockSize, width, height);

        for (int i = 0; i < segmentList.size(); i++) {
            if (segmentList.get(i).getXPosition() == this.Xposition && segmentList.get(i).getYPosition() == this.Yposition) {
                chooseGoldenApplePosition(blockSize, width, height);
                i = 0;
            }
        }
    }

    public void makeInvisible(float delta){
        // Function that places the golden apple outside the screen to make it invisible
            this.Xposition = -100;
            this.Yposition = -100;
    }

    public void chooseGoldenApplePosition(int blockSize, int width, int height){
        // Function that places the golden apple on the screen randomly
        Random randX = new Random();
        Random randY = new Random();

        int randomx = randX.nextInt(width/ blockSize + 1);
        int randomy = randY.nextInt(height/ blockSize + 1);

        this.Xposition = blockSize * randomx;
        this.Yposition = blockSize * randomy;
    }

    public void drawGoldenApple(int blockSize){
        // Function that draws the golden apple
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 0, 1);
            shapeRenderer.rect(this.Xposition, this.Yposition, blockSize - 5, blockSize - 5);
            shapeRenderer.end();
    }

    public int getXposition() {
        return Xposition;
    }

    public int getYposition() {
        return Yposition;
    }
}
