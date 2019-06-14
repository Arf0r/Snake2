package com.maartenwijstma.snake;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class GoldenApple {
    private int Xposition;
    private int Yposition;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public GoldenApple(int blockSize, int width, int height) {
        Random randX = new Random();
        Random randY = new Random();
        int randomx = randX.nextInt(width/ blockSize + 1);
        int randomy = randY.nextInt(height/ blockSize + 1);

        this.Xposition = blockSize * randomx;
        this.Yposition = blockSize * randomy;
    }

    public void newGoldenApple(int blockSize, int width, int height, ArrayList<SnakeBodySegment> segmentList){

        chooseGoldenApplePosition(blockSize, width, height);

        for (int i = 0; i < segmentList.size(); i++){
            if (segmentList.get(i).getXPosition() == this.Xposition && segmentList.get(i).getYPosition() == this.Yposition) {
                chooseGoldenApplePosition(blockSize, width, height);
                i = 0;
            }
        }
    }

    public void chooseGoldenApplePosition(int blockSize, int width, int height){
        Random randX = new Random();
        Random randY = new Random();

        int randomx = randX.nextInt(width/ blockSize + 1);
        int randomy = randY.nextInt(height/ blockSize + 1);

        this.Xposition = blockSize * randomx;
        this.Yposition = blockSize * randomy;
    }

    public void drawGoldenApple(int blockSize){
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