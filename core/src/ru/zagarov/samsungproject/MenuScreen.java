package ru.zagarov.samsungproject;

import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_HEIGHT;
import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MenuScreen implements Screen {

    private Stage stage;
    public Game myGdxGame;
    public FitViewport viewport;

    public MenuScreen(Game myGdxGame, Stage stage) {
        this.myGdxGame = myGdxGame;
        this.stage = stage;

        Texture startTexture = new Texture("startButton.png");
        ImageButton startButton = new ImageButton(new TextureRegionDrawable(startTexture));
        startButton.setPosition((SCREEN_WIDTH - startButton.getWidth()) / 2, SCREEN_HEIGHT / 2f);

        Texture levelTexture = new Texture("levelButton.png");
        ImageButton levelButton = new ImageButton(new TextureRegionDrawable(levelTexture));
        levelButton.setPosition((SCREEN_WIDTH - levelButton.getWidth()) / 2, SCREEN_HEIGHT / 10f);





        stage.addActor(new MenuGroundActor(startButton, levelButton, myGdxGame));
        stage.addActor(startButton);
        stage.addActor(levelButton);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act();
        stage.draw();
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
        stage.dispose();
    }
}
