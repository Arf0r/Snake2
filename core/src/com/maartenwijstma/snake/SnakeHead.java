package com.maartenwijstma.snake;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SnakeHead {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Function that draws the current snake
    public void draw(int snakeXPos, int snakeYPos, int blockSize){

        // Between the begin and end statement, a rectangle is made
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,1);
        shapeRenderer.rect(snakeXPos,snakeYPos,blockSize-5,blockSize-5);
        shapeRenderer.end();
    }
}
