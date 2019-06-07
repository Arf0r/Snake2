package com.maartenwijstma.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;

public class SnakeGame implements Screen {
    // Define the width and height of the screen (basic 1080x1920 ratio)
    private static final int width = 1080;
    private static final int height = 1920;
    private static final int blockSize = 40;
    private int snakeXPos = 540;
    private int snakeYPos = 920;

    private static final float moveTime = 0.5F;
    private float timer = moveTime;
    private int bodyLength = 4;
    private String direction = "UP";
    private ArrayList <SnakeBodySegment> segmentList = new ArrayList<>();

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


        int counter = bodyLength * blockSize;
        for (int i = 0; i < bodyLength; i++){
            SnakeBodySegment segment = new SnakeBodySegment(snakeXPos, snakeYPos - counter);
            this.segmentList.add(segment);
            counter -= blockSize;
        }
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

            this.segmentList.remove(0);
            switch(direction){
                case "RIGHT":
                    snakeXPos += blockSize;
                    SnakeBodySegment segment = new SnakeBodySegment(snakeXPos - blockSize, snakeYPos);
                    this.segmentList.add(segment);
                    break;
                case "UP":
                    snakeYPos += blockSize;
                    SnakeBodySegment segment2 = new SnakeBodySegment(snakeXPos, snakeYPos - blockSize);
                    this.segmentList.add(segment2);
                    break;
                case "DOWN":
                    snakeYPos -= blockSize;
                    SnakeBodySegment segment3 = new SnakeBodySegment(snakeXPos, snakeYPos + blockSize);
                    this.segmentList.add(segment3);
                    break;
                case "LEFT":
                    snakeXPos -= blockSize;
                    SnakeBodySegment segment4 = new SnakeBodySegment(snakeXPos + blockSize, snakeYPos);
                    this.segmentList.add(segment4);
                    break;
            }
        }
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        snakeHead.draw(snakeXPos, snakeYPos, blockSize);

        for (int i = 0; i < this.segmentList.size(); i++) {
            segmentList.get(i).drawbody(blockSize);
        }
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
