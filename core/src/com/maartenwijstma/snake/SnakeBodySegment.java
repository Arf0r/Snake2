package com.maartenwijstma.snake;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.Serializable;

public class SnakeBodySegment {
    private int XPosition;
    private int YPosition;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public SnakeBodySegment(int XPosition, int YPosition){
        this.XPosition = XPosition;
        this.YPosition = YPosition;
    }

}
