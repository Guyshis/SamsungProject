package ru.zagarov.samsungproject;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

	public static final int SCREEN_WIDTH = 320;
	public static final int SCREEN_HEIGHT = 176;

	@Override
	public void create () {
		setScreen(new SecondScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}
}