package ru.zagarov.samsungproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
//import ru.zagarov.fungame.TiledScreen1.TiledScreen;

public class FloorActor extends Actor {

    private Texture texture;
    private Game game;

    public FloorActor(Game game) {
        if(LevelsScreen.FifthLevelCheck){
            texture = new Texture("SamsungMapPng2.png");
        } else if(LevelsScreen.SeventhLevelCheck){
            texture = new Texture("SamsungMapPng5.png");
        } else {
            texture = new Texture("SamsungMapPng.png");
        }

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