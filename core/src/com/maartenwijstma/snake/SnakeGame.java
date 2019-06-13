package com.maartenwijstma.snake;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public class SnakeGame implements Screen {
    private static final float WORLD_WIDTH = Gdx.graphics.getWidth();
    private static final float WORLD_HEIGHT = Gdx.graphics.getHeight();
    private Viewport viewport;
    private static final int blockSize = 60;
    private int snakeXPos = 5 * blockSize;
    private int snakeYPos = 10 * blockSize;

    private static final float moveTime = 0.12F;
    private float timer = moveTime;
    private int bodyLength = 4;
    private String direction = "UP";
    private ArrayList <SnakeBodySegment> segmentList = new ArrayList<>();
    private enum STATE {
        PLAYING, GAME_OVER
    }
    private STATE state = STATE.PLAYING;

    // Make a camera that the user views the screen through
    private OrthographicCamera cam = new OrthographicCamera();
    private SnakeHead snakeHead = new SnakeHead();
    private Apple apple;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private int score = 5;
    private String ScoreName = "score: 5";
    private SpriteBatch batch;
    BitmapFont font  = new BitmapFont();



    public SnakeGame(MyGdxGame SnakeGame) {
        // Construct the camera with the given width and height (false makes sure y values more up)

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, cam);
        viewport.apply();
        Matrix4 normalProjection = new Matrix4().setToOrtho2D(0,0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch = new SpriteBatch();


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
            Gdx.app.log("tagagag", "2");
            counter -= blockSize;
        }

        this.apple = new Apple(blockSize, Math.round(WORLD_WIDTH), Math.round(WORLD_HEIGHT));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        cam.update();
        viewport.apply();
        switch (state) {
            case PLAYING: {
                Gdx.app.debug("mytag", String.valueOf(WORLD_WIDTH));
                timer -= delta;
                if (timer <= 0) {
                    timer = moveTime;
                    checkApple();
                    move();
                }
                draw();
                batch.begin();
                font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                font.getData().setScale(2, 2);
                font.draw(batch, ScoreName, WORLD_WIDTH/2 - 40, WORLD_HEIGHT - 10);
                batch.end();
            }
            break;
            case GAME_OVER: {
            }
            break;
        }

    }

    private boolean checkApple() {
        Boolean appleEaten = false;
        int appleX = this.apple.getXposition();
        int appleY = this.apple.getYposition();

        if (appleX == snakeXPos && appleY == snakeYPos){
            appleEaten = true;
        }

        return appleEaten;
    }

    public void move(){
        Boolean appleEaten = checkApple();
        if (!appleEaten) {
            this.segmentList.remove(0);
        }
        else{
            this.apple.newApple(blockSize);
            score++;
            ScoreName = "length: " + score;
        }
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

        checkGameOver();
    }

    public boolean checkGameOver(){

        for (int i = 0; i < segmentList.size(); i++) {
            Gdx.app.log("tagagag", "3");
            if (segmentList.get(i).getXPosition() == snakeXPos && segmentList.get(i).getYPosition() == snakeYPos){
                state = STATE.GAME_OVER;
            }
        }
        if (snakeXPos >= viewport.getWorldWidth() || snakeXPos < 0) {
            state = STATE.GAME_OVER;
        }
        if (snakeYPos >= viewport.getWorldHeight() || snakeYPos < 0) {
            state = STATE.GAME_OVER;
        }
        return false;
    }

    public void draw(){
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.app.log("tagagag", "4");

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,1);
        shapeRenderer.rect(0,0,WORLD_WIDTH,WORLD_HEIGHT);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0);
        shapeRenderer.rect(5,5,WORLD_WIDTH - 10,WORLD_HEIGHT-10);
        shapeRenderer.end();

        snakeHead.draw(snakeXPos, snakeYPos, blockSize);

        this.apple.drawApple(blockSize);
        for (int i = 0; i < this.segmentList.size(); i++) {
            segmentList.get(i).drawbody(blockSize);
            Gdx.app.log("tagagag", "5");
        }
    }

    private void drawScore() {
        String scoreString = "Score: " + score;

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

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
