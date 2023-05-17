package ru.zagarov.samsungproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelsGroundActor extends Actor {

    private Texture texture;

    public LevelsGroundActor() {
        texture = new Texture("MenuGround.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, 0, 0, MyGdxGame.SCREEN_WIDTH, MyGdxGame.SCREEN_HEIGHT);
    }
}
