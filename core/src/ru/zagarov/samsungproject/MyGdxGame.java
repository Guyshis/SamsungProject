package ru.zagarov.samsungproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyGdxGame extends Game {

	public static final int SCREEN_WIDTH = 320;
	public static final int SCREEN_HEIGHT = 176;
	public FitViewport fitViewport;

	@Override
	public void create () {
		fitViewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
		fitViewport.apply();
		Stage stage = new Stage(fitViewport);
		Gdx.input.setInputProcessor(stage);
		setScreen(new MenuScreen(this, stage));
	}

	@Override
	public void resize(int width, int height) {
		fitViewport.update(width, height);
	}
}