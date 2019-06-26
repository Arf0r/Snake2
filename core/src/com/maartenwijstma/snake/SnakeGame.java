package com.maartenwijstma.snake;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame implements Screen {
    // Make variables
    private static final int blockSize = 80;
    private static final float WORLD_WIDTH = blockSize * Math.round((Gdx.graphics.getWidth()-20)/blockSize);
    private static final float WORLD_HEIGHT = blockSize * Math.round((Gdx.graphics.getHeight()-20)/blockSize);
    private static final float goldenAppleSpawnTime = 20F;
    private static final float goldenSnakeTime = 5F;
    private static final float goldenAppleVisibilityTime = 5F;
    private static float moveTime = 0.3F;
    private float timer = moveTime;
    private float timer2 = goldenAppleSpawnTime;
    private float timer3 = goldenSnakeTime;
    private float timer4 = goldenAppleVisibilityTime;
    private int snakeXPos = 5 * blockSize;
    private int snakeYPos = 10 * blockSize;
    private int bodyLength = 4;
    private int score = 5;
    private boolean goldenAppleEaten;
    private boolean goldenSnake = false;
    private enum STATE {PLAYING, GAME_OVER}
    private STATE state = STATE.PLAYING;
    private String direction = "UP";
    private ArrayList<SnakeBodySegment> segmentList = new ArrayList<>();
    private Random rand = new Random();
    private OrthographicCamera cam = new OrthographicCamera();
    private SnakeHead snakeHead = new SnakeHead();
    private Apple apple;
    private String ScoreName = "score: 5";
    private SpriteBatch batch;
    private Viewport viewport;
    private Wormholes wormholes;
    private GoldenApple goldenApple;
    BitmapFont font = new BitmapFont();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Rectangle restart = new Rectangle(WORLD_WIDTH/2 - 100, WORLD_HEIGHT/2 - 50, 130, 130);
    private Vector2 touch = new Vector2();


    public SnakeGame(MyGdxGame SnakeGame) {
        // Sets the grid to fit any screen size
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, cam);
        viewport.apply();
        Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Make a new spriteBatch and call the function that allows user to swipe and make the snake
        batch = new SpriteBatch();
        Swipe();
        initializeSnake();
    }

    public void Swipe() {
        // Function that allows the user to swipe up, dowm, right or left

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

    public void initializeSnake() {
        // Function that initializes the snake's body as well as the apple, golden apple and wormholes
        int counter = bodyLength * blockSize;
        for (int i = 0; i < bodyLength; i++) {
            SnakeBodySegment segment = new SnakeBodySegment(snakeXPos, snakeYPos - counter);
            this.segmentList.add(segment);
            counter -= blockSize;
        }

        this.apple = new Apple(blockSize, Math.round(WORLD_WIDTH), Math.round(WORLD_HEIGHT));
        this.goldenApple = new GoldenApple();
        this.wormholes = new Wormholes(blockSize, Math.round(WORLD_WIDTH), Math.round(WORLD_HEIGHT));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Function that renders everything on the screen, updates several time each second
        cam.update();
        viewport.apply();
        switch (state) {
            // There are two states: playing and game over
            case PLAYING: {
                // Move the snake
                timer -= delta;
                if (timer <= 0) {
                    timer = moveTime;
                    move();
                    this.goldenAppleEaten = checkGoldenApple(delta);
                    if (this.goldenAppleEaten) {
                        this.goldenApple.makeInvisible(delta);
                        enlargeSnake(delta);
                    }
                }

                // If golden apple is eaten the apple disappears
                if (goldenAppleEaten){
                    timer4 -= delta;
                    if (timer4 <= 0){
                        timer4 = goldenAppleVisibilityTime;
                        this.goldenApple.makeInvisible(delta);
                    }
                }

                // Spawns a new golden apple within a certain time frame
                timer2 -= delta;
                if (timer2 <= 0) {
                    timer2 = goldenAppleSpawnTime;
                    this.goldenApple.newGoldenApple(blockSize, Math.round(WORLD_WIDTH), Math.round(WORLD_HEIGHT), segmentList, delta);
                }

                checkWormhole();
                drawBoard();
                drawSnakeAppleWormhole();
            }
            break;
            // If the game is over, check for restart
            case GAME_OVER: {
                restart();
            }
            break;
        }
    }

    private void restart() {
        // Function that draws the restart button and that checks if its clicked, and that then
        // restarts the game
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.getData().setScale(2, 2);
        font.draw(batch, "Restart!", WORLD_WIDTH/2 - 50, WORLD_HEIGHT/2 - 20);
        batch.end();

        if (Gdx.input.isTouched()) {
            touch.x = Gdx.input.getX();
            touch.y = Gdx.input.getY();

        }
        if (restart.contains(touch)){
            touch.x = 0;
            touch.y = 0;

            direction = "UP";
            snakeXPos = 5 * blockSize;
            snakeYPos = 7 * blockSize;
            bodyLength = 4;
            segmentList.clear();
            initializeSnake();
            state = STATE.PLAYING;

        }
    }


    private void enlargeSnake(float delta) {
        // Function that increases the snake by 5 if golden apple is eaten
        for (int i = 0; i < 5; i++) {
            SnakeBodySegment segment = new SnakeBodySegment(snakeXPos, snakeYPos);
            this.segmentList.add(0, segment);
        }

        this.moveTime = 0.2F;
        timer3 -= delta;
        if (timer3 <= 0) {
            timer = goldenSnakeTime;
            this.moveTime = 0.3F;
        }
    }

    public void checkWormhole() {
        // function that checks if the snake goes trough the wormholes, if so swap the position
        int wormhole1x = this.wormholes.getWorm1X();
        int wormhole1y = this.wormholes.getWorm1Y();
        int wormhole2x = this.wormholes.getWorm2X();
        int wormhole2y = this.wormholes.getWorm2Y();

        if (wormhole1x == snakeXPos && wormhole1y == snakeYPos) {
            snakeXPos = wormhole2x;
            snakeYPos = wormhole2y;
        }

        if (wormhole2x == snakeXPos && wormhole2y == snakeYPos) {
            snakeXPos = wormhole1x;
            snakeYPos = wormhole1y;
        }
    }

    private boolean checkApple() {
        // Function that returns wether the apple has been eaten the last move or not and increases the score
        Boolean appleEaten = false;
        int appleX = this.apple.getXposition();
        int appleY = this.apple.getYposition();

        if (appleX == snakeXPos && appleY == snakeYPos) {
            appleEaten = true;
        }

        if (appleEaten) {
            this.apple.newApple(blockSize, Math.round(WORLD_WIDTH), Math.round(WORLD_HEIGHT), segmentList);
            score++;
            ScoreName = "length: " + score;
        }
        return appleEaten;
    }

    private boolean checkGoldenApple(float delta) {
        // Function that checks whether the golden apple has been eaten last move or not and increase score
        this.goldenAppleEaten = false;
        int goldenAppleX = this.goldenApple.getXposition();
        int goldenAppleY = this.goldenApple.getYposition();

        if (goldenAppleX == snakeXPos && goldenAppleY == snakeYPos) {
            this.goldenAppleEaten = true;
        }
        if (goldenAppleEaten) {
            score += 5;
            ScoreName = "length: " + score;
        }
        return this.goldenAppleEaten;
    }

    public void move() {
        // Function that moves the snake one block on grid every second in the right direction
        boolean appleEaten = checkApple();
        if (!appleEaten) {
            this.segmentList.remove(0);
        }
        switch (direction) {
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

    public boolean checkGameOver() {
        // Function that checks whether the player has died and returns it

        for (int i = 0; i < segmentList.size(); i++) {
            if (segmentList.get(i).getXPosition() == snakeXPos && segmentList.get(i).getYPosition() == snakeYPos) {
                state = STATE.GAME_OVER;

            }
        }
        if (snakeXPos == viewport.getWorldWidth() || snakeXPos < 0) {
            state = STATE.GAME_OVER;
        }
        if (snakeYPos == viewport.getWorldHeight() || snakeYPos < 0) {
            state = STATE.GAME_OVER;

        }
        return false;
    }

    public void drawBoard() {
        // Function that draws the backgound and the score
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0);
        shapeRenderer.rect(5, 5, WORLD_WIDTH - 10, WORLD_HEIGHT - 10);
        shapeRenderer.end();

        batch.begin();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.getData().setScale(2, 2);
        font.draw(batch, ScoreName, WORLD_WIDTH / 2 - 40, WORLD_HEIGHT - 10);
        batch.end();
    }

    public void drawSnakeAppleWormhole() {
        // Function that draws the apple, golden apple, snake and its body and the wormholes
        this.apple.drawApple(blockSize);
        snakeHead.draw(snakeXPos, snakeYPos, blockSize, goldenSnake);

        for (int i = 0; i < this.segmentList.size(); i++) {
            segmentList.get(i).drawbody(blockSize, goldenSnake);
        }

        this.goldenApple.drawGoldenApple(blockSize);
        this.wormholes.drawWormhole(blockSize);
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
