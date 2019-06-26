package com.maartenwijstma.snake;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.Serializable;

public class SnakeBodySegment {
    private int XPosition;
    private int YPosition;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public SnakeBodySegment(int XPosition, int YPosition) {
        // Constructor
        this.XPosition = XPosition;
        this.YPosition = YPosition;
    }

    public void drawbody(int blockSize, boolean goldenSnake) {
        // Function that draws the body of the snake
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(goldenSnake){
            shapeRenderer.setColor(255/255f, 215/255f, 0/255f, 1);
        }
        else {
            shapeRenderer.setColor(1, 1, 1, 1);
        }
        shapeRenderer.rect(this.XPosition,this.YPosition,blockSize-5,blockSize-5);
        shapeRenderer.end();
    }

    public int getXPosition() {
        return XPosition;
    }

    public int getYPosition() {
        return YPosition;
    }
}
