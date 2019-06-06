package com.maartenwijstma.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SnakeGame implements Screen {
    // Define the width and height of the screen (basic 1080x1920 ratio)
    private int width = 1080;
    private int height = 1920;

    // Make a camera that the user views the screen through
    private OrthographicCamera cam = new OrthographicCamera(width, height);
    private SnakeState snakeState = new SnakeState();

    public SnakeGame(MyGdxGame SnakeGame) {
        // Construct the camera with the given width and height (false makes sure y values more up)
        cam.setToOrtho(false, width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Make the background white
        Gdx.gl.glClearColor(0, 0, 0, 1);

        // Draw the snake in its current state
        snakeState.draw(width, height, cam);
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
