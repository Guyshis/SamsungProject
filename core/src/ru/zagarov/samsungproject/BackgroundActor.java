package ru.zagarov.samsungproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.zagarov.samsungproject.MyGdxGame;

public class BackgroundActor extends Actor {

    private Texture texture;



    public BackgroundActor() {
        texture = new Texture("background.png");
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, 0, 0, MyGdxGame.SCREEN_WIDTH , MyGdxGame.SCREEN_HEIGHT);
    }


}
