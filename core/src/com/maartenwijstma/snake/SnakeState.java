package com.maartenwijstma.snake;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SnakeState {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Function that draws the current snake
    public void draw(int width, int height, OrthographicCamera cam){

        // Between the begin and end statement, a rectangle is made
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,1);
        shapeRenderer.rect(540,920,50,50);
        shapeRenderer.end();
    }
}
