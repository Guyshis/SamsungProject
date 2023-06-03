package ru.zagarov.samsungproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
//import ru.zagarov.fungame.TiledScreen1.TiledScreen;

public class GameMenuScreen extends Actor {

    private Texture texture;
    private Game game;

    public GameMenuScreen(Game game) {
        texture = new Texture("0.jpg");
        this.game = game;
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, 0, 0, MyGdxGame.SCREEN_WIDTH, MyGdxGame.SCREEN_HEIGHT);
    }
}

