package com.maartenwijstma.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SnakeGame implements Screen {
    // Define the width and height of the screen (basic 1080x1920 ratio)
    private static final int width = 1080;
    private static final int height = 1920;
    private static final int blockSize = 40;
    private int snakeXPos = 540;
    private int snakeYPos = 920;

    private static final float moveTime = 0.5F;
    private float timer = moveTime;
    private int snakeLength = 5;
    private String direction = "UP";

    // Make a camera that the user views the screen through
    private OrthographicCamera cam = new OrthographicCamera(width, height);
    private SnakeHead snakeHead = new SnakeHead();

    public SnakeGame(MyGdxGame SnakeGame) {
        // Construct the camera with the given width and height (false makes sure y values more up)
        cam.setToOrtho(false, width, height);
        Gdx.input.setInputProcessor(new GestureListener(new GestureListener.DirectionListener() {

            @Override
            public void onUp() {
                if (direction != "DOWN") {
                    direction = "UP";
                }
            }

            @Override
            public void onRight() {
                if (direction != "LEFT") {
                    direction = "RIGHT";
                }
            }

            @Override
            public void onLeft() {
                if (direction != "RIGHT") {
                    direction = "LEFT";
                }
            }

            @Override
            public void onDown() {
                if (direction != "UP") {
                    direction = "DOWN";
                }
            }
        }));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Make the background white
        Gdx.gl.glClearColor(0, 0, 0, 1);

        timer -= delta;
        if (timer <= 0) {
            timer = moveTime;

            switch(direction){
                case "RIGHT":
                    snakeXPos += blockSize;
                    break;
                case "UP":
                    snakeYPos += blockSize;
                    break;
                case "DOWN":
                    snakeYPos -= blockSize;
                    break;
                case "LEFT":
                    snakeXPos -= blockSize;
                    break;

            }

        }
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        snakeHead.draw(snakeXPos, snakeYPos, blockSize);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {

    }
}
