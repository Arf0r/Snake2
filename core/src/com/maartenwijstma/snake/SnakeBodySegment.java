package com.maartenwijstma.snake;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.Serializable;

public class SnakeBodySegment {
    private int XPosition;
    private int YPosition;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public SnakeBodySegment(int XPosition, int YPosition) {
        this.XPosition = XPosition;
        this.YPosition = YPosition;
    }

    public void drawbody(int blockSize) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,1);
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
