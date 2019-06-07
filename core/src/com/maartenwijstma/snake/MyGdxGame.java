package com.maartenwijstma.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Extends the game class which sets the screen
public class MyGdxGame extends Game {

	@Override
	public void create() {
		// Set the screen according to the game class
		this.setScreen(new SnakeGame(this));

	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		// Executes the render method of SnakeGame class
		super.render();
	}
}
